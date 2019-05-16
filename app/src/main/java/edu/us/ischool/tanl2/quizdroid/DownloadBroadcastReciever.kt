package edu.us.ischool.tanl2.quizdroid

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast

class DownloadBroadcastReciever() : BroadcastReceiver() {
    public lateinit var listener:downloadListener
    override fun onReceive(context: Context, intent:Intent) {
        MainActivity.mainActivityContext().fetchData()
        Log.v("network","Alarm activated")
    }
}