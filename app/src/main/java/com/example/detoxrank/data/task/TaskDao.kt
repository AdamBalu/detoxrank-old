package com.example.detoxrank.data.task

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {
    @Insert
    suspend fun insert(task: Task)

    @Delete
    suspend fun delete(task: Task)

    @Update
    suspend fun update(task: Task)

    @Query("SELECT * FROM task WHERE id = :id")
    fun getTask(id: Int): Flow<Task>

    @Query("SELECT * FROM task")
    fun getAllTasks(): Flow<List<Task>>

    @Transaction
    @Query("UPDATE task SET selected = 1 WHERE duration_category = :durationCategory AND id IN (SELECT id FROM task WHERE duration_category = :durationCategory ORDER BY RANDOM() LIMIT :numberOfTasks)")
    suspend fun selectNRandomTasksByDuration(durationCategory: TaskDurationCategory,
                                  numberOfTasks: Int)

    @Transaction
    @Query("UPDATE task SET selected = 0, completed = 0 WHERE duration_category = :durationCategory")
    suspend fun resetTasksFromCategory(durationCategory: TaskDurationCategory)

    @Query("SELECT * FROM task WHERE selected = 1")
    fun getSelectedTasks(): Flow<List<Task>>

    @Update
    suspend fun updateRows(taskRows: List<Task>)
}