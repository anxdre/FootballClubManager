package com.setiawan.anxdre.footballclubmanager

import android.database.sqlite.SQLiteConstraintException
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.ImageView
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONObjectRequestListener
import com.setiawan.anxdre.footballclubmanager.data.Favorite
import com.setiawan.anxdre.footballclubmanager.data.database
import com.setiawan.anxdre.footballclubmanager.utils.DownloadImg
import kotlinx.android.synthetic.main.activity_event_detail.*
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.delete
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.db.select
import org.jetbrains.anko.toast
import org.json.JSONObject

class EventDetail : AppCompatActivity() {
    var mImagePath: String = ""
    var mHomeTitle: String = ""
    var mAwayTitle: String = ""
    private var mIsFavorite: Boolean = false
    val id: String by lazy {
        intent.getStringExtra("MatchID")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event_detail)

        //inisialisasi data
        val matchId: String = intent.getStringExtra("MatchID")
        val homeId: String = intent.getStringExtra("HomeID")
        val awayId: String = intent.getStringExtra("AwayID")
        val date: String? = intent.getStringExtra("Date")

        loadFanDetail(getString(R.string.GET_EVENT_DETAIL) + matchId)
        loadFanTeam(getString(R.string.GET_IMAGE_BADGE) + homeId, IV_Home)
        loadFanTeam(getString(R.string.GET_IMAGE_BADGE) + awayId, IV_Away)

        favoriteState()
        setFavorite()

        Fab_AddButton.setOnClickListener {
            //
            if (mIsFavorite) {
                removeFromFavorite()
                finish()
            } else {
                database.use {
                    insert(Favorite.TABLE_FAVORITE,
                            Favorite.MATCH_ID to matchId,
                            Favorite.HOME_ID to homeId,
                            Favorite.HOMETITLE to mHomeTitle,
                            Favorite.AWAY_ID to awayId,
                            Favorite.AWAYTITLE to mAwayTitle,
                            Favorite.DATE to date)
                    toast("Added To Favorite")
//                    Fab_AddButton.setImageResource(R.drawable.ic_delete_forever_black_24dp)
                    finish()
                }
                mIsFavorite = !mIsFavorite
                setFavorite()
            }

        }
    }

    private fun favoriteState() {
        database.use {
            val result = select(Favorite.TABLE_FAVORITE)
                    .whereArgs("(MATCH_ID = {id})",
                            "id" to id)
            val favorite = result.parseList(classParser<Favorite>())
            if (!favorite.isEmpty()) mIsFavorite = true
        }
    }

    private fun removeFromFavorite() {
        try {
            database.use {
                delete(Favorite.TABLE_FAVORITE, "(MATCH_ID = {id})",
                        "id" to id)
            }
            toast("Deleted From Favorite")
        } catch (e: SQLiteConstraintException) {
            toast(e.toString())
        }
    }

    private fun setFavorite() {
        if (mIsFavorite)
            Fab_AddButton.setImageResource(R.drawable.ic_delete_forever_black_24dp)
        else
            Fab_AddButton.setImageResource(R.drawable.ic_favorite_black_24dp)
    }

    private fun loadFanDetail(URL: String) {
        AndroidNetworking.get(URL)
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(object : JSONObjectRequestListener {
                    override fun onResponse(response: JSONObject) {
                        val jsonArrayEvent = response.getJSONArray("events")
                        for (i in 0 until jsonArrayEvent.length()) {
                            val jsonObj = jsonArrayEvent.getJSONObject(i)

                            mHomeTitle = jsonObj.optString("strHomeTeam")
                            TV_TitleHome.text = mHomeTitle
                            mAwayTitle = jsonObj.optString("strAwayTeam")
                            TV_TitleAway.text = mAwayTitle
                            TV_ScoreHome.text = jsonObj.optString("intHomeScore")
                            TV_ScoreAway.text = jsonObj.optString("intAwayScore")
                            TV_ShootsHome.text = jsonObj.optString("intHomeShots")
                            TV_ShootsAway.text = jsonObj.optString("intAwayShots")
                            TV_KeeperHome.text = jsonObj.optString("strHomeLineupGoalkeeper")
                            TV_KeeperAway.text = jsonObj.optString("strAwayLineupGoalkeeper")
                            TV_RoundsHome.text = jsonObj.optString("intRound")
                            TV_RoundsAway.text = jsonObj.optString("intRound")
                            TV_DefenseHome.text = jsonObj.optString("strHomeLineupDefense")
                            TV_DefenseAway.text = jsonObj.optString("strAwayLineupDefense")
                            TV_MiddleHome.text = jsonObj.optString("strHomeLineupMidfield")
                            TV_MiddleAway.text = jsonObj.optString("strAwayLineupMidfield")
                            TV_FrontHome.text = jsonObj.optString("strHomeLineupForward")
                            TV_FrontAway.text = jsonObj.optString("strAwayLineupForward")
                        }
                    }

                    override fun onError(anError: ANError?) {
                        toast("Connection Error")
                    }

                })
    }

    private fun loadFanTeam(URL: String, Image: ImageView) {
        AndroidNetworking.get(URL)
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(object : JSONObjectRequestListener {
                    override fun onResponse(response: JSONObject) {
                        val jsonArrayDetail = response.getJSONArray("teams")
                        for (i in 0 until jsonArrayDetail.length()) {
                            val jsonObj = jsonArrayDetail.getJSONObject(i)
                            mImagePath = jsonObj.optString("strTeamBadge")
                        }
                        DownloadImg.getImage(mImagePath, Image)
                        PB_DetailLoad.visibility = View.INVISIBLE
                    }

                    override fun onError(anError: ANError?) {
                        toast("Connection Error")
                    }

                })
    }

}