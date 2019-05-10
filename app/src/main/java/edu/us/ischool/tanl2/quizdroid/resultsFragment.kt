package edu.us.ischool.tanl2.quizdroid

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.answer_layout.*
import layout.Quiz
import java.io.Serializable


class resultsFragment : Fragment() {

    private lateinit var Quizzes: List<Quiz>
    private lateinit var name: String
    private lateinit var description: String

    companion object {
        fun newInstance(Quizzes:List<Quiz>, name:String, description:String, qIndex:Int,
                        right:Int, wrong:Int): resultsFragment {

            val args = Bundle()
            args.putString("title", name)
            args.putSerializable("questions", Quizzes as Serializable)
            args.putString("desc", description)
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
        Quizzes = arguments?.getSerializable("questions") as ArrayList<Quiz>
        name = arguments?.getString("title") as String
        description = arguments?.getString("desc") as String

        a_topic_name.text = name
        var qIndex: Int = arguments?.getInt("qIndex", 0)!!
        if (qIndex != Quizzes.size) {
            var question = Quizzes[qIndex]
            next_question.text = question.text
        }
        var rightAnswers: Int = arguments?.getInt("right", 0)!!
        var wrongAnswers: Int = arguments?.getInt("wrong", 0)!!
        right_answers.text = "$rightAnswers right"
        wrong_answers.text = "$wrongAnswers wrong"
        val transaction = fragmentManager!!.beginTransaction()
        if (qIndex >= Quizzes.size) {
            next_question.text = "Finish"
            next_question.setOnClickListener {
                val intent = Intent(activity, MainActivity::class.java)
                startActivity(intent)
            }
        } else {
            next_question.setOnClickListener {
                val qFrag =
                    questionFragment.newInstance(Quizzes, name, description, qIndex, rightAnswers, wrongAnswers)
                transaction.replace(R.id.view_container, qFrag)
                transaction.addToBackStack(null)
                transaction.commit()
            }
            next_question.text = "Next Question"
        }
    }
}