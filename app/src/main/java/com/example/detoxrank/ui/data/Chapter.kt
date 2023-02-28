package com.example.detoxrank.ui.data

enum class ChapterDifficulty {
    Easy, Medium, Hard
}

data class Chapter(
    val name: String,
    val description: String,
    val difficulty: ChapterDifficulty,
    val wasCompleted: Boolean = false
)