package com.setiawan.anxdre.footballclubmanager.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.setiawan.anxdre.footballclubmanager.EventDetail
import com.setiawan.anxdre.footballclubmanager.R
import com.setiawan.anxdre.footballclubmanager.adapter.FavoriteAdapter
import com.setiawan.anxdre.footballclubmanager.data.FavoriteEvent
import com.setiawan.anxdre.footballclubmanager.data.database
import kotlinx.android.synthetic.main.fragment_team_detail_player_list.*
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.select
import org.jetbrains.anko.support.v4.startActivity

class FragmentFavoriteEvent : Fragment() {
    private var mFavoriteEvents: MutableList<FavoriteEvent> = mutableListOf()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_team_detail_player_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        load()
    }

    private fun load() {
        context?.database?.use {
            val result = select(FavoriteEvent.TABLE_FAVORITE)
            val favorite = result.parseList(classParser<FavoriteEvent>())
            mFavoriteEvents.addAll(favorite)
        }
        Pb_PlayerListLoad.visibility = View.INVISIBLE
        Rv_PlayerList.layoutManager = LinearLayoutManager(context)
        Rv_PlayerList.adapter = FavoriteAdapter(mFavoriteEvents, context) {
            startActivity<EventDetail>("MatchID" to "${it.MatchId}"
                    , "HomeID" to "${it.HomeId}"
                    , "AwayID" to "${it.AwayId}")
        }
    }

    override fun onResume() {
        mFavoriteEvents.clear()
        load()
        super.onResume()
    }
}