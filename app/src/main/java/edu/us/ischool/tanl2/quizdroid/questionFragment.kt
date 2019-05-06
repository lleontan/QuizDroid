package edu.us.ischool.tanl2.quizdroid

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import kotlinx.android.synthetic.main.layout_qa.*
import kotlinx.android.synthetic.main.overview_view.*
import layout.question
import java.io.Serializable



class questionFragment:Fragment() {

    private lateinit var questions:List<question>
    private lateinit var name:String
    private lateinit var description:String
    companion object {
        fun newInstance(questions:List<question>, name:String, description:String,qIndex:Int,
                        right:Int,wrong:Int): questionFragment {

            val args = Bundle()
            args.putString("name", name)
            args.putSerializable("questions", questions as Serializable)
            args.putString("description", description)
            args.putInt("qIndex", qIndex)
            args.putInt("right", right)
            args.putInt("wrong", wrong)
            val fragment = questionFragment()
            fragment.arguments = args
            return fragment
        }
    }
     override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
         submitButton.isEnabled=false

         questions=arguments?.getSerializable("questions") as ArrayList<question>
         val topicName=arguments?.getString("name") as String
         q_topic_name.text=topicName
         var qIndex:Int=arguments?.getInt("qIndex",0)!!
         var question=questions[qIndex]
         question_name.text=question.question
         var rightAnswers:Int=arguments?.getInt("right",0)!!
         var wrongAnswers:Int=arguments?.getInt("wrong",0)!!
         var count=0
         question.answers.map {
             var newButton: RadioButton = RadioButton(activity)
             newButton.text=it
             newButton.id=count
             newButton.setOnClickListener {
                 submitButton.isEnabled=true
             }
             radios.addView(newButton)
             count=count+1
         }
         submitButton.setOnClickListener {
             val transaction = fragmentManager!!.beginTransaction()

             if(radios.findViewById<RadioButton>(question.answerIndex).isChecked){
                 rightAnswers++
             }else{
                 wrongAnswers++
             }
             val qFrag = questionFragment.newInstance(questions,name,description,qIndex+1,rightAnswers,wrongAnswers)

             transaction.replace(R.id.view_container,qFrag)
             transaction.addToBackStack(null)
             transaction.commit()         }
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(R.layout.layout_qa, container, false)
        // Inflate the layout for this fragment
        return view
    }
}