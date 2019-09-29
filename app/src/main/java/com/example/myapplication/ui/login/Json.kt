package com.example.myapplication.ui.login

import org.json.JSONException
import org.json.JSONObject

class Json {
    fun Jsonit(key:String,jsonString: String) : String{
        var str = ""
        try {
             str = JSONObject(jsonString).getString(key)
        }catch (e : JSONException){
            e.printStackTrace()
        }
        return str
    }


}