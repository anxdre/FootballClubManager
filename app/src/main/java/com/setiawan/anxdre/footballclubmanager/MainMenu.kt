package com.setiawan.anxdre.footballclubmanager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONObjectRequestListener
import com.setiawan.anxdre.footballclubmanager.adapter.EventAdapter
import com.setiawan.anxdre.footballclubmanager.data.model.UpcomingEvent
import kotlinx.android.synthetic.main.activity_main_menu.*
import org.jetbrains.anko.toast
import org.json.JSONObject
import kotlin.collections.ArrayList

class MainMenu : AppCompatActivity() {
    val Events = ArrayList<UpcomingEvent>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_menu)

        RV_EventList.layoutManager = LinearLayoutManager(this)
        loadFan(getString(R.string.GET_UPCOMING_EVENT))
    }

    private fun loadFan(URL:String){
        AndroidNetworking.get(URL)
                .setTag("test")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(object:JSONObjectRequestListener{
                    override fun onResponse(response: JSONObject) {
                            val jsonArray = response.getJSONArray("events")
                            for (i in 0 until jsonArray.length()){
                                val jsonObj = jsonArray.getJSONObject(i)
                                Log.e("_VALUE",jsonObj.optString("strEvent"))
                                Events.add(
                                        UpcomingEvent(jsonObj.optString("idEvent")
                                                ,jsonObj.optString("strHomeTeam")
                                                ,jsonObj.optString("strAwayTeam")
                                                ,jsonObj.optString("dateEvent"))
                                )
                            }
                        RV_EventList.adapter = EventAdapter(Events,this@MainMenu){
                            toast("${it.idEvent}")
                        }
                    }

                    override fun onError(anError: ANError?) {
                        Log.e("_ERROR", anError.toString())
                    }
                })
    }
}
