package com.example.supportretrofit2.retrofit

import com.google.gson.JsonElement
import kotlinx.coroutines.Deferred
import retrofit2.Response
import java.net.SocketTimeoutException

class Response {
    open class MyResponse {
        var code: Int? = null
        var error: Boolean = false
        var e: Exception? = null
        var body: Any? = null
        var errorBody: Any? = null
        var raw: Any? = null
        var headers: Any? = null
        var message: Any?= null
        var eTimeout: Boolean = false
    }

    suspend fun getResponse(deferredResponse: Deferred<Response<JsonElement>>): MyResponse {
        val myResponse = MyResponse()
        myResponse.error = false
        try {
            val res = deferredResponse.await()
            myResponse.code = res.code()
            myResponse.body = res.body()
            myResponse.errorBody = res.errorBody()
            myResponse.raw = res.raw()
            myResponse.headers = res.headers()
            myResponse.message = res.message()
        }
        catch (eTimeout: SocketTimeoutException){
            myResponse.error = true
            myResponse.eTimeout = true
        }
        catch (e: Exception) {
            myResponse.error = true
            myResponse.e = e
        }
        return myResponse
    }
}