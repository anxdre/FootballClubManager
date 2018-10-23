package com.setiawan.anxdre.footballclubmanager.data

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import org.jetbrains.anko.db.*

class DatabaseHelper(ctx: Context) : ManagedSQLiteOpenHelper(ctx, "FavoriteTeam.db", null, 1) {
    companion object {
        private var instance: DatabaseHelper? = null

        @Synchronized
        fun getInstance(ctx: Context): DatabaseHelper {
            if (instance == null) {
                instance = DatabaseHelper(ctx.applicationContext)
            }
            return instance as DatabaseHelper
        }
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.createTable(FavoriteEvent.TABLE_FAVORITE, true,
                FavoriteEvent.ID to INTEGER + PRIMARY_KEY + AUTOINCREMENT,
                FavoriteEvent.MATCH_ID to TEXT,
                FavoriteEvent.HOMETITLE to TEXT,
                FavoriteEvent.HOME_ID to TEXT,
                FavoriteEvent.AWAYTITLE to TEXT,
                FavoriteEvent.AWAY_ID to TEXT,
                FavoriteEvent.DATE to TEXT)

        db.createTable(FavoriteTeam.TBL_FAVORITE_TEAM, true,
                FavoriteTeam.ID_FAVORITE_TEAM to INTEGER + PRIMARY_KEY + AUTOINCREMENT,
                FavoriteTeam.TEAM_ID to TEXT + UNIQUE,
                FavoriteTeam.TEAM_NAME to TEXT,
                FavoriteTeam.TEAM_BADGE to TEXT)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.dropTable(FavoriteEvent.TABLE_FAVORITE, true)
        db.dropTable(FavoriteTeam.TBL_FAVORITE_TEAM, true)
    }
}

val Context.database: DatabaseHelper
    get() = DatabaseHelper.getInstance(applicationContext)