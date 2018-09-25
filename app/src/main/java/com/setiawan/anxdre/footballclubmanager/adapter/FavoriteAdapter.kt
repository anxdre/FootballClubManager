package com.setiawan.anxdre.footballclubmanager.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.setiawan.anxdre.footballclubmanager.R
import com.setiawan.anxdre.footballclubmanager.data.Favorite
import kotlinx.android.synthetic.main.list_adapter.view.*

class FavoriteAdapter(private val FavoriteList: List<Favorite>, private val context: Context?, private val listener: (Favorite) -> Unit)
    : RecyclerView.Adapter<FavoriteHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, position: Int) = FavoriteHolder(LayoutInflater.from(context).inflate(R.layout.list_adapter, parent, false))

    override fun onBindViewHolder(holder: FavoriteHolder, position: Int) {
        holder.bindItem(FavoriteList[position], listener)
    }

    override fun getItemCount(): Int = FavoriteList.size

}

class FavoriteHolder(view: View) : RecyclerView.ViewHolder(view) {
    fun bindItem(items: Favorite, listener: (Favorite) -> Unit) {
        itemView.TV_Home.text = items.HomeTitle
        itemView.TV_Away.text = items.AwayTitle
        itemView.TV_Eventdate.text = items.Date
        itemView.setOnClickListener {
            listener(items)
        }
    }
}