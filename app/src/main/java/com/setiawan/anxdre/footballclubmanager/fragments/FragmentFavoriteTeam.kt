package com.setiawan.anxdre.footballclubmanager.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.setiawan.anxdre.footballclubmanager.R
import com.setiawan.anxdre.footballclubmanager.TeamDetail
import com.setiawan.anxdre.footballclubmanager.adapter.FavoriteTeamAdapter
import com.setiawan.anxdre.footballclubmanager.data.FavoriteTeam
import com.setiawan.anxdre.footballclubmanager.data.database
import kotlinx.android.synthetic.main.fragment_team_detail_player_list.*
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.select
import org.jetbrains.anko.support.v4.startActivity

class FragmentFavoriteTeam : Fragment() {
    private var mFavoriteEvents: MutableList<FavoriteTeam> = mutableListOf()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_team_detail_player_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        load()
    }

    private fun load() {
        context?.database?.use {
            val result = select(FavoriteTeam.TBL_FAVORITE_TEAM)
            val favorite = result.parseList(classParser<FavoriteTeam>())
            mFavoriteEvents.addAll(favorite)
        }
        Pb_PlayerListLoad.visibility = View.INVISIBLE
        Rv_PlayerList.layoutManager = LinearLayoutManager(context)
        Rv_PlayerList.adapter = FavoriteTeamAdapter(mFavoriteEvents, context) {
            startActivity<TeamDetail>("TeamId" to it.teamId
                    , "BadgePath" to it.teamBadge
                    , "Name" to it.teamName)
        }
    }

    override fun onResume() {
        mFavoriteEvents.clear()
        load()
        super.onResume()
    }
}