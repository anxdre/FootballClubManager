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
import com.setiawan.anxdre.footballclubmanager.data.DataEvent
import kotlinx.android.synthetic.main.fragment_adapter.*
import org.jetbrains.anko.support.v4.startActivity
import org.jetbrains.anko.support.v4.toast
import org.json.JSONObject

class FragmentUpcomingEvent : Fragment() {
    val mEvents = ArrayList<DataEvent>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        loadFan(getString(R.string.GET_UPCOMING_EVENT))
        return inflater.inflate(R.layout.fragment_adapter, container, false)
    }

    private fun loadFan(URL: String) {
        Log.i("Progresss", "Runninggg....")
        AndroidNetworking.get(URL)
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(object : JSONObjectRequestListener {
                    override fun onResponse(response: JSONObject) {
                        val jsonArray = response.getJSONArray("events")
                        for (i in 0 until jsonArray.length()) {
                            val jsonObj = jsonArray.getJSONObject(i)
                            Log.e("_VALUE", jsonObj.optString("strEvent"))
                            mEvents.add(
                                    DataEvent(jsonObj.optString("idEvent")
                                            , jsonObj.optString("strHomeTeam")
                                            , jsonObj.optString("strAwayTeam")
                                            , jsonObj.optString("idHomeTeam")
                                            , jsonObj.optString("idAwayTeam")
                                            , jsonObj.optString("dateEvent"))
                            )
                        }
                        Pb_Loading?.visibility = View.INVISIBLE
                        Rv_EventList.layoutManager = LinearLayoutManager(context)
                        Rv_EventList.adapter = EventAdapter(mEvents, context) {
                            startActivity<EventDetail>("MatchID" to "${it.idEvent}"
                                    , "HomeID" to "${it.HomeID}"
                                    , "AwayID" to "${it.AwayID}"
                                    , "Date" to "${it.DateEvent}")
                        }
                    }

                    override fun onError(anError: ANError?) {
                        toast("Connection Error")

                    }
                })
    }
}