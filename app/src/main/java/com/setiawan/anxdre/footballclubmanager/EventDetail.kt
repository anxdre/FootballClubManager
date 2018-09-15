package com.setiawan.anxdre.footballclubmanager

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.ImageView
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONObjectRequestListener
import kotlinx.android.synthetic.main.activity_event_detail.*
import org.jetbrains.anko.toast
import org.json.JSONObject

class EventDetail : AppCompatActivity() {
    var ImagePath: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event_detail)

        //inisialisasi data
        val MatchID: String = intent.getStringExtra("MatchID")
        val HomeID: String = intent.getStringExtra("HomeID")
        val AwayID: String = intent.getStringExtra("AwayID")

        loadFanDetail(getString(R.string.GET_EVENT_DETAIL) + MatchID)
        loadFanTeam(getString(R.string.GET_IMAGE_BADGE) + HomeID, IV_Home)
        loadFanTeam(getString(R.string.GET_IMAGE_BADGE) + AwayID, IV_Away)
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

                            TV_TitleHome.text = jsonObj.optString("strHomeTeam")
                            TV_TitleAway.text = jsonObj.optString("strAwayTeam")
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
                            ImagePath = jsonObj.optString("strTeamBadge")
                        }
                        DownloadImage(ImagePath, Image)
                    }

                    override fun onError(anError: ANError?) {
                        toast("Connection Error")
                    }

                })
    }

    private fun DownloadImage(Url: String, ImgView: ImageView) {
        PB_DetailLoad.visibility = View.VISIBLE
        com.squareup.picasso.Picasso.get().load(Url).placeholder(R.drawable.ic_landscape_black_24dp)
                .error(R.drawable.ic_landscape_black_24dp).fit()
                .centerCrop().into(ImgView)
        PB_DetailLoad.visibility = View.INVISIBLE
    }
}