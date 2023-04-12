package com.example.detoxrank.data.achievements

import kotlinx.coroutines.flow.Flow

interface AchievementRepository {
    fun getAllAchievements(): Flow<List<Achievement>>

    suspend fun update(achievement: Achievement)

    suspend fun insert(achievement: Achievement)

    fun getAchievementById(id: Int): Flow<Achievement?>
}