package com.setiawan.anxdre.footballclubmanager.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.setiawan.anxdre.footballclubmanager.R
import com.setiawan.anxdre.footballclubmanager.data.DataTeam
import com.setiawan.anxdre.footballclubmanager.utils.DownloadImg
import kotlinx.android.synthetic.main.list_team_adapter.view.*

class TeamAdapter(private val dataTeamList: ArrayList<DataTeam>, private val context: Context?, private val listener: (DataTeam) -> Unit)
    : RecyclerView.Adapter<TeamViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, position: Int) = TeamViewHolder(LayoutInflater.from(context).inflate(R.layout.list_team_adapter, parent, false))

    override fun getItemCount(): Int = dataTeamList.size

    override fun onBindViewHolder(holder: TeamViewHolder, position: Int) {
        holder.bindItem(dataTeamList[position], listener)
    }
}

class TeamViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    fun bindItem(items: DataTeam, listener: (DataTeam) -> Unit) {
        itemView.Tv_TeamName.text = items.Name
        DownloadImg.getImage(items.Badge, itemView.Iv_TeamBadge)
        itemView.setOnClickListener {
            listener(items)
        }
    }
}