package edu.us.ischool.tanl2.quizdroid

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import kotlinx.android.synthetic.main.layout_qa.*
import layout.Quiz
import java.io.Serializable



class questionFragment:Fragment() {

    private lateinit var Quizzes:List<Quiz>
    private lateinit var name:String
    private lateinit var description:String
    companion object {
        fun newInstance(Quizzes:List<Quiz>, name:String, description:String, qIndex:Int,
                        right:Int, wrong:Int): questionFragment {

            val args = Bundle()
            args.putString("name", name)
            args.putSerializable("Quizzes", Quizzes as Serializable)
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

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        // Inflate the layout for this fragment
        submitButton.isEnabled=false

        Quizzes=arguments?.getSerializable("Quizzes") as ArrayList<Quiz>
        name=arguments?.getString("name") as String
        description=arguments?.getString("description") as String
        q_topic_name.text=name
        var qIndex:Int=arguments?.getInt("qIndex",0)!!
        var question=Quizzes[qIndex]
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
            val qFrag = resultsFragment.newInstance(Quizzes,name,description,qIndex+1,rightAnswers,wrongAnswers)

            transaction.replace(R.id.view_container,qFrag)
            transaction.addToBackStack(null)
            transaction.commit()
        }
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(R.layout.layout_qa, container, false)

        return view
    }
}