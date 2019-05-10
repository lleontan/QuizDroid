package edu.us.ischool.tanl2.quizdroid

import android.app.Application
import android.content.SharedPreferences
import android.preference.PreferenceManager

import android.util.Log
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
            var urlRegex=Regex("^+.{3}+.{3}+$")
            if(urlRegex.matches(url)){
                jsonString=URL(url).readText()
            }else{
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