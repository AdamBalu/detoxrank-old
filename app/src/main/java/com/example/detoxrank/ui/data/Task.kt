package com.example.detoxrank.ui.data

import androidx.annotation.StringRes

data class Task (
    @StringRes val description: Int,
    @StringRes val longDescription: Int? = null,
    val daysForCompletion: Int,
    val completed: Boolean = false,
    val timesCompleted: Int = 0
)