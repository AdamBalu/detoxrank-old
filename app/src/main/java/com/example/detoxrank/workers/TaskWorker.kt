package com.example.detoxrank.workers

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.detoxrank.KEY_TASK_DURATION
import com.example.detoxrank.data.AppDatabase
import com.example.detoxrank.data.task.OfflineTasksRepository
import com.example.detoxrank.data.task.TaskDurationCategory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class TaskWorker(
    context: Context,
    params: WorkerParameters
) : CoroutineWorker(context, params) {
    override suspend fun doWork(): Result {
        val taskDurationCategory = inputData.getString(KEY_TASK_DURATION)
        Log.d("TaskWorker", "Got into task worker, taskDurationCategory:<$taskDurationCategory>")
        val taskDao = AppDatabase.getDatabase(applicationContext).taskDao()
        val tasksRepository = OfflineTasksRepository(taskDao)
        return withContext(Dispatchers.IO) {
            return@withContext try {
                val category = when (taskDurationCategory) {
                    "Daily" -> TaskDurationCategory.Daily
                    "Weekly" -> TaskDurationCategory.Weekly
                    "Monthly" -> TaskDurationCategory.Monthly
                    else -> TaskDurationCategory.Daily
                }
                Log.d("TaskWorker", "Got into coroutine context, category:<${category}>")
                tasksRepository.getNewTasks(category)
                Result.success()
            } catch(throwable: Throwable) {
                Result.failure()
            }
        }
    }
}