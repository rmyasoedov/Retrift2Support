package com.example.supportretrofit2.presenter

import com.example.supportretrofit2.model.ModelArticles
import com.example.supportretrofit2.retrofit.NetworkWrapper
import com.example.supportretrofit2.view.PostView
import com.google.gson.Gson
import com.google.gson.JsonObject
import java.lang.Exception

class PostPresenter(private val viewState: PostView) {
    suspend fun getOnlyPost(id: Int){
        try {
            val response = NetworkWrapper().getOnlyPost(id)

            //Если в запросе была ошибка, то вызываем исключение и передаем код ошибки
            if(response.error)
                throw Exception("Ошибка! "+response.e?.message.toString())

            //Если ответ сервера не 200, то также вызываем исключение с передачи кода ответа сервера
            if(response.code!=200)
                throw Exception("Ошибка! Ответ сервера: "+response.code)

            val obj = response.body as JsonObject

            val post = Gson().fromJson(obj, ModelArticles::class.java)

            viewState.showPost(post)

        }catch (e: Exception){
            viewState.onErrorLoadPost(e.message.toString())
        }
    }
}