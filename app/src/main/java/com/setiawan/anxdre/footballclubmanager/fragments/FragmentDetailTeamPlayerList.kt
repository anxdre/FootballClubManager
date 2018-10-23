package com.setiawan.anxdre.footballclubmanager.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONObjectRequestListener
import com.setiawan.anxdre.footballclubmanager.PlayerDesc
import com.setiawan.anxdre.footballclubmanager.R
import com.setiawan.anxdre.footballclubmanager.adapter.TeamDetailPlayerAdapter
import com.setiawan.anxdre.footballclubmanager.data.PlayerDetail
import kotlinx.android.synthetic.main.fragment_team_detail_player_list.*
import org.jetbrains.anko.support.v4.startActivity
import org.jetbrains.anko.support.v4.toast
import org.json.JSONObject

class FragmentDetailTeamPlayerList : Fragment() {
    private val mPlayer = ArrayList<PlayerDetail>()
    private val mId by lazy {
        activity!!.intent.getStringExtra("TeamId")
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_team_detail_player_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        loadFan(getString(R.string.GET_LIST_PLAYER) + mId)
    }

    fun loadFan(URL: String) {
        Pb_PlayerListLoad?.visibility = View.VISIBLE
        mPlayer.clear()
//        Log.e("_Url", URL)
        AndroidNetworking.get(URL)
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(object : JSONObjectRequestListener {
                    override fun onResponse(response: JSONObject) {
                        val jsonArray = response.getJSONArray("player")
                        for (i in 0 until jsonArray.length()) {
                            val jsonObj = jsonArray.getJSONObject(i)
                            mPlayer.add(
                                    PlayerDetail(jsonObj.optString("idPlayer")
                                            , jsonObj.optString("strPlayer")
                                            , jsonObj.optString("strCutout")
                                            , jsonObj.optString("strFanart1")
                                            , jsonObj.optString("strDescriptionEN")
                                            , jsonObj.optString("strHeight")
                                            , jsonObj.optString("strWeight")
                                            , jsonObj.optString("strPosition"))
                            )
                        }
                        Pb_PlayerListLoad?.visibility = View.INVISIBLE
                        Rv_PlayerList?.layoutManager = LinearLayoutManager(context)
                        Rv_PlayerList?.adapter = TeamDetailPlayerAdapter(mPlayer, context) {
                            startActivity<PlayerDesc>("Name" to it.PlayerName
                                    , "ProfilePhoto" to it.ProfileImagePath
                                    , "ProfileCover" to it.ProfileCover
                                    , "ProfileDesc" to it.ProfileDesc
                                    , "Height" to it.Height
                                    , "Weight" to it.Weight
                                    , "Position" to it.Position)
                        }
                    }

                    override fun onError(anError: ANError?) {
                        toast("Connection Error")
                    }
                })
    }
}