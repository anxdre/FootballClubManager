package com.setiawan.anxdre.footballclubmanager

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.setiawan.anxdre.footballclubmanager.adapter.FragmentEventApdapter
import kotlinx.android.synthetic.main.activity_match_menu.*

class MatchMenu : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_match_menu)

        val fragmentAdapter = FragmentEventApdapter(supportFragmentManager)
        VP_Main.adapter = fragmentAdapter
        tabs_main.setupWithViewPager(VP_Main)

    }

}
