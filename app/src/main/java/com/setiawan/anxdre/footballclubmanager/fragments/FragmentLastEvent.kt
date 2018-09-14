package com.setiawan.anxdre.footballclubmanager.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONObjectRequestListener
import com.setiawan.anxdre.footballclubmanager.EventDetail
import com.setiawan.anxdre.footballclubmanager.R
import com.setiawan.anxdre.footballclubmanager.adapter.EventAdapter
import com.setiawan.anxdre.footballclubmanager.data.model.DataEvent
import kotlinx.android.synthetic.main.fragment_adapter.*
import org.jetbrains.anko.support.v4.startActivity
import org.jetbrains.anko.support.v4.toast
import org.json.JSONObject

class FragmentLastEvent : Fragment(){
    val Events = ArrayList<DataEvent>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        loadFan(getString(R.string.GET_LAST_EVENT))
        return inflater.inflate(R.layout.fragment_adapter, container, false)
    }

    private fun loadFan(URL: String) {
        Log.i("Progresss","Runninggg....")
        AndroidNetworking.get(URL)
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(object : JSONObjectRequestListener {
                    override fun onResponse(response: JSONObject) {
                        val jsonArray = response.getJSONArray("events")
                        for (i in 0 until jsonArray.length()) {
                            val jsonObj = jsonArray.getJSONObject(i)
                            Log.e("_VALUE", jsonObj.optString("strEvent"))
                            Events.add(
                                    DataEvent(jsonObj.optString("idEvent")
                                            , jsonObj.optString("strHomeTeam")
                                            , jsonObj.optString("strAwayTeam")
                                            , jsonObj.optString("idHomeTeam")
                                            , jsonObj.optString("idAwayTeam")
                                            , jsonObj.optString("dateEvent"))
                            )
                        }
                        PB_Loading?.visibility = View.INVISIBLE
                        RV_EventList.layoutManager = LinearLayoutManager(context)
                        RV_EventList.adapter = EventAdapter(Events, context) {
                            startActivity<EventDetail>("MatchID" to "${it.idEvent}"
                                    , "HomeID" to "${it.HomeID}"
                                    , "AwayID" to "${it.AwayID}")
                        }
                    }

                    override fun onError(anError: ANError?) {
                        Log.e("_ERROR", anError.toString())
                    }
                })
    }
}