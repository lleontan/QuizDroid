package edu.us.ischool.tanl2.quizdroid

import android.app.Application
import android.content.SharedPreferences
import android.os.NetworkOnMainThreadException
import android.preference.PreferenceManager

import android.util.Log
import android.widget.Toast
import com.google.gson.Gson
import layout.Quiz
import layout.topic
import org.json.JSONObject
import java.net.URL


class QuizApp: Application() {
    private lateinit var repo:TopicRepository
    fun getTopicStore():TopicRepository{
        return repo
    }
    init {
        instance = this
    }

    companion object {
        private var instance: QuizApp? = null

        fun applicationContext() : QuizApp {
            return instance as QuizApp
        }
    }


    override fun onCreate() {
        super.onCreate()
        repo= TopicStore()
        repo.store()
        Log.v("quiz","QuizApp Started")
    }

    class TopicStore:TopicRepository {
        private lateinit var topics:Array<topic>
        override fun store() {
            var url:String = PreferenceManager.getDefaultSharedPreferences(QuizApp.applicationContext()).getString("url","")
            var jsonString=""
            var urlRegex=Regex("^.+.{3}.+.{3}.+[.]json$")
            if(urlRegex.matches(url)){
                Log.v("regex","regexPassed")
                try{
                jsonString=URL(url).readText()
                }catch(excep: NetworkOnMainThreadException){
                    val file_name = "questions.json"
                    Log.v("regex","urlInvalid $url")
                    Toast.makeText(applicationContext(),"Invalid URL",Toast.LENGTH_SHORT)
                    jsonString=applicationContext().assets.open(file_name).bufferedReader().use{
                        it.readText()
                    }
                }
            }else{
                Log.v("regex","regexFailed $url")
                val file_name = "questions.json"
                jsonString=applicationContext().assets.open(file_name).bufferedReader().use{
                    it.readText()
                }
            }
            val gson = Gson()
            topics=gson.fromJson(jsonString, Array<topic>::class.java)

        }

        override fun getTopics():Array<topic> {
            return topics
        }
    }
}