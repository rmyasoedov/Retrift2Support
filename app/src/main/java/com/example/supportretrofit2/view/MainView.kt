package com.example.supportretrofit2.view

import com.example.supportretrofit2.model.ModelArticles

interface MainView {
    fun showPosts(listPost: List<ModelArticles>)
    fun onErrorLoadPosts(message: String)
}