package com.example.detoxrank.ui

import com.example.detoxrank.data.TimerDifficulty
import com.example.detoxrank.data.user.UiTheme
import com.example.detoxrank.data.user.UserData

data class UserDataUiState(
    val id: Int = 1,
    val timerStartTimeMillis: Long = 0,
    val timerStarted: Boolean = false,
    val rankPoints: Int = 0,
    val xpPoints: Int = 0,
    val timerDifficulty: TimerDifficulty = TimerDifficulty.Easy,
    val theme: UiTheme = UiTheme.Default,
)

fun UserDataUiState.toUserData(): UserData = UserData(
    id = id,
    timerStartTimeMillis = timerStartTimeMillis,
    timerStarted = timerStarted,
    rankPoints = rankPoints,
    xpPoints = xpPoints,
    timerDifficulty = timerDifficulty,
    theme = theme
)

fun UserData.toUserDataUiState(): UserDataUiState = UserDataUiState(
    id = id,
    timerStartTimeMillis = timerStartTimeMillis,
    timerStarted = timerStarted,
    rankPoints = rankPoints,
    xpPoints = xpPoints,
    timerDifficulty = timerDifficulty,
    theme = theme
)