package com.setiawan.anxdre.footballclubmanager.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.setiawan.anxdre.footballclubmanager.fragments.FragmentFavoriteEvent
import com.setiawan.anxdre.footballclubmanager.fragments.FragmentLastEvent
import com.setiawan.anxdre.footballclubmanager.fragments.FragmentUpcomingEvent

class FragmentEventApdapter(mFragment: FragmentManager) : FragmentPagerAdapter(mFragment) {
    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> {
                FragmentLastEvent()
            }
            1 -> {
                FragmentUpcomingEvent()
            }
            else -> {
                return FragmentFavoriteEvent()

            }
        }
    }

    override fun getCount(): Int {
        return 3
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when (position) {
            0 -> "Last Event"
            1 -> "Upcoming Event"
            else -> "Favorite"
        }
    }
}