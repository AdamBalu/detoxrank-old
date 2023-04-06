package com.example.detoxrank.data.task

import com.example.detoxrank.*
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

    override fun updateUserRankPoints(amount: Int) = taskDao.updateUserRankPoints(amount)
    override fun getCompletedTaskNum(taskDurationCategory: TaskDurationCategory): Int = taskDao.getCompletedTaskNum(taskDurationCategory)

    override suspend fun getNewTasks(taskDurationCategory: TaskDurationCategory)  {
        val completedTasksNum = taskDao.getCompletedTaskNum(taskDurationCategory)
        when (taskDurationCategory) {
            TaskDurationCategory.Daily -> {
                taskDao.updateUserRankPoints(DAILY_TASK_RP_GAIN * completedTasksNum)
                taskDao.resetTasksFromCategory(durationCategory = TaskDurationCategory.Daily)
                taskDao.selectNRandomTasksByDuration(TaskDurationCategory.Daily, NUMBER_OF_TASKS_DAILY)
            }
            TaskDurationCategory.Weekly -> {
                taskDao.updateUserRankPoints(WEEKLY_TASK_RP_GAIN * completedTasksNum)
                taskDao.resetTasksFromCategory(durationCategory = TaskDurationCategory.Weekly)
                taskDao.selectNRandomTasksByDuration(TaskDurationCategory.Weekly, NUMBER_OF_TASKS_WEEKLY)
            }
            TaskDurationCategory.Monthly -> {
                taskDao.updateUserRankPoints(MONTHLY_TASK_RP_GAIN * completedTasksNum)
                taskDao.resetTasksFromCategory(durationCategory = TaskDurationCategory.Monthly)
                taskDao.selectNRandomTasksByDuration(TaskDurationCategory.Monthly, NUMBER_OF_TASKS_MONTHLY)
            }
            else -> {}
        }
    }

    override fun getSelectedTasks(): Flow<List<Task>> = taskDao.getSelectedTasks()

    override suspend fun updateRows(rows: List<Task>) = taskDao.updateRows(rows)
}
