package com.apps.bacon.pomodorotimer.data

import android.content.Context
import android.os.Environment
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.apps.bacon.pomodorotimer.R
import com.apps.bacon.pomodorotimer.data.dao.UserDao
import com.apps.bacon.pomodorotimer.data.dao.WeeklyStatsDao
import com.apps.bacon.pomodorotimer.data.entities.Alarm
import com.apps.bacon.pomodorotimer.data.entities.User
import com.apps.bacon.pomodorotimer.data.entities.WeeklyStats
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(
    entities = [User::class, WeeklyStats::class, Alarm::class],
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
                    .addCallback(object : RoomDatabase.Callback() {
                        override fun onCreate(db: SupportSQLiteDatabase) {
                            super.onCreate(db)
                            CoroutineScope(Dispatchers.Default).launch {
                                instance!!.userDao().insertAlarm(
                                    Alarm(
                                        1,
                                        R.raw.yourefinallyawake,
                                        R.raw.yourefinallyawake,
                                        "${context.getExternalFilesDir(Environment.DIRECTORY_MUSIC)}/pomodoro_user_alarm.mp3"
                                    )
                                )
                            }
                        }
                    })
                    .allowMainThreadQueries()
                    .build()
            return instance!!
        }
    }
}