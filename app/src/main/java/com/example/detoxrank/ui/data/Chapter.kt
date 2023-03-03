package com.example.detoxrank.ui.data

enum class ChapterDifficulty {
    Easy, Medium, Hard
}

enum class ChapterTag {
    Dopamine, HedonicsVsDopaminergics, Reinforcement, PREP, Solutions
}

data class Chapter(
    val name: String,
    val description: String,
    val difficulty: ChapterDifficulty,
    val wasCompleted: Boolean = false,
    val tag: ChapterTag,
    val chapterScreenNum: Int = 0
)