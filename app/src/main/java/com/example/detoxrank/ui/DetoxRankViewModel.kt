package com.example.detoxrank.ui

import androidx.lifecycle.ViewModel
import com.example.detoxrank.ui.data.Section
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class DetoxRankViewModel : ViewModel() {

    /** UI state exposed to the UI **/
    private val _uiState = MutableStateFlow(DetoxRankUiState())
    val uiState: StateFlow<DetoxRankUiState> = _uiState.asStateFlow()

    init {
        initializeUIState()
    }

    private fun initializeUIState() {

    }

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
     * Update [isTheoryLaunched]
     */
    fun updateTheoryLaunchState(isTheoryLaunched: Boolean) {
        _uiState.update {
            it.copy(
                isTheoryLaunched = isTheoryLaunched
            )
        }
    }

    fun getProgressBarValue(): Float {
        return uiState.value.progressBarProgression
    }

    fun updateProgressBarProgression(valueToAdd: Float) {
        val progression = getProgressBarValue() + valueToAdd
        _uiState.update {
            it.copy(
                progressBarProgression = progression
            )
        }
    }

    fun resetProgressBarProgression() {
        _uiState.update {
            it.copy(
                progressBarProgression = 0f
            )
        }
    }
}
