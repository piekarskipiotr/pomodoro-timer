package com.apps.bacon.pomodorotimer.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.apps.bacon.pomodorotimer.data.dao.UserDao
import com.apps.bacon.pomodorotimer.data.dao.WeeklyStatsDao
import com.apps.bacon.pomodorotimer.data.entities.User
import com.apps.bacon.pomodorotimer.data.entities.WeeklyStats

@Database(
    entities = [User::class, WeeklyStats::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun weeklyStatsDao(): WeeklyStatsDao

    companion object {
        @Volatile
        private var instance: AppDatabase? = null
        private const val DATABASE_NAME: String = "products_database"

        @Synchronized
        fun getInstance(context: Context): AppDatabase {
            if (instance == null)
                instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    DATABASE_NAME
                )
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build()
            return instance!!
        }
    }
}