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
import com.setiawan.anxdre.footballclubmanager.data.Favorite
import com.setiawan.anxdre.footballclubmanager.data.database
import kotlinx.android.synthetic.main.fragment_adapter.*
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.select
import org.jetbrains.anko.support.v4.startActivity

class FragmentFavorite : Fragment() {
    private var mFavorites: MutableList<Favorite> = mutableListOf()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_adapter, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        load()
    }

    fun load() {
        context?.database?.use {
            val result = select(Favorite.TABLE_FAVORITE)
            val favorite = result.parseList(classParser<Favorite>())
            mFavorites.addAll(favorite)
        }
        Pb_Loading.visibility = View.INVISIBLE
        Rv_EventList.layoutManager = LinearLayoutManager(context)
        Rv_EventList.adapter = FavoriteAdapter(mFavorites, context) {
            startActivity<EventDetail>("MatchID" to "${it.MatchId}"
                    , "HomeID" to "${it.HomeId}"
                    , "AwayID" to "${it.AwayId}")
        }
    }

    override fun onResume() {
        mFavorites.clear()
        load()
        super.onResume()
    }
}