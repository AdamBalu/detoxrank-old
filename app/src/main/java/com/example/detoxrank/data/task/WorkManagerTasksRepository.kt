package com.example.detoxrank.data.task

import android.content.Context
import androidx.work.Data
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.example.detoxrank.ui.utils.Constants.KEY_TASK_DURATION
import java.util.*
import java.util.concurrent.TimeUnit

class WorkManagerTasksRepository(context: Context) : WMTasksRepository {
//    private val workManager = WorkManager.getInstance(context)
//
//    override fun getNewTasks() {
//        val midnight = Calendar.getInstance().apply {
//            set(Calendar.HOUR_OF_DAY, 23)
//            set(Calendar.MINUTE, 59)
//            set(Calendar.SECOND, 59)
//            set(Calendar.MILLISECOND, 999)
//        }
//
//        val endOfWeek = Calendar.getInstance().apply {
//            set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY)
//            set(Calendar.HOUR_OF_DAY, 23)
//            set(Calendar.MINUTE, 59)
//            set(Calendar.SECOND, 59)
//            set(Calendar.MILLISECOND, 999)
//        }
//
//        val systemTimeMillis = System.currentTimeMillis()
//        val millisUntilMidnight = midnight.timeInMillis - systemTimeMillis
//        val millisUntilEndOfWeek = endOfWeek.timeInMillis - systemTimeMillis
//
//        val repeatingRequestDay = PeriodicWorkRequestBuilder<TaskWorker>(
//            repeatInterval = 1,
//            repeatIntervalTimeUnit = TimeUnit.DAYS)
//            .setInitialDelay(millisUntilMidnight, TimeUnit.MILLISECONDS)
//            .setInputData(createInputDataForWorkRequest(TaskDurationCategory.Daily))
//            .build()
//
//        val repeatingRequestWeek = PeriodicWorkRequestBuilder<TaskWorker>(
//            repeatInterval = 7,
//            repeatIntervalTimeUnit = TimeUnit.DAYS
//        )
//            .setInitialDelay(millisUntilEndOfWeek, TimeUnit.MILLISECONDS)
//            .setInputData(createInputDataForWorkRequest(TaskDurationCategory.Weekly))
//            .build()
//
//        workManager.enqueueUniquePeriodicWork(
//            "tasks_daily",
//            ExistingPeriodicWorkPolicy.CANCEL_AND_REENQUEUE,
//            repeatingRequestDay
//        )
//
//        workManager.enqueueUniquePeriodicWork(
//            "tasks_weekly",
//            ExistingPeriodicWorkPolicy.CANCEL_AND_REENQUEUE,
//            repeatingRequestWeek
//        )
//    }
}

private fun createInputDataForWorkRequest(taskDurationCategory: TaskDurationCategory): Data {
    val builder = Data.Builder()
    builder.putString(KEY_TASK_DURATION, taskDurationCategory.toString())
    return builder.build()
}