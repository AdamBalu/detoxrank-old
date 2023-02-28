package com.example.detoxrank.ui

import com.example.detoxrank.ui.data.Section

data class DetoxRankUiState(
    val currentSection: Section = Section.Rank,
    var isTheoryLaunched: Boolean = false
)
