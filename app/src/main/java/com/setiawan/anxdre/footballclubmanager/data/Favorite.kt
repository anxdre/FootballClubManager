package com.setiawan.anxdre.footballclubmanager.data

data class Favorite(val id: Long?, val MatchId: String?,
                    val HomeTitle: String?, val HomeId: String?,
                    val AwayTitle: String?, val AwayId: String?, val Date: String?) {

    companion object {
        const val TABLE_FAVORITE: String = "TABLE_FAVORITE"
        const val ID: String = "ID_"
        const val MATCH_ID: String = "MATCH_ID"
        const val HOMETITLE: String = "HOMETITLE"
        const val HOME_ID: String = "HOME_ID"
        const val AWAYTITLE: String = "AWAYTITLE"
        const val AWAY_ID: String = "AWAY_ID"
        const val DATE: String = "DATE"
    }
}