package com.example.detoxrank.data.achievements

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface AchievementDao {
    @Query("SELECT * FROM achievement")
    fun getAllAchievements(): Flow<List<Achievement>>

    @Update
    suspend fun update(achievement: Achievement)

    @Insert
    suspend fun insert(achievement: Achievement)

    @Query("SELECT * FROM achievement WHERE id = :id")
    fun getById(id: Int): Flow<Achievement?>
}
