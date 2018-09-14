package com.setiawan.anxdre.footballclubmanager

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONObjectRequestListener
import kotlinx.android.synthetic.main.activity_event_detail.*
import org.json.JSONObject

class EventDetail : AppCompatActivity() {

    var TitleHome: String = "";
    var TitleAway: String = "";
    var ScoreHome: String = "";
    var ScoreAway: String = ""
    var ShootsHome: String = "";
    var ShootsAway: String = ""
    var SubtituesHome: String = "";
    var SubtituesAway: String = ""
    var Rounds: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event_detail)

        //inisialisasi data
        val MatchID: String = intent.getStringExtra("MatchID")
        val HomeID: String = intent.getStringExtra("HomeID")
        val AwayId: String = intent.getStringExtra("AwayID")

        loadFan(getString(R.string.GET_EVENT_DETAIL) + MatchID)
    }

    private fun loadFan(URL: String) {
        Log.i("Progresss", "Runninggg....")
        Log.e("_Url",URL)
        AndroidNetworking.get(URL)
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(object : JSONObjectRequestListener {
                    override fun onResponse(response: JSONObject) {
                        val jsonArray = response.getJSONArray("events")
                        for (i in 0 until jsonArray.length()) {
                            val jsonObj = jsonArray.getJSONObject(i)

                            TitleHome = jsonObj.optString("strHomeTeam")
                            TitleAway = jsonObj.optString("strAwayTeam")
                            ScoreHome = jsonObj.optString("intHomeScore")
                            ScoreAway = jsonObj.optString("intAwayScore")
                            ShootsHome = jsonObj.optString("intHomeShots")
                            ShootsAway = jsonObj.optString("intAwayShots")
                            SubtituesHome = jsonObj.optString("strHomeFormation")
                            SubtituesAway = jsonObj.optString("strAwayFormation")
                            Rounds = jsonObj.optString("intRound")
                        }
                        Log.e("_FORMATION",SubtituesHome)
                        TV_TitleHome.text = TitleHome
                        TV_TitleAway.text = TitleAway
                        TV_ScoreHome.text = ScoreHome
                        TV_ScoreAway.text = ScoreAway
                        TV_ShootsHome.text = ShootsHome
                        TV_ShootsAway.text = ShootsAway
                        TV_SubtituesHome.text = SubtituesHome
                        TV_SubtituesAway.text = SubtituesAway
                        TV_RoundsHome.text = Rounds
                        TV_RoundsAway.text = Rounds
                    }

                    override fun onError(anError: ANError?) {
                        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                    }

                })
    }
}