package com.setiawan.anxdre.footballclubmanager.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONObjectRequestListener
import com.setiawan.anxdre.footballclubmanager.R
import com.setiawan.anxdre.footballclubmanager.data.DataTeam
import kotlinx.android.synthetic.main.fragment_adapter.*
import kotlinx.android.synthetic.main.fragment_team_detail_overview.*
import org.jetbrains.anko.support.v4.toast
import org.json.JSONObject

class FragmentDetailTeamOverview : Fragment() {
    private val mTeam = ArrayList<DataTeam>()
    val id: String by lazy {
        activity!!.intent.getStringExtra("TeamId")
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_team_detail_overview, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        loadFan(getString(R.string.GET_DETAIL_TEAM) + id)
    }

    fun loadFan(URL: String) {
        Pb_Loading?.visibility = View.VISIBLE
        mTeam.clear()
//        Log.e("_Url", URL)
        AndroidNetworking.get(URL)
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(object : JSONObjectRequestListener {
                    override fun onResponse(response: JSONObject) {
                        val jsonArray = response.getJSONArray("teams")
                        for (i in 0 until jsonArray.length()) {
                            val jsonObj = jsonArray.getJSONObject(i)
                            Tv_DescTeam.text = jsonObj.optString("strDescriptionEN")
                        }
                        Pb_Loading?.visibility = View.INVISIBLE
                    }

                    override fun onError(anError: ANError?) {
                        toast("Connection Error")
                    }
                })
    }
}