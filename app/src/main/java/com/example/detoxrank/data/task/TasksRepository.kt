package com.example.detoxrank.data.task

import kotlinx.coroutines.flow.Flow

interface TasksRepository {
    suspend fun insertTask(task: Task)
    suspend fun deleteTask(task: Task)
    suspend fun updateTask(task: Task)
    fun getAllTasksStream(): Flow<List<Task>>
    fun getTaskStream(id: Int): Flow<Task?>
    suspend fun selectNRandomTasksByDuration(durationCategory: TaskDurationCategory,
                                          numberOfTasks: Int)
    suspend fun resetTasksFromCategory(durationCategory: TaskDurationCategory)

    fun updateUserRankPoints(amount: Int)

    fun getCompletedTaskNum(taskDurationCategory: TaskDurationCategory): Int

    suspend fun getNewTasks(taskDurationCategory: TaskDurationCategory)
    fun getSelectedTasks(): Flow<List<Task>>

    suspend fun updateRows(rows: List<Task>)
}
