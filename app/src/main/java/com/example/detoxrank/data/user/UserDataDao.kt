package com.example.detoxrank.data.user

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDataDao {
    @Update
    suspend fun update(userData: UserData)

    @Insert
    suspend fun insert(userData: UserData)

    @Query("SELECT * FROM user_data")
    fun getUser(): Flow<UserData>

    @Transaction
    @Query("UPDATE user_data SET pages_read = pages_read + :amount WHERE id = 1")
    fun updatePagesRead(amount: Int)

    @Transaction
    @Query("UPDATE user_data SET rank_points = rank_points + :amount WHERE id = 1")
    fun updateRankPoints(amount: Int)

    @Transaction
    @Query("UPDATE user_data SET xp_points = xp_points + :amount WHERE id = 1")
    fun updateXpPoints(amount: Int)

    @Transaction
    @Query("UPDATE user_data SET timer_start_time = :time WHERE id = 1")
    fun updateTimerStartTimeMillis(time: Long)

    @Transaction
    @Query("UPDATE user_data SET timer_started = :value WHERE id = 1")
    fun updateTimerStarted(value: Boolean)
}