package com.example.supportretrofit2.view

import com.example.supportretrofit2.model.ModelArticles

interface PostView {
    fun showPost(post: ModelArticles)
    fun onErrorLoadPost(message: String)
}