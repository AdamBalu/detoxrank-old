package com.example.detoxrank.data

import android.content.Context
import com.example.detoxrank.data.chapter.ChaptersRepository
import com.example.detoxrank.data.chapter.OfflineChaptersRepository
import com.example.detoxrank.data.task.OfflineTasksRepository
import com.example.detoxrank.data.task.TasksRepository
import com.example.detoxrank.data.user.OfflineUserDataRepository
import com.example.detoxrank.data.user.UserDataRepository

interface AppContainer {
    val tasksRepository: TasksRepository
    val chaptersRepository: ChaptersRepository
    val userDataRepository: UserDataRepository
}

class AppDataContainer(private val context: Context): AppContainer {
    override val tasksRepository: TasksRepository by lazy {
        OfflineTasksRepository(AppDatabase.getDatabase(context).taskDao())
    }
    override val chaptersRepository: ChaptersRepository by lazy {
        OfflineChaptersRepository(AppDatabase.getDatabase(context).chapterDao())
    }
    override val userDataRepository: UserDataRepository by lazy {
        OfflineUserDataRepository(AppDatabase.getDatabase(context).userDataDao())
    }
}