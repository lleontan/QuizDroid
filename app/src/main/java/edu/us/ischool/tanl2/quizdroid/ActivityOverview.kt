package edu.us.ischool.tanl2.quizdroid

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle

import kotlinx.android.synthetic.main.overview_view.*
import layout.Quiz
import java.io.Serializable

class ActivityOverview : AppCompatActivity() {
    private lateinit var Quizzes:List<Quiz>
    private lateinit var name:String
    private lateinit var description:String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.overview_view)
        intent
        Quizzes=intent.getSerializableExtra("Quizzes")!! as ArrayList<Quiz>
        name=intent.getStringExtra("name") as String
        description=intent.getStringExtra("description") as String
        topic_name.text=name
        topic_description.text=description
        procede.setOnClickListener {
            val intent = Intent(this, ActivityQA::class.java)
            intent.putExtra("Quizzes", Quizzes as Serializable)
            intent.putExtra("name", name)
            intent.putExtra("description", description)
            intent.putExtra("qIndex",0)
            intent.putExtra("rightCount",0)
            intent.putExtra("wrongCount",0)
            startActivity(intent)
        }
    }
}
