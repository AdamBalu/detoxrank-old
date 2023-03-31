package com.example.detoxrank.data.task

import kotlinx.coroutines.flow.Flow

class OfflineTasksRepository(private val taskDao: TaskDao) : TasksRepository {
    override fun getAllTasksStream(): Flow<List<Task>> = taskDao.getAllTasks()

    override fun getTaskStream(id: Int): Flow<Task> = taskDao.getTask(id)

    override suspend fun insertTask(task: Task) = taskDao.insert(task)

    override suspend fun deleteTask(task: Task) = taskDao.delete(task)

    override suspend fun updateTask(task: Task) = taskDao.update(task)

    override suspend fun selectNRandomTasksByDuration(durationCategory: TaskDurationCategory,
                                                   numberOfTasks: Int) =
        taskDao.selectNRandomTasksByDuration(durationCategory, numberOfTasks)

    override suspend fun resetTasksFromCategory(durationCategory: TaskDurationCategory) =
        taskDao.resetTasksFromCategory(durationCategory)

    override fun getSelectedTasks(): Flow<List<Task>> = taskDao.getSelectedTasks()

    override suspend fun updateRows(rows: List<Task>) = taskDao.updateRows(rows)
}
