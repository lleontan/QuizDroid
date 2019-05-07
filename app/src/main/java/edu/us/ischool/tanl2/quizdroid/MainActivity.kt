package edu.us.ischool.tanl2.quizdroid

import android.app.Application
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

import kotlinx.android.synthetic.main.activity_main.*
import layout.topic
import java.io.Serializable

class MainActivity : AppCompatActivity() {
    private lateinit var topics:Array<topic>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val topicRep=application as QuizApp
        topics=topicRep.getTopicStore().getTopics()
        topics.map {newTopic:topic ->
            var newButton:Button=Button(this)
            val name:String=newTopic.name
            val desc:String=newTopic.description
            val bText="$name\n$desc"
            newButton.text=bText
                newButton.setOnClickListener {
                val intent = Intent(this, QuestionMain::class.java)
                intent.putExtra("Quizzes", newTopic.Quizzes as Serializable)
                    intent.putExtra("name", newTopic.name)
                    intent.putExtra("description", newTopic.longDescription)
                    startActivity(intent)
            }
            topic_list.addView(newButton)

        }
    }
}
