package com.example.detoxrank.ui.data.local

import com.example.detoxrank.R
import com.example.detoxrank.ui.data.Task

object LocalTasksDataProvider {
    val tasks = listOf(
        Task(
            description = R.string.default_task,
            daysForCompletion = 7,
            longDescription = R.string.default_task_long_description
        ),
        Task(
            description = R.string.default_task,
            daysForCompletion = 5
        ),
        Task(
            description = R.string.default_task,
            daysForCompletion = 3
        ),
        Task(
            description = R.string.default_task,
            daysForCompletion = 1
        ),
        Task(
            description = R.string.default_task,
            daysForCompletion = 5
        )
    )

//    val completedTasks = mutableListOf<Task>(
//
//    )
}