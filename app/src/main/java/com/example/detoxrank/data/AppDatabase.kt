package com.example.detoxrank.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.detoxrank.data.chapter.Chapter
import com.example.detoxrank.data.chapter.ChapterDao
import com.example.detoxrank.data.task.Task
import com.example.detoxrank.data.task.TaskDao
import com.example.detoxrank.data.user.UserData
import com.example.detoxrank.data.user.UserDataDao

@Database(entities = [Task::class, Chapter::class, UserData::class], version = 9)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun taskDao(): TaskDao
    abstract fun chapterDao(): ChapterDao
    abstract fun userDataDao(): UserDataDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                Room.databaseBuilder(
                    context,
                    AppDatabase::class.java,
                    "app_database"
                )
                    .createFromAsset("database/app_database.db")
                    .fallbackToDestructiveMigration()
                    .build()
                    .also {
                        INSTANCE = it
                    }
            }
        }
    }
}