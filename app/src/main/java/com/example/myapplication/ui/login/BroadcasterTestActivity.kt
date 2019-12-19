package com.example.myapplication.ui.login

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.R
import kotlinx.android.synthetic.main.activity_broadcasttest.*

class BroadcasterTestActivity : AppCompatActivity(){

    var mBroadcastReceiver = mBroadcaster()
    var intentFilter = IntentFilter()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_broadcasttest)


        btn_broad.setOnClickListener {
            var intent = Intent()
            intent.putExtra("message","You success")
            intent.action = Context.CONNECTIVITY_SERVICE
            sendBroadcast(intent)
        }
    }

    class mbroadcastreceiver : BroadcastReceiver(){
        override fun onReceive(p0: Context?, p1: Intent?) {

        }

    }

    override fun onResume() {
        super.onResume()
        intentFilter.addAction(Context.CONNECTIVITY_SERVICE)
        registerReceiver(mBroadcastReceiver,intentFilter)
    }

    override fun onPause() {
        super.onPause()
        unregisterReceiver(mBroadcastReceiver)
    }
}