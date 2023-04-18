package com.example.detoxrank.data

import androidx.annotation.DrawableRes
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

enum class TimerDifficulty {
    Easy, Medium, Hard
}

data class TimerDifficultyCard(
    val difficulty: TimerDifficulty,
    val avoidList: List<Int>,
    @DrawableRes val backgroundImageRes: Int
)
