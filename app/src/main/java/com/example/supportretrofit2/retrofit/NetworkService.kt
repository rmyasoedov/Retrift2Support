package com.example.supportretrofit2.retrofit

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class NetworkService {

    private var mInstance: NetworkService? = null
    private val BASE_URL = "https://jsonplaceholder.typicode.com/"
    private var mRetrofit: Retrofit? = null

    constructor() {
        mRetrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    fun getInstance() : NetworkService? {
        if (mInstance == null) {
            mInstance = NetworkService()
        }
        return mInstance
    }

    fun getJSONApi(): INetCleintAPI? {
        return mRetrofit!!.create(INetCleintAPI::class.java)
    }
}
