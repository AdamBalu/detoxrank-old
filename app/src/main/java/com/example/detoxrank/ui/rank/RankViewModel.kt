package com.example.detoxrank.ui.rank

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.detoxrank.data.achievements.Achievement
import com.example.detoxrank.data.achievements.AchievementRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class RankViewModel(
    achievementRepository: AchievementRepository
) : ViewModel() {
    val achievementsHomeUiState: StateFlow<AchievementHomeUiState> = achievementRepository
        .getAllAchievements()
        .map { AchievementHomeUiState(it) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
            initialValue = AchievementHomeUiState()
        )

    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }

    private val _achievementsDisplayed = mutableStateOf(false)
    val achievementsDisplayed: MutableState<Boolean>
        get() = _achievementsDisplayed

    fun setAchievementsDisplayed(isDisplayed: Boolean) {
        _achievementsDisplayed.value = isDisplayed
    }
}

data class AchievementHomeUiState(
    val achievementList: List<Achievement> = listOf()
)