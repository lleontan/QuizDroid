package edu.us.ischool.tanl2.quizdroid

import android.app.AlarmManager
import android.app.AlertDialog
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.SystemClock
import android.preference.PreferenceManager
import android.provider.Settings
import android.support.v4.content.ContextCompat
import android.support.v4.content.LocalBroadcastManager
import android.util.Log
import android.widget.Button
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson

import kotlinx.android.synthetic.main.activity_main.*
import layout.topic
import java.io.File
import java.io.Serializable
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class MainActivity : AppCompatActivity(), downloadListener{
    private lateinit var topics:Array<topic>
    val BROADCAST_DOWNLOAD="333$"
    lateinit var queue: RequestQueue
    var alarmID = 99
    private var alarmMgr: AlarmManager? = null
    private lateinit var alarmIntent: PendingIntent
    private var quitBuggingAboutAirplaneMode=false
    lateinit var dBroadCastReceiver:DownloadBroadcastReciever
    init{
        instance=this
    }
    companion object {
        private var instance: MainActivity? = null

        fun mainActivityContext(): MainActivity {
            return instance as MainActivity
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dBroadCastReceiver= DownloadBroadcastReciever()
        setContentView(R.layout.activity_main)
        val topicRep=application as QuizApp
        queue = Volley.newRequestQueue(this)
        alarmMgr = applicationContext.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        alarmIntent = Intent(this, DownloadBroadcastReciever::class.java).let { intent ->
            PendingIntent.getBroadcast(this, 0, intent, 0)
        }

        val interval =
            PreferenceManager.getDefaultSharedPreferences(QuizApp.applicationContext()).getInt("minutes", 1)
        //I don't know why but multiplaying interval by 1000 results in trigger times larger than 1 minute.
        //Keeping interval as a int seems to be closer to the minutes desired.
        Log.v("network", "Alarm interval is $interval minutes")
        alarmMgr?.setRepeating(
            AlarmManager.RTC_WAKEUP,
            SystemClock.elapsedRealtime() + interval * R.integer.base_download_interval,
            interval.toLong()*R.integer.base_download_interval,
            alarmIntent
        )
    }
    private fun IsAirplaneModeOn(context: Context): Boolean {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR1) {
            return Settings.System.getInt(
                context.getContentResolver(),
                Settings.System.AIRPLANE_MODE_ON, 0
            ) != 0;
        } else {
            return Settings.Global.getInt(
                context.getContentResolver(),
                Settings.Global.AIRPLANE_MODE_ON, 0
            ) != 0;
        }
    }
    override fun fetchData(){
        val connectivityManager =
            QuizApp.applicationContext().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetworkInfo = connectivityManager!!.activeNetworkInfo
        if (activeNetworkInfo==null||!activeNetworkInfo.isConnected) {
            Toast.makeText(QuizApp.applicationContext(), R.string.no_internet, Toast.LENGTH_SHORT).show()
            if (IsAirplaneModeOn(QuizApp.applicationContext())&&!quitBuggingAboutAirplaneMode) {
                quitBuggingAboutAirplaneMode=true
                val builder = AlertDialog.Builder(this)
                builder.setTitle("Airplane mode")
                builder.setMessage("Turn off Airplane mode?")
                builder.setPositiveButton("YES") { dialog, which ->
                    var settingsIntent = Intent(android.provider.Settings.ACTION_AIRPLANE_MODE_SETTINGS)
                    ContextCompat.startActivity(QuizApp.applicationContext(), settingsIntent, null);
                }
                builder.setNegativeButton("No") { dialog, which ->
                }
                builder.setNeutralButton("Cancel") { _, _ ->
                }
                var airplaneModeDialog = builder.create()
                airplaneModeDialog.show()
            }
        } else {
            var url: String =
                PreferenceManager.getDefaultSharedPreferences(QuizApp.applicationContext()).getString("url", "")
            var jsonString = ""
            var urlRegex = Regex("^.+[.]json$")
            var altUrlRegex = Regex("^http(s|)//:.+[.]json$")
            if (!url.matches(altUrlRegex)) {
                url = "https://$url"
            }
            Log.v("networks", "attempting $url")

            if (urlRegex.matches(url)) {
                var attemptText=R.string.attempt_download
                Toast.makeText(QuizApp.applicationContext(), attemptText, Toast.LENGTH_SHORT).show()
                val jsonObjectRequest = StringRequest(
                    Request.Method.GET, url,
                    Response.Listener<String> { response ->
                        Log.v("networks", "network success $url")
                        //Log.v("networks",response)
                        jsonString = response
                        QuizApp.applicationContext().getTopicStore().store(jsonString)
                        val gson=Gson()
                        writeQuestions()
                        //topics = gson.fromJson(jsonString, Array<topic>::class.java)
                        Toast.makeText(QuizApp.applicationContext(), R.string.download_success, Toast.LENGTH_SHORT).show()
                    },
                    Response.ErrorListener { error ->
                        Log.v("networks", "network error $url")
                        Toast.makeText(QuizApp.applicationContext(), R.string.download_failed, Toast.LENGTH_SHORT).show()
                    }
                )
                queue.add(jsonObjectRequest)
            } else {
                Log.v("networks", "Invalid url $url")
                Toast.makeText(QuizApp.applicationContext(), "${R.string.invalid_url}$url", Toast.LENGTH_LONG).show()
                //QuizApp.applicationContext().getTopicStore().localStore()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    private fun writeQuestions(){
        val topicRep=application as QuizApp
        topic_list.removeAllViews()
        topics=topicRep.getTopicStore().getTopics()
        topics.map {newTopic:topic ->
            var newButton:Button=Button(this)
            val name:String=newTopic.title
            val desc:String=newTopic.desc
            val bText="$name\n$desc"
            newButton.text=bText
            newButton.setOnClickListener {
                val intent = Intent(this, QuestionMain::class.java)
                intent.putExtra("questions", newTopic.questions as Serializable)
                intent.putExtra("title", newTopic.title)
                intent.putExtra("desc", newTopic.desc)
                startActivity(intent)
            }
            topic_list.addView(newButton)
        }
    }
    override fun onResume() {
        super.onResume()
        writeQuestions()
        fetchData()
        preferences_button.setOnClickListener {
            val intent = Intent(this, Preferences::class.java)
            startActivity(intent)
        }
    }
}
