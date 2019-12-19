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
import com.alibaba.fastjson.JSON
import com.bumptech.glide.Glide
import com.bumptech.glide.request.Request
import com.example.myapplication.R
import com.google.gson.Gson
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import kotlinx.android.synthetic.main.activity_second.*
import kotlinx.coroutines.*
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.uiThread
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.*
import java.net.URL


class SecondActivity : AppCompatActivity(),CoroutineScope by MainScope(){

    val jsonString = "{\"name\":\"wang\",\"age\":\"18\",\"job\":\"student\"}"



    var itemlist = ArrayList<String>()
    var adapter : MyAdapter? = null
    var bufferedWriter : BufferedWriter? = null
    var bufferedReader : BufferedReader? = null

    val NEWS_URL : String = "http://api.shujuzhihui.cn/api/news/"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        var js  = JSONArray()
        var jsb  = JSONObject()
        var jsb1 = JSONObject()

        jsb.put("id",0)
        jsb.put("answer","b")
        jsb1.put("id",1)
        jsb1.put("answer","a")

        js.put(jsb)
        js.put(jsb1)
        var answer :String? = null

        fun ArrayList<String>.addData(){
            for (i in 0..js.length()-1){
                var obj : JSONObject = js.get(i) as JSONObject
                this.add(obj.getString("answer"))
            }
        }
        itemlist.addData()
        adapter = MyAdapter(itemlist,this)
        listview.adapter = adapter

        listview.setOnItemClickListener { parent, view, position, id ->
            Toast.makeText(this,"you click $position item",Toast.LENGTH_LONG).show()
            startActivity<ThirdActivity>("position" to "$position")

        }

        buttonbroadtest.setOnClickListener { startActivity<BroadcasterTestActivity>() }

        buttonin.setOnClickListener { startActivity<LoginActivity>("name" to "wang","age" to 11) }

        buttonnet.setOnClickListener {

            var js  = JSONArray()
            var jsb  = JSONObject()
            var jsb1 = JSONObject()

            jsb.put("id",0)
            jsb.put("answer","b")
            jsb1.put("id",1)
            jsb1.put("answer","a")

            js.put(jsb)
            js.put(jsb1)
            var answer :String? = null

            fun ArrayList<String>.addData(){
                for (i in 0..js.length()-1){
                    var obj : JSONObject = js.get(i) as JSONObject
                    this.add(obj.getString("answer"))
                }
            }
            itemlist.addData()
            adapter = MyAdapter(itemlist,this)
            listview.adapter = adapter
//            show.setText("${js.toString()}")
//            show.setText("${jsb.toString()}")
        }

        buttontest.setOnClickListener { retrofit2() }
        //保存文件
        savebtn.setOnClickListener { savefile() }
        //读取文件
        readfile()
    }

    fun savefile(){
        var text = edittext.text.toString()
        try {
            var fileoutputstream = openFileOutput("myapplicationdata", Context.MODE_APPEND)
            bufferedWriter = BufferedWriter(OutputStreamWriter(fileoutputstream))
            bufferedWriter!!.write(text)
        }catch (e : FileNotFoundException){
            e.printStackTrace()
        }catch (e : IOException){
            e.printStackTrace()
        }finally {
            try {
                bufferedWriter!!.close()
            }catch (e : IOException){
                e.printStackTrace()
            }
        }
    }

    fun readfile(){
        var stringBuilder = StringBuilder()
        try {
            var fileInputStream = openFileInput("myapplicationdata")
            bufferedReader = BufferedReader(InputStreamReader(fileInputStream))
            stringBuilder.append(bufferedReader!!.readLine())
        }catch (e : FileNotFoundException){
            e.printStackTrace()
        }catch (e : IOException){
            e.printStackTrace()
        }finally {
            try {
                bufferedReader!!.close()
            }catch (e : FileNotFoundException){
                e.printStackTrace()
            }
        }
        var string = stringBuilder.toString()
        show.text = string
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

    fun retrofit(){
        var retrofit = Retrofit.Builder()
            .baseUrl("http://192.168.50.177:8081/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val ipService = retrofit.create(APIservices::class.java)
        ipService.getData().enqueue(object : retrofit2.Callback<Data>{
            override fun onFailure(call: retrofit2.Call<Data>?, t: Throwable?) {
                print("response failture")
            }

            override fun onResponse(
                call: retrofit2.Call<Data>?,
                response: retrofit2.Response<Data>?
            ) {
                var data : Data = response!!.body()
                show.text = "${data.data}"
                print("${response.body().data}")
            }

        })
    }

    fun retrofit2(){
        var jsonArray = JSONArray()

        var jsonObject = JSONObject()
        var jsonObject1 = JSONObject()
        jsonObject.put("id",0)
        jsonObject.put("answer","a")
        jsonObject1.put("id",1)
        jsonObject1.put("answer","b")
        jsonArray.put(jsonObject)
        jsonArray.put(jsonObject1)

        var retrofit = Retrofit.Builder()
            .baseUrl("http://192.168.50.177:8081/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        var se = retrofit.create(APIservices::class.java)

        var body : requestBody = requestBody(0,"a")
        var gson : Gson = Gson()
        var obj : String = gson.toJson(body)

        val requestBody = RequestBody.create("application/json".toMediaTypeOrNull(),jsonObject.toString())

        se.getCall(requestBody).enqueue(object :retrofit2.Callback<Answer>{
            override fun onFailure(call: retrofit2.Call<Answer>?, t: Throwable?) {

            }

            override fun onResponse(
                call: retrofit2.Call<Answer>?,
                response: retrofit2.Response<Answer>?
            ) {
                var data : Answer = response!!.body()
                show.text ="${data.data!![0]}"
            }

        })
    }

    fun test(){
        var mretrofit = Retrofit.Builder()
            .baseUrl(NEWS_URL)
            .build()

        var apIservices = mretrofit.create(APIservices::class.java)

    }


    fun getJson(json : JSONArray){
        for (i in 0..json.length() - 1){
            var obj : JSONObject = json.get(i) as JSONObject
            var obj_id = obj.getInt("id")
            var list  = arrayOf(obj_id)
        }
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