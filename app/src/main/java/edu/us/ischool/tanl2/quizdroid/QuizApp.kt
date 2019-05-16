package edu.us.ischool.tanl2.quizdroid

import android.app.*
import android.app.PendingIntent.getActivity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.NetworkOnMainThreadException
import android.os.SystemClock
import android.preference.PreferenceManager

import android.util.Log
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import layout.Quiz
import layout.topic
import org.json.JSONObject
import java.net.URL
import android.net.NetworkInfo
import android.support.v4.content.ContextCompat.getSystemService
import android.net.ConnectivityManager
import android.os.Build
import android.provider.Settings
import android.support.v4.app.ActivityCompat.startActivityForResult
import android.support.v4.content.ContextCompat.startActivity
import java.io.File
import java.io.FileWriter


class QuizApp : Application() {
    private lateinit var repo: TopicRepository
    fun getTopicStore(): TopicRepository {
        return repo
    }

    init {
        instance = this
    }


    companion object {
        private var instance: QuizApp? = null

        fun applicationContext(): QuizApp {
            return instance as QuizApp
        }
    }


    override fun onCreate() {
        super.onCreate()
        repo = TopicStore()
        repo.localStore()
        Log.v("quiz", "QuizApp Started")
    }


    class TopicStore : TopicRepository {
        private lateinit var topics: Array<topic>

        override fun fetchData(){

        }

        override fun store(jsonStr:String) {
            val gson = Gson()
            File(QuizApp.applicationContext().filesDir, "questions.json").writeText(jsonStr)
            localStore()
        }

        override fun localStore() {
            var file_path = "questions.json"
            val alternate_file_path = "alternative_questions.json"
            val gson = Gson()
            if (File(applicationContext().filesDir, file_path).exists()) {
                topics = gson.fromJson(File(applicationContext().filesDir, (file_path)).bufferedReader().use {
                    it.readText()
                }, Array<topic>::class.java)
            } else {
                topics = gson.fromJson(applicationContext().assets.open(alternate_file_path).bufferedReader().use {
                    it.readText()
                }, Array<topic>::class.java)
            }
        }

        override fun getTopics(): Array<topic> {
            return topics
        }
    }
}