package com.setiawan.anxdre.footballclubmanager

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.setiawan.anxdre.footballclubmanager.adapter.FragmentTeamApdapter
import kotlinx.android.synthetic.main.activity_team_menu.*

class TeamMenu : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_team_menu)

        val fragmentTeamAdapter = FragmentTeamApdapter(supportFragmentManager)
        VP_Team.adapter = fragmentTeamAdapter
        tabs_Team.setupWithViewPager(VP_Team)

    }
}
