package edu.us.ischool.tanl2.quizdroid

import layout.topic

interface TopicRepository {
    fun store()
    fun getTopics(): kotlin.Array<topic>
}