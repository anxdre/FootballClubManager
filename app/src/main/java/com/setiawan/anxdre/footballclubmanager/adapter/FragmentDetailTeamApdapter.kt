package com.setiawan.anxdre.footballclubmanager.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.setiawan.anxdre.footballclubmanager.fragments.FragmentDetailTeamOverview
import com.setiawan.anxdre.footballclubmanager.fragments.FragmentDetailTeamPlayerList

class FragmentDetailTeamApdapter(mFragment: FragmentManager) : FragmentPagerAdapter(mFragment) {
    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> {
                FragmentDetailTeamOverview()
            }
            else -> {
                return FragmentDetailTeamPlayerList()

            }
        }
    }

    override fun getCount(): Int {
        return 2
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when (position) {
            0 -> "Overview"
            else -> "Players"
        }
    }
}