package com.example.detoxrank.data.user

import kotlinx.coroutines.flow.Flow

class OfflineUserDataRepository(private val userDataDao: UserDataDao) : UserDataRepository {
    override suspend fun updateUserData(userData: UserData) = userDataDao.update(userData)
    override fun getUserStream(): Flow<UserData> = userDataDao.getUser()
    override suspend fun insertUserData(userData: UserData) = userDataDao.insert(userData)

    override fun updatePagesRead(amount: Int) = userDataDao.updatePagesRead(amount)
    override fun updateRankPoints(amount: Int) = userDataDao.updateRankPoints(amount)
    override fun updateXpPoints(amount: Int) = userDataDao.updateXpPoints(amount)
}