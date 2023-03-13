package com.example.detoxrank.ui

import com.example.detoxrank.data.Section

data class DetoxRankUiState(
    val currentSection: Section = Section.Rank,
    var progressBarProgression: Float = 0.0f
)
