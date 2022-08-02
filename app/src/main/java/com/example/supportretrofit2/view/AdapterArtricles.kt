package com.example.supportretrofit2.view

import android.view.LayoutInflater
import android.view.TextureView
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.supportretrofit2.R
import com.example.supportretrofit2.model.ModelArticles

class AdapterArtricles(val listPosts: List<ModelArticles>, val listetner: IEventPosts):
    RecyclerView.Adapter<AdapterArtricles.ViewHolder>() {
    interface IEventPosts{
        fun onClick(id: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_article, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tvTitle.text = listPosts[position].title

        holder.clMain.setOnClickListener {
            listetner.onClick(listPosts[position].id!!)
        }
    }

    override fun getItemCount(): Int {
        return listPosts.size
    }

    class ViewHolder(internal var mView: View) : RecyclerView.ViewHolder(mView){
        var tvTitle: TextView = mView.findViewById(R.id.tvTitle)
        var clMain: View = mView.findViewById(R.id.clMain)
    }
}