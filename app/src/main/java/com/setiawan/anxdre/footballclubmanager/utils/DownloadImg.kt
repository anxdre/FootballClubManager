package com.setiawan.anxdre.footballclubmanager.utils

import android.widget.ImageView
import com.setiawan.anxdre.footballclubmanager.R

object DownloadImg {
    fun getImage(Url: String, ImgView: ImageView) {
        com.squareup.picasso.Picasso.get().load(Url).placeholder(R.drawable.ic_landscape_black_24dp)
                .error(R.drawable.ic_landscape_black_24dp).fit()
                .centerCrop().into(ImgView)
    }
}