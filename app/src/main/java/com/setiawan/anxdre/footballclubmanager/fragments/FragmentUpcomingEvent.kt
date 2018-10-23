package com.setiawan.anxdre.footballclubmanager.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONObjectRequestListener
import com.setiawan.anxdre.footballclubmanager.EventDetail
import com.setiawan.anxdre.footballclubmanager.R
import com.setiawan.anxdre.footballclubmanager.adapter.EventAdapter
import com.setiawan.anxdre.footballclubmanager.data.DataEvent
import kotlinx.android.synthetic.main.fragment_adapter.*
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.support.v4.startActivity
import org.jetbrains.anko.support.v4.toast
import org.json.JSONObject

class FragmentUpcomingEvent : Fragment() {
    val mEvents = ArrayList<DataEvent>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_adapter, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        ArrayAdapter.createFromResource(context, R.array.Liga, android.R.layout.simple_spinner_item).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            Sp_Liga.adapter = adapter
        }
        Sp_Liga.onItemSelectedListener = (object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                when (position) {
                    0 -> loadFan(getString(R.string.GET_UPCOMING_EVENT) + "4328")
                    1 -> loadFan(getString(R.string.GET_UPCOMING_EVENT) + "4329")
                    2 -> loadFan(getString(R.string.GET_UPCOMING_EVENT) + "4330")
                }
            }
        })

        btn_search.onClick {
            if (Et_Search.text.isEmpty()) {
                loadFan(getString(R.string.GET_UPCOMING_EVENT) + "4328")
            } else {
                val query: String?
                query = Et_Search.text.toString().replace(" ", "_").toLowerCase()
                searchEvent(getString(R.string.SEARCH_EVENT) + query)
            }
        }
    }

    private fun loadFan(URL: String) {
        Pb_Loading?.visibility = View.VISIBLE
        mEvents.clear()
        AndroidNetworking.get(URL)
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(object : JSONObjectRequestListener {
                    override fun onResponse(response: JSONObject) {
                        val jsonArray = response.getJSONArray("events")
                        for (i in 0 until jsonArray.length()) {
                            val jsonObj = jsonArray.getJSONObject(i)
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

    private fun searchEvent(URL: String) {
        Pb_Loading?.visibility = View.VISIBLE
        mEvents.clear()
//        Log.e("_Url", URL)
        AndroidNetworking.get(URL)
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(object : JSONObjectRequestListener {
                    override fun onResponse(response: JSONObject) {
                        if (response.isNull("event")) {
                            toast("Data Is Empty")
                            loadFan(getString(R.string.GET_UPCOMING_EVENT) + "4328")
                            Pb_Loading?.visibility = View.INVISIBLE
                        } else {
                            val jsonArray = response.getJSONArray("event")
                            for (i in 0 until jsonArray.length()) {
                                val jsonObj = jsonArray.getJSONObject(i)
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
                            Rv_EventList?.layoutManager = LinearLayoutManager(context)
                            Rv_EventList?.adapter = EventAdapter(mEvents, context) {
                                startActivity<EventDetail>("MatchID" to "${it.idEvent}"
                                        , "HomeID" to "${it.HomeID}"
                                        , "AwayID" to "${it.AwayID}"
                                        , "Date" to "${it.DateEvent}")
                            }
                        }
                    }

                    override fun onError(anError: ANError?) {
                        toast("Connection Error")
                    }
                })
    }
}