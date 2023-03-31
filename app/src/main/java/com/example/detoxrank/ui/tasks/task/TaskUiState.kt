package com.example.detoxrank.ui.tasks.task

import com.example.detoxrank.data.task.Task
import com.example.detoxrank.data.task.TaskDurationCategory
import com.example.detoxrank.data.task.TaskIconCategory

data class TaskUiState(
    val id: Int = 0,
    val description: String = "",
    val completed: Boolean = false,
    val category: TaskDurationCategory = TaskDurationCategory.Uncategorized,
    val iconCategory: TaskIconCategory = TaskIconCategory.Other,
    val selectedAsCurrentTask: Boolean = false,
    val language: String = "EN"
)

fun TaskUiState.toTask(): Task = Task(
    id = id,
    description = description,
    completed = completed,
    durationCategory = category,
    iconCategory = iconCategory,
    selectedAsCurrentTask = selectedAsCurrentTask,
    language = language
)

fun Task.toTaskUiState(): TaskUiState = TaskUiState(
    id = id,
    description = description,
    completed = completed,
    category = durationCategory,
    iconCategory = iconCategory,
    selectedAsCurrentTask = selectedAsCurrentTask,
    language = language
)

fun TaskUiState.isValid(): Boolean = description.isNotBlank()