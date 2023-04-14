package com.example.detoxrank.data.user

import com.example.detoxrank.data.TimerDifficulty
import kotlinx.coroutines.flow.Flow

interface UserDataRepository {
    suspend fun updateUserData(userData: UserData)
    suspend fun insertUserData(userData: UserData)
    fun getUserStream(): Flow<UserData>
    fun updatePagesRead(amount: Int)
    fun updateRankPoints(amount: Int)
    fun updateXpPoints(amount: Int)
    fun updateTimerStartTimeMillis(time: Long)
    fun updateTimerStarted(value: Boolean)
    fun updateTimerDifficulty(value: TimerDifficulty)
}
