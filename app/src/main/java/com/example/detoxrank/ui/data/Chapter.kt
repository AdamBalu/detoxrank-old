package com.example.detoxrank.ui.data

import androidx.annotation.StringRes

enum class ChapterDifficulty {
    Easy, Medium, Hard
}

enum class ChapterTag {
    Introduction, Dopamine, Reinforcement, Tolerance, HedonicCircuit, Solutions
}

data class Chapter(
    @StringRes var name: Int,
    @StringRes val description: Int,
    val difficulty: ChapterDifficulty,
    var wasCompleted: Boolean = false,
    val tag: ChapterTag,
    val chapterScreenNum: Int = 0,
    @StringRes val startChapterButtonLabel: Int
)
