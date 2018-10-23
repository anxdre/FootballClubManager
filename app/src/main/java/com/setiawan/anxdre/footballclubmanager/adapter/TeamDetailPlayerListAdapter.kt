package com.setiawan.anxdre.footballclubmanager.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.setiawan.anxdre.footballclubmanager.R
import com.setiawan.anxdre.footballclubmanager.data.PlayerDetail
import com.setiawan.anxdre.footballclubmanager.utils.DownloadImg
import kotlinx.android.synthetic.main.list_team_adapter.view.*

class TeamDetailPlayerAdapter(private val playerList: ArrayList<PlayerDetail>, private val context: Context?, private val listener: (PlayerDetail) -> Unit)
    : RecyclerView.Adapter<PlayerViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, position: Int): PlayerViewHolder = PlayerViewHolder(LayoutInflater.from(context).inflate(R.layout.list_team_adapter, parent, false))

    override fun getItemCount(): Int = playerList.size

    override fun onBindViewHolder(holder: PlayerViewHolder, position: Int) {
        holder.bindItem(playerList[position], listener)
    }
}

class PlayerViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    fun bindItem(items: PlayerDetail, listener: (PlayerDetail) -> Unit) {
        itemView.Tv_TeamName.text = items.PlayerName
        DownloadImg.getImage(items.ProfileImagePath, itemView.Iv_TeamBadge)
        itemView.setOnClickListener {
            listener(items)
        }
    }
}