package edu.us.ischool.tanl2.quizdroid

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.answer_layout.*

import layout.Quiz
import java.io.Serializable

class AnswerActivity : AppCompatActivity() {
    private lateinit var Quizzes: List<Quiz>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.answer_layout)
        Quizzes = intent?.getSerializableExtra("Quizzes") as ArrayList<Quiz>
        val topicName = intent?.getStringExtra("name") as String
        a_topic_name.text = topicName
        var qIndex: Int = intent.getIntExtra("qIndex", 0)
        if(qIndex!=Quizzes.size){
            var question = Quizzes[qIndex]
            next_question.text = question.question
        }
        var rightAnswers: Int = intent.getIntExtra("rightCount",0)
        var wrongAnswers: Int = intent.getIntExtra("wrongCount", 0)
        right_answers.text = "$rightAnswers right"
        wrong_answers.text = "$wrongAnswers wrong"

        if (qIndex >= Quizzes.size) {
            next_question.text = "Finish"
            next_question.setOnClickListener {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }
        } else {
            next_question.setOnClickListener {
                val intent = Intent(this, ActivityQA::class.java)
                intent.putExtra("Quizzes", Quizzes as Serializable)
                intent.putExtra("qIndex", qIndex)
                intent.putExtra("name", topicName)
                intent.putExtra("rightCount", rightAnswers)
                intent.putExtra("wrongCount", wrongAnswers)
                startActivity(intent)
            }
            next_question.text = "Next Question"
        }
    }
}
