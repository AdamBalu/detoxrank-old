package com.example.detoxrank.ui.tasks.task

import com.example.detoxrank.data.task.Task
import com.example.detoxrank.data.task.TaskDurationCategory
import com.example.detoxrank.data.task.TaskIconCategory

data class TaskUiState(
    val id: Int = 0,
    val description: String = "",
    val completed: Boolean = false,
    val durationCategory: TaskDurationCategory = TaskDurationCategory.Uncategorized,
    val iconCategory: TaskIconCategory = TaskIconCategory.Other,
    val selectedAsCurrentTask: Boolean = false,
    val language: String = "EN",
    val specialTaskID: Int = 0
)

fun TaskUiState.toTask(): Task = Task(
    id = id,
    description = description,
    completed = completed,
    durationCategory = durationCategory,
    iconCategory = iconCategory,
    selectedAsCurrentTask = selectedAsCurrentTask,
    language = language,
    specialTaskID = specialTaskID
)

fun Task.toTaskUiState(): TaskUiState = TaskUiState(
    id = id,
    description = description,
    completed = completed,
    durationCategory = durationCategory,
    iconCategory = iconCategory,
    selectedAsCurrentTask = selectedAsCurrentTask,
    language = language,
    specialTaskID = specialTaskID
)

fun TaskUiState.isValid(): Boolean = description.isNotBlank()