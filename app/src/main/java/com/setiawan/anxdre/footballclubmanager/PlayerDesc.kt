package com.setiawan.anxdre.footballclubmanager

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.setiawan.anxdre.footballclubmanager.utils.DownloadImg
import kotlinx.android.synthetic.main.player_detail.*

class PlayerDesc : AppCompatActivity() {
    private val mPlayerName: String? by lazy {
        intent.getStringExtra("Name")
    }
    private val mProfileCover: String? by lazy {
        intent.getStringExtra("ProfileCover")
    }
    private val mProfileDesc: String? by lazy {
        intent.getStringExtra("ProfileDesc")
    }
    private val mHeight: String? by lazy {
        intent.getStringExtra("Height")
    }
    private val mWeight: String? by lazy {
        intent.getStringExtra("Weight")
    }
    private val mPosition: String? by lazy {
        intent.getStringExtra("Position")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.player_detail)

        DownloadImg.getImage(mProfileCover, iv_player_detail)
        txt_weight.text = mWeight
        txt_height.text = mHeight
        txt_position_player.text = mPosition
        txt_forward_player_detail.text = mProfileDesc
        txt_player_name.text = mPlayerName
    }
}
