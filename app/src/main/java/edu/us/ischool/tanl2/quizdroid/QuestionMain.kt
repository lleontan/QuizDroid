package edu.us.ischool.tanl2.quizdroid

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.overview_view.*
import layout.question
import java.io.Serializable

class QuestionMain : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_layout)
        intent
        val questions=intent.getSerializableExtra("questions")!! as ArrayList<question>
        val name=intent.getStringExtra("name") as String
        val description=intent.getStringExtra("description") as String
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        val fragment = overviewFragment.newInstance(questions,name,description)
        fragmentTransaction.add(R.id.view_container, fragment)
        fragmentTransaction.commit()
    }
}
