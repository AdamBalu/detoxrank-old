package com.example.detoxrank.data.task

import android.content.Context
import androidx.work.Data
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.example.detoxrank.ui.utils.Constants.KEY_TASK_DURATION
import com.example.detoxrank.workers.TaskWorker
import java.util.*
import java.util.concurrent.TimeUnit

class WorkManagerTasksRepository(context: Context) : WMTasksRepository {
    private val workManager = WorkManager.getInstance(context)

    override fun getNewTasks() {
        val midnight = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, 23)
            set(Calendar.MINUTE, 59)
            set(Calendar.SECOND, 59)
            set(Calendar.MILLISECOND, 999)
        }

        val endOfWeek = Calendar.getInstance().apply {
            set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY)
            set(Calendar.HOUR_OF_DAY, 23)
            set(Calendar.MINUTE, 59)
            set(Calendar.SECOND, 59)
            set(Calendar.MILLISECOND, 999)
        }

        val endOfMonth = Calendar.getInstance().apply {
            set(Calendar.DAY_OF_MONTH, getActualMaximum(Calendar.DAY_OF_MONTH))
            set(Calendar.HOUR_OF_DAY, 23)
            set(Calendar.MINUTE, 59)
            set(Calendar.SECOND, 59)
            set(Calendar.MILLISECOND, 999)
        }

        val systemTimeMillis = System.currentTimeMillis()
        val millisUntilMidnight = midnight.timeInMillis - systemTimeMillis
        val millisUntilEndOfWeek = endOfWeek.timeInMillis - systemTimeMillis
        val millisUntilEndOfMonth = endOfMonth.timeInMillis - systemTimeMillis

        val repeatingRequestDay = PeriodicWorkRequestBuilder<TaskWorker>(
            repeatInterval = 1,
            repeatIntervalTimeUnit = TimeUnit.DAYS)
            .setInitialDelay(millisUntilMidnight, TimeUnit.MILLISECONDS)
            .setInputData(createInputDataForWorkRequest(TaskDurationCategory.Daily))
            .build()

        val repeatingRequestWeek = PeriodicWorkRequestBuilder<TaskWorker>(
            repeatInterval = 7,
            repeatIntervalTimeUnit = TimeUnit.DAYS
        )
            .setInitialDelay(millisUntilEndOfWeek, TimeUnit.MILLISECONDS)
            .setInputData(createInputDataForWorkRequest(TaskDurationCategory.Weekly))
            .build()

        val repeatingRequestMonth = PeriodicWorkRequestBuilder<TaskWorker>(
            repeatInterval = 30,
            repeatIntervalTimeUnit = TimeUnit.DAYS
        )
            .setInitialDelay(millisUntilEndOfMonth, TimeUnit.MILLISECONDS)
            .setInputData(createInputDataForWorkRequest(TaskDurationCategory.Monthly))
            .build()

        workManager.enqueueUniquePeriodicWork(
            "tasks_daily",
            ExistingPeriodicWorkPolicy.CANCEL_AND_REENQUEUE,
            repeatingRequestDay
        )

        workManager.enqueueUniquePeriodicWork(
            "tasks_weekly",
            ExistingPeriodicWorkPolicy.CANCEL_AND_REENQUEUE,
            repeatingRequestWeek
        )

        workManager.enqueueUniquePeriodicWork(
            "tasks_monthly",
            ExistingPeriodicWorkPolicy.CANCEL_AND_REENQUEUE,
            repeatingRequestMonth
        )
    }

    override fun checkNewMonthTasks() {
//        val endOfMonth = Calendar.getInstance().apply {
//            set(Calendar.DAY_OF_MONTH, getActualMaximum(Calendar.DAY_OF_MONTH))
//            set(Calendar.HOUR_OF_DAY, 23)
//            set(Calendar.MINUTE, 59)
//        }
//        if (endOfMonth.timeInMillis == System.currentTimeMillis()) {
//            val oneTimeRequestMonth = OneTimeWorkRequestBuilder<TaskWorker>()
//                .setInputData(createInputDataForWorkRequest(TaskDurationCategory.Monthly))
//                .build()
//            workManager.enqueue(oneTimeRequestMonth)
//        }
    }
    override fun checkNewMonthTasksTest() {}
}

private fun createInputDataForWorkRequest(taskDurationCategory: TaskDurationCategory): Data {
    val builder = Data.Builder()
    builder.putString(KEY_TASK_DURATION, taskDurationCategory.toString())
    return builder.build()
}