package com.setiawan.anxdre.footballclubmanager.data

class FavoriteTeam(
        val id: Long?,
        val teamId: String?,
        val teamName: String?,
        val teamBadge: String?) {

    companion object {
        const val TBL_FAVORITE_TEAM: String = "TABLE_FAVORITE_TEAM"
        const val ID_FAVORITE_TEAM: String = "ID_FAVORITE_TEAM"
        const val TEAM_ID: String = "TEAM_ID"
        const val TEAM_NAME: String = "TEAM_NAME"
        const val TEAM_BADGE: String = "TEAM_BADGE"
    }
}