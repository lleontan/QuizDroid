package edu.us.ischool.tanl2.quizdroid

import android.app.Application
import android.util.Log
import layout.Quiz
import layout.topic

class QuizApp: Application() {
    private lateinit var repo:TopicRepository
    fun getTopicStore():TopicRepository{
        return repo
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
            topics=arrayOf(
                topic("Math","Literally Math","MathMathMAth",
                    arrayListOf(
                        Quiz("1+1", arrayOf("0","1","2","3"),2),
                        Quiz("1+1", arrayOf("0","1","2","3"),2),
                        Quiz("2+1", arrayOf("0","1","2","3"),3),
                        Quiz("0*0", arrayOf("0","1","2","3"),0),
                        Quiz("10%4", arrayOf("0","1","2","3"),2)
                    )
                ), topic("Physics","An illuminati plot","Applied Math",
                    arrayListOf(
                        Quiz("Moon landing?", arrayOf("Faked","An inside Job","Real","Faked News"),2),
                        Quiz("How flat is the earth", arrayOf("As a pancake","Like a disc","Round like a ball","Doughnut"),2),
                        Quiz("Albert ...", arrayOf("Einstein","Schmidt","Baker","Smith"),0),
                        Quiz("The mitochondria", arrayOf("powerhouse","Powerhouse","POWERHOUSE","POWERHOUSEOFTHECELL"),3),
                        Quiz("WHAT is the airspeed velocity of an unladen swallow?", arrayOf("0","1","2","African or european?"),3)
                    )
                ), topic("Marvel Super Heroes","Yeeeet","Antman x Thanos, I ship it.",
                    arrayListOf(
                        Quiz("Thanos", arrayOf("Clicks","Snaps","Taps","Hums"),1),
                        Quiz("Antman is prophesied to infiltrate", arrayOf("Thanos","A bank","The government","All of the above"),3),
                        Quiz("Nanananana", arrayOf("Batman","Spiderman","SpiderHam","Spider Sam"),0),
                        Quiz("Flat Asgard Theory", arrayOf("As a pancake","Like a disc","Round like a ball","Doughnut"),1),
                        Quiz("Hottest Avenger", arrayOf("Batman","Superman","AntDude","Thanos"),2)
                    )
                )
            )
        }

        override fun getTopics():Array<topic> {
            return topics
        }
    }
}