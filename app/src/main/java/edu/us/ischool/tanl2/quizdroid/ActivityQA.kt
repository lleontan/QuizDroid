package edu.us.ischool.tanl2.quizdroid

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.RadioButton
import kotlinx.android.synthetic.main.layout_qa.*

import kotlinx.android.synthetic.main.overview_view.*
import layout.question
import layout.topic
import java.io.Serializable

class ActivityQA : AppCompatActivity() {
    private lateinit var questions:List<question>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.layout_qa)
        submitButton.isEnabled=false

        questions=intent?.getSerializableExtra("questions") as ArrayList<question>
        val topicName=intent?.getStringExtra("name") as String
        q_topic_name.text=topicName
        var qIndex:Int=intent.getIntExtra("qIndex",0)
        var question=questions[qIndex]
        question_name.text=question.question
        var rightAnswers:Int=intent.getIntExtra("rightCount",0)
        var wrongAnswers:Int=intent.getIntExtra("wrongCount",0)
        var count=0
        question.answers.map {
            var newButton:RadioButton=RadioButton(this)
            newButton.text=it
            newButton.id=count
            newButton.setOnClickListener {
                submitButton.isEnabled=true
            }
            radios.addView(newButton)
            count=count+1
        }
        submitButton.setOnClickListener {
            val intent = Intent(this, AnswerActivity::class.java)
            if(radios.findViewById<RadioButton>(question.answerIndex).isChecked){
                rightAnswers++
            }else{
                wrongAnswers++
            }

            intent.putExtra("questions", questions as Serializable)
            intent.putExtra("name", topicName)
            intent.putExtra("rightCount", rightAnswers)
            intent.putExtra("wrongCount", wrongAnswers)
            intent.putExtra("qIndex", qIndex+1)

            startActivity(intent)
        }
    }
}
