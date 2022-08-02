package com.example.supportretrofit2.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import com.example.supportretrofit2.R
import com.example.supportretrofit2.model.ModelArticles
import com.example.supportretrofit2.presenter.PostPresenter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception

class PostActivity : AppCompatActivity(), PostView {

    private lateinit var tvTitle: TextView
    private lateinit var tvTextBody: TextView
    private lateinit var progressBar: ProgressBar

    private var postId: Int? = null

    private val postPresenter = PostPresenter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post)

        tvTitle = findViewById(R.id.tvTitle)
        tvTextBody = findViewById(R.id.tvTextBody)
        progressBar = findViewById(R.id.progressBar)
    }

    override fun onBackPressed() {
        finish()
    }

    override fun onResume() {
        super.onResume()

        try {
            val arguments = intent.extras
            arguments.let {
                //Получаем id статьи из MainActivity
                postId = arguments!!["post_id"].toString().toInt()
            }

            CoroutineScope(Dispatchers.Main).launch {
                progressBar.visibility = View.VISIBLE
                postPresenter.getOnlyPost(postId!!)
                progressBar.visibility = View.GONE
            }
        }catch (e: Exception){
            onErrorLoadPost(e.message.toString())
        }
    }

    override fun showPost(post: ModelArticles) {
        try {
            tvTitle.text = post.title
            tvTextBody.text = post.body
        }catch (e: Exception){
            onErrorLoadPost(e.message.toString())
        }
    }

    override fun onErrorLoadPost(message: String) {
        //Выводим на экран текст ошибки
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}