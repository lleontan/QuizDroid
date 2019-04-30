package edu.us.ischool.tanl2.quizdroid

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.overview_view.*
import layout.question
import layout.topic
import java.io.Serializable

class ActivityOverview : AppCompatActivity() {
    private lateinit var questions:List<question>
    private lateinit var name:String
    private lateinit var description:String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.overview_view)
        intent
        questions=intent.getSerializableExtra("questions")!! as ArrayList<question>
        name=intent.getStringExtra("name") as String
        description=intent.getStringExtra("description") as String
        topic_name.text=name
        topic_description.text=description
        procede.setOnClickListener {
            val intent = Intent(this, ActivityQA::class.java)
            intent.putExtra("questions", questions as Serializable)
            intent.putExtra("name", name)
            intent.putExtra("description", description)
            intent.putExtra("qIndex",0)
            intent.putExtra("rightCount",0)
            intent.putExtra("wrongCount",0)
            startActivity(intent)
        }
    }
}
