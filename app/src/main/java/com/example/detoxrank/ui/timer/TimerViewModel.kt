package com.example.detoxrank.ui.timer

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.detoxrank.data.TimerDifficultyCard
import com.example.detoxrank.data.local.LocalTimerDifficultyDataProvider

class TimerViewModel : ViewModel() {
    private val _difficultySelectShown = mutableStateOf(false)
    val difficultySelectShown: Boolean
        get() = _difficultySelectShown.value

    private val _difficultyList = LocalTimerDifficultyDataProvider.listOfDifficulties
    val difficultyList: List<TimerDifficultyCard>
        get() = _difficultyList

    fun setDifficultySelectShown(value: Boolean) {
        _difficultySelectShown.value = value
    }
}
