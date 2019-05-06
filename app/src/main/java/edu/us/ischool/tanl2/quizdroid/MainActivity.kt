package edu.us.ischool.tanl2.quizdroid

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

import kotlinx.android.synthetic.main.activity_main.*
import layout.question
import layout.topic
import java.io.Serializable

class MainActivity : AppCompatActivity() {
    private lateinit var topics:Array<topic>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        topics= arrayOf(topic("Math","Literally Math",
            arrayListOf(question("1+1", arrayOf("0","1","2","3"),2),
                question("1+1", arrayOf("0","1","2","3"),2),
                question("2+1", arrayOf("0","1","2","3"),3),
                question("0*0", arrayOf("0","1","2","3"),0),
                question("10%4", arrayOf("0","1","2","3"),2))
            ),topic("Physics","An illuminati plot",
            arrayListOf(question("Moon landing?", arrayOf("Faked","An inside Job","Real","Faked News"),2),
                question("How flat is the earth", arrayOf("As a pancake","Like a disc","Round like a ball","Doughnut"),2),
                question("Albert ...", arrayOf("Einstein","Schmidt","Baker","Smith"),0),
                question("The mitochondria", arrayOf("powerhouse","Powerhouse","POWERHOUSE","POWERHOUSEOFTHECELL"),3),
                question("WHAT is the airspeed velocity of an unladen swallow?", arrayOf("0","1","2","African or european?"),3))
            ),topic("Marvel Super Heroes","Yeeeet",
                arrayListOf(question("Thanos", arrayOf("Clicks","Snaps","Taps","Hums"),1),
                    question("Antman is prophesied to infiltrate", arrayOf("Thanos","A bank","The government","All of the above"),3),
                    question("Nanananana", arrayOf("Batman","Spiderman","SpiderHam","Spider Sam"),0),
                    question("Flat Asgard Theory", arrayOf("As a pancake","Like a disc","Round like a ball","Doughnut"),1),
                    question("Hottest Avenger", arrayOf("Batman","Superman","AntDude","Thanos"),2))
            )
        )
        topics.map {newTopic:topic ->
            var newButton:Button=Button(this)
            newButton.text=newTopic.name
                newButton.setOnClickListener {
                val intent = Intent(this, QuestionMain::class.java)
                intent.putExtra("questions", newTopic.questions as Serializable)
                    intent.putExtra("name", newTopic.name)
                    intent.putExtra("description", newTopic.description)
                    startActivity(intent)
            }
            topic_list.addView(newButton)

        }
    }
}
