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
import org.json.JSONObject

class EventDetail : AppCompatActivity() {

    var TitleHome: String = ""
    var TitleAway: String = ""
    var ScoreHome: String = ""
    var ScoreAway: String = ""
    var ShootsHome: String = ""
    var ShootsAway: String = ""
    var GoalsKeeperHome: String = ""
    var GoalsKeeperAway: String = ""
    var Rounds: String = ""
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

                            TitleHome = jsonObj.optString("strHomeTeam")
                            TitleAway = jsonObj.optString("strAwayTeam")
                            ScoreHome = jsonObj.optString("intHomeScore")
                            ScoreAway = jsonObj.optString("intAwayScore")
                            ShootsHome = jsonObj.optString("intHomeShots")
                            ShootsAway = jsonObj.optString("intAwayShots")
                            GoalsKeeperHome = jsonObj.optString("strHomeLineupGoalkeeper")
                            GoalsKeeperAway = jsonObj.optString("strAwayLineupGoalkeeper")
                            Rounds = jsonObj.optString("intRound")
                        }
                        TV_TitleHome.text = TitleHome
                        TV_TitleAway.text = TitleAway
                        TV_ScoreHome.text = ScoreHome
                        TV_ScoreAway.text = ScoreAway
                        TV_ShootsHome.text = ShootsHome
                        TV_ShootsAway.text = ShootsAway
                        TV_SubtituesHome.text = GoalsKeeperHome
                        TV_SubtituesAway.text = GoalsKeeperAway
                        TV_RoundsHome.text = Rounds
                        TV_RoundsAway.text = Rounds
                    }

                    override fun onError(anError: ANError?) {
                        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
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
                        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
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