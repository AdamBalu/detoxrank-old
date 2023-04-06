package com.example.detoxrank.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.detoxrank.data.Section
import com.example.detoxrank.data.TimerDifficulty
import com.example.detoxrank.data.user.Rank
import com.example.detoxrank.data.user.UserDataRepository
import kotlinx.coroutines.flow.*

class DetoxRankViewModel(
    private val userDataRepository: UserDataRepository
) : ViewModel() {
    /** UI state exposed to the UI **/
    private val _uiState = MutableStateFlow(DetoxRankUiState())
    val uiState: StateFlow<DetoxRankUiState> = _uiState.asStateFlow()

    var userDataUiState by mutableStateOf(UserDataUiState())
        private set

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

    fun getRankProgressBarValue(): Float {
        return _uiState.value.rankProgressBarProgression
    }

    fun getLevelProgressBarValue(): Float {
        return _uiState.value.levelProgressBarProgression
    }

    fun updateRankProgressBarProgression(valueToAdd: Float) {
        val progression = getRankProgressBarValue() + valueToAdd
        _uiState.update {
            it.copy(
                rankProgressBarProgression = progression
            )
        }
    }

    fun setRankProgressBar(value: Float) {
        _uiState.update {
            it.copy(
                rankProgressBarProgression = value
            )
        }
    }

    fun resetRankProgressBarProgression() {
        _uiState.update {
            it.copy(
                rankProgressBarProgression = 0f
            )
        }
    }

    suspend fun getUserRankPoints(): Int {
        return userDataRepository.getUserStream().first().rankPoints
    }

    suspend fun updateUserData() {
        userDataRepository.updateUserData(userDataUiState.toUserData())
    }

    fun updateUiState(newDataUiState: UserDataUiState) {
        userDataUiState = newDataUiState.copy()
    }

    suspend fun updateUserRankPoints(toAdd: Int) {
        val user = userDataRepository.getUserStream().first()
        updateUiState(user.copy(rankPoints = user.rankPoints + toAdd).toUserDataUiState())
        updateUserData()
    }

    fun getCurrentRank(rankPoints: Int): Pair<Rank, Pair<Int, Int>> {
        return when (rankPoints) {
            in bronze1.first..bronze1.second -> Pair(Rank.Bronze1, bronze1)
            in bronze2.first..bronze2.second -> Pair(Rank.Bronze2, bronze2)
            in bronze3.first..bronze3.second -> Pair(Rank.Bronze3, bronze3)

            in silver1.first..silver1.second -> Pair(Rank.Silver1, silver1)
            in silver2.first..silver2.second -> Pair(Rank.Silver2, silver2)
            in silver3.first..silver3.second -> Pair(Rank.Silver3, silver3)

            in gold1.first..gold1.second -> Pair(Rank.Gold1, gold1)
            in gold2.first..gold2.second -> Pair(Rank.Gold2, gold2)
            in gold3.first..gold3.second -> Pair(Rank.Gold3, gold3)

            in platinum1.first..platinum1.second -> Pair(Rank.Platinum1, platinum1)
            in platinum2.first..platinum2.second -> Pair(Rank.Platinum2, platinum2)
            in platinum3.first..platinum3.second -> Pair(Rank.Platinum3, platinum3)

            in diamond1.first..diamond1.second -> Pair(Rank.Diamond1, diamond1)
            in diamond2.first..diamond2.second -> Pair(Rank.Diamond2, diamond2)
            in diamond3.first..diamond3.second -> Pair(Rank.Diamond3, diamond3)

            in master.first..master.second -> Pair(Rank.Master, master)
            else -> Pair(Rank.Legend, Pair(0, 0))
        }
    }

    companion object {
        private val bronze1 = Pair(0, 149)
        private val bronze2 = Pair(150, 499)
        private val bronze3 = Pair(500, 999)

        private val silver1 = Pair(1000, 1599)
        private val silver2 = Pair(1600, 2199)
        private val silver3 = Pair(2200, 2999)

        private val gold1 = Pair(3000, 3699)
        private val gold2 = Pair(3700, 4699)
        private val gold3 = Pair(4700, 5699)

        private val platinum1 = Pair(5700, 6599)
        private val platinum2 = Pair(6600, 7599)
        private val platinum3 = Pair(7600, 8699)

        private val diamond1 = Pair(8700, 9999)
        private val diamond2 = Pair(10000, 11999)
        private val diamond3 = Pair(12000, 14999)

        private val master = Pair(15000, 19999)
    }
}