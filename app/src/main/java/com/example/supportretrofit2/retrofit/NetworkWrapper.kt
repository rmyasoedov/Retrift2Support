package com.example.supportretrofit2.retrofit

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async

class NetworkWrapper {

    suspend fun getPosts(): Response.MyResponse{
        var request = NetworkService().getInstance()?.getJSONApi()

        var result = Response().getResponse(
            GlobalScope.async {
                    request?.getPosts()!!.execute()
            }
        )

        return result
    }

    suspend fun getOnlyPost(id: Int): Response.MyResponse{
        var request = NetworkService().getInstance()?.getJSONApi()

        var result = Response().getResponse(
            GlobalScope.async {
                request?.getOnlyPost(id)!!.execute()
            }
        )
        return result
    }
}