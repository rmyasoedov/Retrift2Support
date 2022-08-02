package com.example.supportretrofit2.retrofit

import com.google.gson.JsonElement
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface INetCleintAPI {
    @GET("posts")
    fun getPosts() : Call<JsonElement>

    @GET("posts/{id}")
    fun getOnlyPost(@Path("id") id: Int) : Call<JsonElement>
}