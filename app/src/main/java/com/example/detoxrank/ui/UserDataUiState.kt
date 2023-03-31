package com.example.detoxrank.ui

import com.example.detoxrank.data.TimerDifficulty
import com.example.detoxrank.data.user.UiTheme
import com.example.detoxrank.data.user.UserData

data class UserDataUiState(
    val id: Int = 1,
    val timerStartedTimeInMillis: Int = 0,
    val tasksStartedTimeInMillis: Int = 0,
    val brainPoints: Int = 0,
    val rankPoints: Int = 0,
    val xpPoints: Int = 0,
    val chosenTimerDifficulty: TimerDifficulty = TimerDifficulty.Easy,
    val selectedTheme: UiTheme = UiTheme.Default
)

fun UserDataUiState.toUserDate(): UserData = UserData(
    id = id,
    timerStartedTimeInMillis = timerStartedTimeInMillis,
    tasksStartedTimeInMillis = tasksStartedTimeInMillis,
    brainPoints = brainPoints,
    rankPoints = rankPoints,
    xpPoints = xpPoints,
    chosenTimerDifficulty = chosenTimerDifficulty,
    selectedTheme = selectedTheme
)

fun UserData.toUserDataUiState(): UserDataUiState = UserDataUiState(
    id = id,
    timerStartedTimeInMillis = timerStartedTimeInMillis,
    tasksStartedTimeInMillis = tasksStartedTimeInMillis,
    brainPoints = brainPoints,
    rankPoints = rankPoints,
    xpPoints = xpPoints,
    chosenTimerDifficulty = chosenTimerDifficulty,
    selectedTheme = selectedTheme
)