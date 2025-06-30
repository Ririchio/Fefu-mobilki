package com.ririchio.fefu.data.storage

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.ririchio.fefu.data.storage.dao.DaoUser
import com.ririchio.fefu.data.storage.models.User
import com.ririchio.fefu.utils.Const.Companion.DB_NAME

@Database(entities = [User::class], version = 1, exportSchema = true)
abstract class MainDatabase : RoomDatabase() {

    abstract fun userDao(): DaoUser

    companion object {
        @Volatile
        private var INSTANCE: MainDatabase? = null

        fun getInstance(context: Context): MainDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    MainDatabase::class.java,
                    DB_NAME
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}