package com.example.detoxrank.data.user

import kotlinx.coroutines.flow.Flow

interface UserDataRepository {
    suspend fun updateUserData(userData: UserData)
    fun getUserStream(): Flow<UserData>
}
