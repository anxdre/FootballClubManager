package com.setiawan.anxdre.footballclubmanager.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.setiawan.anxdre.footballclubmanager.R
import com.setiawan.anxdre.footballclubmanager.data.model.DataEvent
import kotlinx.android.synthetic.main.list_adapter.view.*

class EventAdapter(val EventList:ArrayList<DataEvent>, val context: Context?, val listener: (DataEvent) -> Unit)
    :RecyclerView.Adapter<ViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, position: Int)
            =ViewHolder (LayoutInflater.from(context).inflate(R.layout.list_adapter,parent,false))

    override fun onBindViewHolder(holder: ViewHolder, position:Int) {
        holder.bindItem(EventList[position],listener)
    }

    override fun getItemCount(): Int = EventList.size
}

class ViewHolder (view:View):RecyclerView.ViewHolder(view){
    fun bindItem(items:DataEvent, listener:(DataEvent)->Unit){
        itemView.TV_Eventdate.text = items.DateEvent
        itemView.TV_Home.text = items.Home
        itemView.TV_Away.text = items.Away
        itemView.setOnClickListener {
            listener(items)
        }
    }
}