package com.example.detoxrank.ui.data

enum class ChapterDifficulty {
    Easy, Medium, Hard
}

enum class ChapterTag {
    Introduction, Dopamine, Reinforcement, Tolerance, PREP, Solutions
}

data class Chapter(
    var name: String,
    val description: String,
    val difficulty: ChapterDifficulty,
    val wasCompleted: Boolean = false,
    val tag: ChapterTag,
    val chapterScreenNum: Int = 0
)