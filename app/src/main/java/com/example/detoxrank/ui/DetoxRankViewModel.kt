package com.example.detoxrank.ui

import androidx.lifecycle.ViewModel
import com.example.detoxrank.ui.data.Section
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class DetoxRankViewModel : ViewModel() {

    /** UI state exposed to the UI **/
    private val _uiState = MutableStateFlow(DetoxRankUiState())
    val uiState: StateFlow<DetoxRankUiState> = _uiState

    init {
        initializeUIState()
    }

    private fun initializeUIState() {

    }

    /**
     * Update [currentSection]
     */
    fun updateCurrentMailbox(section: Section) {
        _uiState.update {
            it.copy(
                currentSection = section
            )
        }
    }
}
