package com.setiawan.anxdre.footballclubmanager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.setiawan.anxdre.footballclubmanager.adapter.FragmentApdapter
import kotlinx.android.synthetic.main.activity_main_menu.*

class MainMenu : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_menu)

        val fragmentAdapter = FragmentApdapter(supportFragmentManager)
        VP_Main.adapter = fragmentAdapter
        tabs_main.setupWithViewPager(VP_Main)
    }

}
