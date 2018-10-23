package com.setiawan.anxdre.footballclubmanager

import android.database.sqlite.SQLiteConstraintException
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.setiawan.anxdre.footballclubmanager.adapter.FragmentDetailTeamApdapter
import com.setiawan.anxdre.footballclubmanager.data.FavoriteTeam
import com.setiawan.anxdre.footballclubmanager.data.database
import com.setiawan.anxdre.footballclubmanager.utils.DownloadImg
import kotlinx.android.synthetic.main.activity_team_detail.*
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.delete
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.db.select
import org.jetbrains.anko.toast

class TeamDetail : AppCompatActivity() {
    private var mIsFavorite: Boolean = false

    val id: String by lazy {
        intent.getStringExtra("TeamId")
    }

    val mBadgePath: String by lazy {
        intent.getStringExtra("BadgePath")
    }
    val mName: String? by lazy {
        intent.getStringExtra("Name")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_team_detail)

        DownloadImg.getImage(mBadgePath, Iv_BadgeDetail)
        Tv_NameTeamDetail.text = mName

        val fragmentAdapter = FragmentDetailTeamApdapter(supportFragmentManager)
        VP_TeamDetail.adapter = fragmentAdapter
        tabs_TeamDetail.setupWithViewPager(VP_TeamDetail)

        favoriteState()
        setFavorite()

        Fab_AddFavoriteTeam.setOnClickListener {
            //
            if (mIsFavorite) {
                removeFromFavorite()
                finish()
            } else {
                database.use {
                    insert(FavoriteTeam.TBL_FAVORITE_TEAM,
                            FavoriteTeam.TEAM_ID to id,
                            FavoriteTeam.TEAM_NAME to mName,
                            FavoriteTeam.TEAM_BADGE to mBadgePath)
                }
                toast("Added To Favorite Team")
                finish()
            }
            mIsFavorite = !mIsFavorite
            setFavorite()
        }

    }

    private fun favoriteState() {
        database.use {
            val result = select(FavoriteTeam.TBL_FAVORITE_TEAM)
                    .whereArgs("(TEAM_ID = {id})",
                            "id" to id)
            val favorite = result.parseList(classParser<FavoriteTeam>())
            if (!favorite.isEmpty()) mIsFavorite = true
        }
    }

    private fun removeFromFavorite() {
        try {
            database.use {
                delete(FavoriteTeam.TBL_FAVORITE_TEAM, "(TEAM_ID = {id})",
                        "id" to id)
            }
            toast("Deleted From Favorite Team")
        } catch (e: SQLiteConstraintException) {
            toast(e.toString())
        }
    }

    private fun setFavorite() {
        if (mIsFavorite)
            Fab_AddFavoriteTeam.setImageResource(R.drawable.ic_delete_forever_black_24dp)
        else
            Fab_AddFavoriteTeam.setImageResource(R.drawable.ic_favorite_black_24dp)
    }
}
