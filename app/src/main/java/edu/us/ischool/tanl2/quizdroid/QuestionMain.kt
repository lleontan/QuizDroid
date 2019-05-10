package edu.us.ischool.tanl2.quizdroid

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import layout.Quiz

class QuestionMain : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_layout)
        intent
        val questions=intent.getSerializableExtra("questions")!! as ArrayList<Quiz>
        val name=intent.getStringExtra("title") as String
        val description=intent.getStringExtra("desc") as String
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        val fragment = overviewFragment.newInstance(questions,name,description)
        fragmentTransaction.add(R.id.view_container, fragment)
        fragmentTransaction.commit()
    }
}
