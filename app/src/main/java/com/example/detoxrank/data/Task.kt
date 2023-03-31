package com.example.detoxrank.data

import androidx.annotation.IdRes
import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Task
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.vector.ImageVector

enum class TaskCategory {
    Daily, Weekly, Monthly
}
data class Task (
    @IdRes val id: Int? = null,
    @StringRes val description: Int,
    var completed: MutableState<Boolean> = mutableStateOf(false),
    val category: TaskCategory,
    val icon: ImageVector = Icons.Filled.Task
)
