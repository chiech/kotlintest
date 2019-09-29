package com.example.myapplication.ui.login

import android.content.Context
import android.os.Bundle
import android.provider.ContactsContract
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.UiThread
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.Request
import com.example.myapplication.R
import com.google.gson.Gson
import com.google.gson.JsonArray
import kotlinx.android.synthetic.main.activity_second.*
import kotlinx.coroutines.*
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Response
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.uiThread
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException
import java.net.URL


class SecondActivity : AppCompatActivity(),CoroutineScope by MainScope(){

    val jsonString = "{\"name\":\"wang\",\"age\":\"18\",\"job\":\"student\"}"
    val jsonArrayString = "[{\"l1\":\"demo\",\"l2\":2},{\"l1\":\"demo\",\"l2\":2}]"

    var itemlist = ArrayList<String>()
    var adapter : MyAdapter? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        fun ArrayList<String>.getData(){
            for(i in 0..20){
                this.add("str:"+i)
            }
        }

        itemlist.getData()
        adapter = MyAdapter(itemlist,this)
        listview.adapter = adapter

        listview.setOnItemClickListener { parent, view, position, id ->
            Toast.makeText(this,"you click $position item",Toast.LENGTH_LONG).show()
            startActivity<ThirdActivity>("position" to "$position")

        }

        buttonin.setOnClickListener { startActivity<LoginActivity>("name" to "wang","age" to 11) }

        buttonnet.setOnClickListener {
            doAsync {
                val result = URL("https://translate.dollarkiller.com/translate?sl=&tl=fr&text=helo").readText()
                uiThread { show.setText(result) }
            }
        }

        buttontest.setOnClickListener { net() }
    }



    fun load(){
        GlobalScope.launch(Dispatchers.Unconfined) {
            val deferred = GlobalScope.async {

                val data = step1()
                step2(data)
                return@async data
            }
            val result = deferred.await()
            show.setText("$result")
        }
    }



    fun net(){
        val path = "https://translate.dollarkiller.com/translate?sl=&tl=fr&text=helo"
        val client = OkHttpClient()
        val request = okhttp3.Request.Builder()
            .url(path)
            .get()
            .build()
        client.newCall(request).enqueue(object : Callback{
            override fun onFailure(call: Call, e: IOException) {
                println("failure")
            }

            override fun onResponse(call: Call, response: Response) {
                val result = response?.body?.string()
                println("success and result is $result")
            }

        })
    }


    fun step1():String {
        Log.d("Sec","step1 is running , time is ${System.currentTimeMillis()}")
        return "step1 result"
    }

    fun step2(data : String){
        Log.d("Sec","step2 is running , time is ${System.currentTimeMillis()},get step1 data : $data")
    }

    class MyAdapter(var list: ArrayList<String>,var ctx : Context) : BaseAdapter(){

        override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
            var viewHolder : ViewHolder
            var view : View
            if (p1 == null){
                view = View.inflate(ctx,R.layout.item_listview,null)
                viewHolder = ViewHolder(view)
                view.tag = viewHolder
            }else{
                view = p1
                viewHolder = view.tag as ViewHolder
            }
                viewHolder.str.text = list[p0]
                return view
        }

        override fun getItem(p0: Int): Any {
            return list.get(p0)
        }

        override fun getItemId(p0: Int): Long {
            return p0.toLong()
        }

        override fun getCount(): Int {
            return list.size
        }
    }

    class ViewHolder(var view: View){
        var str : TextView = view.findViewById(R.id.tv) as TextView
    }


}