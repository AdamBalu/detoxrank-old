package com.example.detoxrank.data.user

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDataDao {
    @Update
    suspend fun update(userData: UserData)

    @Insert
    suspend fun insert(userData: UserData)

    @Query("SELECT * FROM user_data")
    fun getUser(): Flow<UserData>
}