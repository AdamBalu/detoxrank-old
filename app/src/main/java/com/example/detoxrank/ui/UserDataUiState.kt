package com.example.detoxrank.ui

import com.example.detoxrank.data.TimerDifficulty
import com.example.detoxrank.data.user.UserData

data class UserDataUiState(
    val id: Int = 1,
    val timerStartTimeMillis: Long = 0,
    val timerStarted: Boolean = false,
    val rankPoints: Int = 0,
    val xpPoints: Int = 0,
    val timerDifficulty: TimerDifficulty = TimerDifficulty.Easy,
    val monthlyTasksLastRefreshTime: Long = System.currentTimeMillis(),
    val tasksFinished: Int = 0,
    val pagesRead: Int = 0
)

fun UserDataUiState.toUserData(): UserData = UserData(
    id = id,
    timerStartTimeMillis = timerStartTimeMillis,
    timerStarted = timerStarted,
    rankPoints = rankPoints,
    xpPoints = xpPoints,
    timerDifficulty = timerDifficulty,
    monthlyTasksLastRefreshTime = monthlyTasksLastRefreshTime,
    tasksFinished = tasksFinished,
    pagesRead = pagesRead
)

fun UserData.toUserDataUiState(): UserDataUiState = UserDataUiState(
    id = id,
    timerStartTimeMillis = timerStartTimeMillis,
    timerStarted = timerStarted,
    rankPoints = rankPoints,
    xpPoints = xpPoints,
    timerDifficulty = timerDifficulty,
    monthlyTasksLastRefreshTime = monthlyTasksLastRefreshTime,
    tasksFinished = tasksFinished,
    pagesRead = pagesRead
)