package com.example.supportretrofit2.presenter

import android.util.Log
import com.example.supportretrofit2.model.ModelArticles
import com.example.supportretrofit2.retrofit.NetworkService
import com.example.supportretrofit2.retrofit.NetworkWrapper
import com.example.supportretrofit2.retrofit.Response
import com.example.supportretrofit2.view.MainView
import com.google.gson.Gson
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import java.lang.Exception

class MainPresenter(private val viewState: MainView) {

    //suspend означает, что функция будет вызываться из отдельного потока
    suspend fun getPosts(userId: Int){
        try {
            //В переменную response помещаем результаты нашего http запроса
            val response: Response.MyResponse = NetworkWrapper().getPosts()

            //Если в запросе была ошибка, то вызываем исключение и передаем код ошибки
            if(response.error)
                throw Exception("Ошибка! "+response.e?.message.toString())

            //Если ответ сервера не 200, то также вызываем исключение с передачи кода ответа сервера
            if(response.code!=200)
                throw Exception("Ошибка! Ответ сервера: "+response.code)

            //Получаем массив из Json-объектов
            val arrayObject: JsonArray = response.body as JsonArray

            //Инициализируем коллекцию в которую будем помещать распарсенные результаты с типом ModelArticles
            val listPosts = mutableListOf<ModelArticles>()

            //Создаем цикл для прохождения всех объектов Json статей
            arrayObject.forEach {
                try {
                    //Вытаскиваем из объекта Json значение userId
                    val user = it.asJsonObject.get("userId").asInt

                    //Теперь сравниваем его с парметрам переданным из MainActivity
                    if(user==userId){
                        //Если если пользователь такой же, то добавляем в нашу коллекцию, распарсив Json-объект в ModelArticles
                        listPosts.add(
                            Gson().fromJson(it, ModelArticles::class.java)
                        )
                    }
                }catch (e: Exception){}
            }

            //Если количество элементов массива равно нулю, то выбрасываем ошибку
            if(listPosts.size==0)
                throw Exception("Статей с указанным пользователем не найдено")

            //Передаем во view колллекцию с результатми запроса
            viewState.showPosts(listPosts)

        }catch (e: Exception){
            //Если во времы выполения(обработки) запроса возникло исключение, то вызываем функцию во view и передаем текст ошибки
            viewState.onErrorLoadPosts(e.message.toString())
        }
    }
}