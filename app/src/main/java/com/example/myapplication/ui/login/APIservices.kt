package com.example.myapplication.ui.login

import com.google.gson.JsonArray
import okhttp3.RequestBody
import org.json.JSONArray
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST


interface APIservices {


    @GET("Api/V1/CrystalBall/ss")
    fun getData() : Call<Data>

    @Headers("Content-Type: application/json", "Accept: */*")
    @POST("Api/V1/Palm/Check")
    fun getCall(@Body answer : RequestBody) : Call<Answer>

    @POST("list?appKey=be3ac46843914f5cbe875defccd8f392")
    fun getlist(@Body body : RequestBody) : Call<RequestBody>
}