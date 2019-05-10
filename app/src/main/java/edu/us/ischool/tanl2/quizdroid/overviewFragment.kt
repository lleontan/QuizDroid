package edu.us.ischool.tanl2.quizdroid

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.overview_view.*
import layout.Quiz
import java.io.Serializable
import android.widget.TextView







class overviewFragment:Fragment() {
    private lateinit var Quizzes:List<Quiz>
    private lateinit var name:String
    private lateinit var description:String
    companion object {
        fun newInstance(Quizzes:List<Quiz>, name:String, description:String): overviewFragment {

            val args = Bundle()
            args.putString("title", name)
            args.putSerializable("questions", Quizzes as Serializable)
            args.putString("desc", description)
            val fragment = overviewFragment()
            fragment.arguments = args
            return fragment
        }
    }
     override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(R.layout.overview_view, container, false)

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        Quizzes=arguments?.getSerializable("questions")!! as ArrayList<Quiz>
        name=arguments?.getString("title") as String
        description=arguments?.getString("desc") as String
        val topic_name = activity!!.findViewById<TextView>(R.id.topic_name)
        topic_name.text=name
        topic_description.text=description
        procede.setOnClickListener {
            val qFrag = questionFragment.newInstance(Quizzes,name,description,0,0,0)
            val transaction = fragmentManager!!.beginTransaction()
            transaction.replace(R.id.view_container,qFrag)
            transaction.addToBackStack(null)
            transaction.commit()
        }
    }
}