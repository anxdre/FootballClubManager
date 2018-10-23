package com.setiawan.anxdre.footballclubmanager.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.setiawan.anxdre.footballclubmanager.R
import com.setiawan.anxdre.footballclubmanager.data.FavoriteEvent
import kotlinx.android.synthetic.main.list_adapter.view.*

class FavoriteAdapter(private val favoriteEventList: List<FavoriteEvent>, private val context: Context?, private val listener: (FavoriteEvent) -> Unit)
    : RecyclerView.Adapter<FavoriteHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, position: Int) = FavoriteHolder(LayoutInflater.from(context).inflate(R.layout.list_adapter, parent, false))

    override fun onBindViewHolder(holder: FavoriteHolder, position: Int) {
        holder.bindItem(favoriteEventList[position], listener)
    }

    override fun getItemCount(): Int = favoriteEventList.size

}

class FavoriteHolder(view: View) : RecyclerView.ViewHolder(view) {
    fun bindItem(items: FavoriteEvent, listener: (FavoriteEvent) -> Unit) {
        itemView.TV_Home.text = items.HomeTitle
        itemView.TV_Away.text = items.AwayTitle
        itemView.TV_Eventdate.text = items.Date
        itemView.setOnClickListener {
            listener(items)
        }
    }
}