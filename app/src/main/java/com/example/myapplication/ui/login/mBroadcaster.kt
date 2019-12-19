package com.example.myapplication.ui.login

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_broadcasttest.*

class mBroadcaster : BroadcastReceiver(){

//    var broadcasterTestActivity = BroadcasterTestActivity()

    override fun onReceive(p0: Context?, p1: Intent?) {
        Log.d("broadcast","I received message")
//        broadcasterTestActivity.textView.text = "I received message"
    }
}