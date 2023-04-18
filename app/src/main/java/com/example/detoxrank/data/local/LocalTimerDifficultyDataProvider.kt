package com.example.detoxrank.data.local

import androidx.compose.ui.unit.dp
import com.example.detoxrank.R
import com.example.detoxrank.data.TimerDifficulty
import com.example.detoxrank.data.TimerDifficultyCard

object LocalTimerDifficultyDataProvider {
    val listOfDifficulties = listOf(
        TimerDifficultyCard(
            difficulty = TimerDifficulty.Easy,
            avoidList = listOf(
                R.string.technology,
                R.string.sweets,
                R.string.drugs,
                R.string.pornography
            ),
            backgroundImageRes = R.drawable.timer_easy_difficulty_background
        ),
        TimerDifficultyCard(
            difficulty = TimerDifficulty.Medium,
            avoidList = listOf(
                R.string.technology,
                R.string.fastfood,
                R.string.sweets,
                R.string.drugs,
                R.string.pornography,
                R.string.tv,
                R.string.risktaking
            ),
            backgroundImageRes = R.drawable.timer_medium_difficulty_background
        ),
        TimerDifficultyCard(
            difficulty = TimerDifficulty.Hard,
            avoidList = listOf(
                R.string.technology,
                R.string.fastfood,
                R.string.sweets,
                R.string.drugs,
                R.string.pornography,
                R.string.tv,
                R.string.risktaking,
                R.string.shopping,
                R.string.coffee,
                R.string.music,
                R.string.parties
            ),
            backgroundImageRes = R.drawable.timer_hard_difficulty_background
        )
    )
}