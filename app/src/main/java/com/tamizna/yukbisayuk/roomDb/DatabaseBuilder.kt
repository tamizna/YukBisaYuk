package com.tamizna.yukbisayuk.roomDb

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.tamizna.yukbisayuk.models.User
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

object DatabaseBuilder {

    private var INSTANCE: AppDatabase? = null

    fun getInstance(context: Context): AppDatabase {
        if (INSTANCE == null) {
            synchronized(AppDatabase::class) {
                INSTANCE = buildRoomDB(context)
            }
        }
        return INSTANCE!!
    }

    private fun buildRoomDB(context: Context) =
        Room.databaseBuilder(
            context.applicationContext,
            AppDatabase::class.java,
            "yukbisayuk_db"
        ).addCallback(object : RoomDatabase.Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                GlobalScope.launch {
                    getInstance(context).userDao().run {
                            this.insertUser(
                                User(
                                    0, "tamizna", "tralala123"
                                )
                            )
                    }
                }
            }
        }).build()

}