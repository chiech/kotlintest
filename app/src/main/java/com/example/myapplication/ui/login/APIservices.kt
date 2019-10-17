package com.example.myapplication.ui.login

import com.google.gson.JsonArray
import org.json.JSONArray
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface APIservices {
    @GET("Api/V1/CrystalBall/ss")
    fun getData() : Call<Data>

    @POST("Api/V1/Palm/Check")
    fun getCall(@Body answer : JSONArray) : Call<Answer>
}