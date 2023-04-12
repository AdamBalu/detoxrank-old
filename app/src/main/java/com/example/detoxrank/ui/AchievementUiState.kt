package com.example.detoxrank.ui

import com.example.detoxrank.data.achievements.Achievement
import com.example.detoxrank.data.achievements.AchievementDifficulty

data class AchievementUiState(
    val id: Int = 0,
    val achieved: Boolean = false,
    val name: String = "",
    val description: String = "",
    val difficulty: AchievementDifficulty = AchievementDifficulty.EASY
)

fun AchievementUiState.toAchievement(): Achievement = Achievement(
    id = id,
    achieved = achieved,
    name = name,
    description = description,
    difficulty = difficulty
)

fun Achievement.toAchievementUiState(): AchievementUiState = AchievementUiState(
    id = id,
    achieved = achieved,
    name = name,
    description = description,
    difficulty = difficulty
)
