package com.setiawan.anxdre.footballclubmanager.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONObjectRequestListener
import com.setiawan.anxdre.footballclubmanager.R
import com.setiawan.anxdre.footballclubmanager.TeamDetail
import com.setiawan.anxdre.footballclubmanager.adapter.TeamAdapter
import com.setiawan.anxdre.footballclubmanager.data.DataTeam
import kotlinx.android.synthetic.main.fragment_adapter.*
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.support.v4.startActivity
import org.jetbrains.anko.support.v4.toast
import org.json.JSONObject

class FragmentTeamList : Fragment() {
    private val mTeam = ArrayList<DataTeam>()

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
                    0 -> loadFan(getString(R.string.GET_TEAM_LIST) + "4328")
                    1 -> loadFan(getString(R.string.GET_TEAM_LIST) + "4329")
                    2 -> loadFan(getString(R.string.GET_TEAM_LIST) + "4330")
                }
            }
        })
        btn_search.onClick {
            if (Et_Search.text.isEmpty()) {
                loadFan(getString(R.string.GET_TEAM_LIST) + "4328")
            } else {
                val query: String?
                query = Et_Search.text.toString().replace(" ", "_").toLowerCase()
                searchEvent(getString(R.string.SEARCH_TEAM) + query)
            }
        }
    }

    fun loadFan(URL: String) {
        Pb_Loading?.visibility = View.VISIBLE
        mTeam.clear()
        Log.e("_Url", URL)
        AndroidNetworking.get(URL)
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(object : JSONObjectRequestListener {
                    override fun onResponse(response: JSONObject) {
                        val jsonArray = response.getJSONArray("teams")
                        for (i in 0 until jsonArray.length()) {
                            val jsonObj = jsonArray.getJSONObject(i)
                            mTeam.add(
                                    DataTeam(jsonObj.optString("idTeam")
                                            , jsonObj.optString("strTeam")
                                            , jsonObj.optString("strTeamBadge"))
                            )
                        }
                        Pb_Loading?.visibility = View.INVISIBLE
                        Rv_EventList.layoutManager = LinearLayoutManager(context)
                        Rv_EventList.adapter = TeamAdapter(mTeam, context) {
                            startActivity<TeamDetail>("TeamId" to it.Id
                                    , "BadgePath" to it.Badge
                                    , "Name" to it.Name)
                        }
                    }

                    override fun onError(anError: ANError?) {
                        toast("Connection Error")
                    }
                })
    }

    private fun searchEvent(URL: String) {
        Pb_Loading?.visibility = View.VISIBLE
        mTeam.clear()
        Log.e("_Url", URL)
        AndroidNetworking.get(URL)
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(object : JSONObjectRequestListener {
                    override fun onResponse(response: JSONObject) {
                        if (response.isNull("teams")) {
                            toast("Data Is Empty")
                            loadFan(getString(R.string.GET_TEAM_LIST) + "4328")
                            Pb_Loading?.visibility = View.INVISIBLE
                        } else {
                            val jsonArray = response.getJSONArray("teams")
                            for (i in 0 until jsonArray.length()) {
                                val jsonObj = jsonArray.getJSONObject(i)
                                mTeam.add(
                                        DataTeam(jsonObj?.optString("idTeam")
                                                , jsonObj?.optString("strTeam")
                                                , jsonObj?.optString("strTeamBadge"))
                                )
                            }
                            Pb_Loading?.visibility = View.INVISIBLE
                            Rv_EventList.layoutManager = LinearLayoutManager(context)
                            Rv_EventList.adapter = TeamAdapter(mTeam, context) {
                                startActivity<TeamDetail>("TeamId" to it.Id
                                        , "BadgePath" to it.Badge
                                        , "Name" to it.Name)
                            }
                        }
                    }

                    override fun onError(anError: ANError?) {
                        toast("Connection Error")
                    }
                })
    }
}