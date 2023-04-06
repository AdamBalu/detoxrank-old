package com.example.detoxrank.ui

import com.example.detoxrank.data.Section
import com.example.detoxrank.data.TimerDifficulty

data class DetoxRankUiState(
    val currentSection: Section = Section.Rank,
    var progressBarProgression: Float = 0.0f,
    val currentTimerDifficulty: TimerDifficulty = TimerDifficulty.Easy,
    val rankProgressBarProgression: Float = 0.0f,
    val levelProgressBarProgression: Float = 0.0f
)
