package com.example.supportretrofit2.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.supportretrofit2.R
import com.example.supportretrofit2.model.ModelArticles
import com.example.supportretrofit2.presenter.MainPresenter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception
import android.content.Intent




class MainActivity : AppCompatActivity(), MainView {

    private val mainPresenter = MainPresenter(this)

    private lateinit var progressBar: ProgressBar
    private lateinit var rvListTitles: RecyclerView
    private lateinit var btFind: Button
    private lateinit var etUserId: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        progressBar = findViewById(R.id.progressBar)
        rvListTitles = findViewById(R.id.rvListTitles)
        btFind = findViewById(R.id.btFind)
        etUserId = findViewById(R.id.etUserId)
    }

    override fun onResume() {
        super.onResume()

        //На кнопку вешаем событие с запросом
        btFind.setOnClickListener {
            try {
                val userId = etUserId.text.toString().toInt()

                //Запрос выполняем в отдельном потоке
                CoroutineScope(Dispatchers.Main).launch {
                    progressBar.visibility = View.VISIBLE
                    mainPresenter.getPosts(userId)
                    progressBar.visibility = View.GONE
                }
            }catch (e: Exception){
                onErrorLoadPosts(e.message.toString())
            }
        }
    }

    override fun showPosts(listPost: List<ModelArticles>) {
        //Если при выполнении запроса не возникло ошибок, то помещаем результаты в recyclerView
        rvListTitles.layoutManager = LinearLayoutManager(this)
        rvListTitles.adapter = AdapterArtricles(listPost, object : AdapterArtricles.IEventPosts{
            override fun onClick(id: Int) {
                val intent = Intent(applicationContext, PostActivity::class.java)
                intent.putExtra("post_id", id)
                startActivity(intent)
            }
        })
    }

    override fun onErrorLoadPosts(message: String) {
        //Выводим на экран текст ошибки
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}