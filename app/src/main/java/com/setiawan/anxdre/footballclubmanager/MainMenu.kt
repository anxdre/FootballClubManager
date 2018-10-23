package com.setiawan.anxdre.footballclubmanager

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main_menu.*
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.startActivity

class MainMenu : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_menu)

        btn_MatchList.onClick { startActivity<MatchMenu>() }
        btn_TeamList.onClick { startActivity<TeamMenu>() }
    }
}
