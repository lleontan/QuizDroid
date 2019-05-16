package edu.us.ischool.tanl2.quizdroid

import android.content.Context
import layout.topic

interface TopicRepository {
    fun store(jsonStr: String)
    fun fetchData()
    fun localStore()
    fun getTopics(): kotlin.Array<topic>
}