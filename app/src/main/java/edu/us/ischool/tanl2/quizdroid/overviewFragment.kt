package edu.us.ischool.tanl2.quizdroid

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.overview_view.*
import layout.question
import java.io.Serializable



class overviewFragment:Fragment() {

    private lateinit var questions:List<question>
    private lateinit var name:String
    private lateinit var description:String
    companion object {
        fun newInstance(questions:List<question>, name:String, description:String): overviewFragment {

            val args = Bundle()
            args.putString("name", name)
            args.putSerializable("questions", questions as Serializable)
            args.putString("description", description)
            val fragment = overviewFragment()
            fragment.arguments = args
            return fragment
        }
    }
     override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

         questions=arguments?.getSerializable("questions")!! as ArrayList<question>
         name=arguments?.getString("name") as String
         description=arguments?.getString("description") as String
         topic_name.text=name
         topic_description.text=description
         procede.setOnClickListener {
             val qFrag = questionFragment.newInstance(questions,name,description,0,0,0)
             val transaction = fragmentManager!!.beginTransaction()
             transaction.replace(R.id.view_container,qFrag)
             transaction.addToBackStack(null)
             transaction.commit()
         }
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(R.layout.overview_view, container, false)
        // Inflate the layout for this fragment
        return view
    }
}