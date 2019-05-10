package edu.us.ischool.tanl2.quizdroid

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

import kotlinx.android.synthetic.main.activity_main.*
import layout.topic
import java.io.Serializable
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class MainActivity : AppCompatActivity() {
    private lateinit var topics:Array<topic>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val topicRep=application as QuizApp
        topicRep.getTopicStore().store()
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
        preferences_button.setOnClickListener {
            val intent = Intent(this, Preferences::class.java)
            startActivity(intent)
        }
    }
}
