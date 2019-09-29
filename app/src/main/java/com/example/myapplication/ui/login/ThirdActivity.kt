package com.example.myapplication.ui.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.daimajia.numberprogressbar.OnProgressBarListener
import com.example.myapplication.R
import kotlinx.android.synthetic.main.activity_third.*
import kotlinx.coroutines.*
import org.jetbrains.anko.UI
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class ThirdActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_third)
        val position:String? = intent.getStringExtra("position")
        thirdtv.setText("position is $position")

//        doAsync {
//            for (progress in 0..10){
//                Log.d("Third","$progress")
//                uiThread { progressbar.progress = progress}
//            }
//        }

        Log.d("Third","result is ${test(5)}")

    }



    fun load(){
        GlobalScope.launch(Dispatchers.Main) {
            val deferred = GlobalScope.async(Dispatchers.IO) {
                for (progress in 0..100){
                    delay(100)
                    return@async progress
                }

            }
                var result = deferred.await()
                progressbar.progress = result.toString().toInt()
        }
    }

    public fun test( a: Int) : Int{
        var result = 1
        for (i in 1..a){
            result *= i
        }
        return result
    }

}