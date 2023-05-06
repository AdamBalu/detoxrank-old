package com.blaubalu.detoxrank.workers

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.blaubalu.detoxrank.data.AppDatabase
import com.blaubalu.detoxrank.data.achievements.OfflineAchievementRepository
import com.blaubalu.detoxrank.data.task.OfflineTasksRepository
import com.blaubalu.detoxrank.data.task.TaskDurationCategory
import com.blaubalu.detoxrank.data.user.OfflineUserDataRepository
import com.blaubalu.detoxrank.ui.utils.Constants.KEY_TASK_DURATION
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class TaskWorker(
    context: Context,
    params: WorkerParameters
) : CoroutineWorker(context, params) {
    override suspend fun doWork(): Result {
        val taskDurationCategory = inputData.getString(KEY_TASK_DURATION)
        val taskDao = AppDatabase.getDatabase(applicationContext).taskDao()
        val achievementRepository = OfflineAchievementRepository(AppDatabase.getDatabase(applicationContext).achievementDao())
        val userDataRepository = OfflineUserDataRepository(AppDatabase.getDatabase(applicationContext).userDataDao())
        val tasksRepository = OfflineTasksRepository(taskDao, achievementRepository, userDataRepository)
        return withContext(Dispatchers.IO) {
            return@withContext try {
                val category = when (taskDurationCategory) {
                    "Daily" -> TaskDurationCategory.Daily
                    "Weekly" -> TaskDurationCategory.Weekly
                    "Monthly" -> TaskDurationCategory.Monthly
                    else -> TaskDurationCategory.Daily
                }
                tasksRepository.getNewTasks(category)
                Result.success()
            } catch(throwable: Throwable) {
                Result.failure()
            }
        }
    }
}