package com.example.detoxrank.ui

import androidx.lifecycle.ViewModel
import com.example.detoxrank.data.Section
import com.example.detoxrank.data.TimerDifficulty
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class DetoxRankViewModel : ViewModel() {
    /** UI state exposed to the UI **/
    private val _uiState = MutableStateFlow(DetoxRankUiState())
    val uiState: StateFlow<DetoxRankUiState> = _uiState.asStateFlow()

    /**
     * Update [currentSection]
     */
    fun updateCurrentSection(section: Section) {
        _uiState.update {
            it.copy(
                currentSection = section
            )
        }
    }

    /**
     * Update [timerDifficulty]
     */
    fun setTimerDifficulty(value: TimerDifficulty) {
        _uiState.update {
            it.copy(
                currentTimerDifficulty = value
            )
        }
    }
}