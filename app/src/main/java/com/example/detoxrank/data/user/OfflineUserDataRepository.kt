package com.example.detoxrank.data.user

import kotlinx.coroutines.flow.Flow

class OfflineUserDataRepository(private val userDataDao: UserDataDao) : UserDataRepository {
    override suspend fun updateUserData(userData: UserData) = userDataDao.update(userData)

    override fun getUserStream(): Flow<UserData> = userDataDao.getUser()
}