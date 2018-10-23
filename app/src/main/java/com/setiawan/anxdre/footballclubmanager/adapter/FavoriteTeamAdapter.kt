package com.setiawan.anxdre.footballclubmanager.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.setiawan.anxdre.footballclubmanager.R
import com.setiawan.anxdre.footballclubmanager.data.FavoriteTeam
import com.setiawan.anxdre.footballclubmanager.utils.DownloadImg
import kotlinx.android.synthetic.main.list_team_adapter.view.*

class FavoriteTeamAdapter(private val favoriteTeamList: List<FavoriteTeam>, private val context: Context?, private val listener: (FavoriteTeam) -> Unit)
    : RecyclerView.Adapter<FavoriteTeamHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, position: Int) = FavoriteTeamHolder(LayoutInflater.from(context).inflate(R.layout.list_team_adapter, parent, false))

    override fun onBindViewHolder(holder: FavoriteTeamHolder, position: Int) {
        holder.bindItem(favoriteTeamList[position], listener)
    }

    override fun getItemCount(): Int = favoriteTeamList.size

}

class FavoriteTeamHolder(view: View) : RecyclerView.ViewHolder(view) {
    fun bindItem(items: FavoriteTeam, listener: (FavoriteTeam) -> Unit) {
        itemView.Tv_TeamName.text = items.teamName
        DownloadImg.getImage(items.teamBadge, itemView.Iv_TeamBadge)
        itemView.setOnClickListener {
            listener(items)
        }
    }
}