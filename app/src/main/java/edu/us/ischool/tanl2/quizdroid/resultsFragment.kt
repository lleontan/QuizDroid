package edu.us.ischool.tanl2.quizdroid

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import kotlinx.android.synthetic.main.answer_layout.*
import kotlinx.android.synthetic.main.layout_qa.*
import kotlinx.android.synthetic.main.overview_view.*
import layout.question
import java.io.Serializable


class resultsFragment : Fragment() {

    private lateinit var questions: List<question>
    private lateinit var name: String
    private lateinit var description: String

    companion object {
        fun newInstance(questions:List<question>, name:String, description:String,qIndex:Int,
                        right:Int,wrong:Int): resultsFragment {

            val args = Bundle()
            args.putString("name", name)
            args.putSerializable("questions", questions as Serializable)
            args.putString("description", description)
            args.putInt("qIndex", qIndex)
            args.putInt("right", right)
            args.putInt("wrong", wrong)
            val fragment = resultsFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(R.layout.answer_layout, container, false)
        // Inflate the layout for this fragment

        return view
    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        questions = arguments?.getSerializable("questions") as ArrayList<question>
        name = arguments?.getString("name") as String
        description = arguments?.getString("description") as String

        a_topic_name.text = name
        var qIndex: Int = arguments?.getInt("qIndex", 0)!!
        if (qIndex != questions.size) {
            var question = questions[qIndex]
            next_question.text = question.question
        }
        var rightAnswers: Int = arguments?.getInt("right", 0)!!
        var wrongAnswers: Int = arguments?.getInt("wrong", 0)!!
        right_answers.text = "$rightAnswers right"
        wrong_answers.text = "$wrongAnswers wrong"
        val transaction = fragmentManager!!.beginTransaction()
        if (qIndex >= questions.size) {
            next_question.text = "Finish"
            next_question.setOnClickListener {
                val overviewFrag = overviewFragment.newInstance(questions, name, description)
                transaction.replace(R.id.view_container, overviewFrag)
                transaction.addToBackStack(null)
                transaction.commit()
            }
        } else {
            next_question.setOnClickListener {
                val qFrag =
                    questionFragment.newInstance(questions, name, description, qIndex, rightAnswers, wrongAnswers)
                transaction.replace(R.id.view_container, qFrag)
                transaction.addToBackStack(null)
                transaction.commit()
            }
            next_question.text = "Next Question"
        }
    }
}