package com.example.detoxrank.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.detoxrank.data.user.UserDataRepository

class UserDataViewModel(
    private val userDataRepository: UserDataRepository
) : ViewModel() {
    var userDataUiState by mutableStateOf(UserDataUiState())
        private set

    fun updateUiState(newUserDataUiState: UserDataUiState) {
        userDataUiState = newUserDataUiState.copy()
    }

    suspend fun updateUserData() {
        userDataRepository.updateUserData(userDataUiState.toUserDate())
    }
}
