package com.example.detoxrank.data.task

import com.example.detoxrank.*
import com.example.detoxrank.data.achievements.AchievementRepository
import com.example.detoxrank.data.user.UserDataRepository
import kotlinx.coroutines.flow.Flow

class OfflineTasksRepository(
    private val taskDao: TaskDao,
    private val achievementRepository: AchievementRepository,
    private val userDataRepository: UserDataRepository) : TasksRepository {
    override fun getAllTasksStream(): Flow<List<Task>> = taskDao.getAllTasks()

    override fun getCompletedTasksByDuration(taskDurationCategory: TaskDurationCategory): Flow<List<Task>> =
        taskDao.getCompletedTasksByDuration(taskDurationCategory)

    override suspend fun insertTask(task: Task) = taskDao.insert(task)

    override suspend fun deleteTask(task: Task) = taskDao.delete(task)

    override suspend fun updateTask(task: Task) = taskDao.update(task)

    override suspend fun selectNRandomTasksByDuration(durationCategory: TaskDurationCategory,
                                                   numberOfTasks: Int) =
        taskDao.selectNRandomTasksByDuration(durationCategory, numberOfTasks)

    override suspend fun resetTasksFromCategory(durationCategory: TaskDurationCategory) =
        taskDao.resetTasksFromCategory(durationCategory)

    override fun getCompletedTaskNum(taskDurationCategory: TaskDurationCategory): Int =
        taskDao.getCompletedTaskNum(taskDurationCategory)

    override suspend fun getNewTasks(taskDurationCategory: TaskDurationCategory)  {
        val completedTasksNum = getCompletedTaskNum(taskDurationCategory)

        // update the number of finished tasks
        userDataRepository.getUserStream().collect { user ->
            userDataRepository.updateUserData(user.copy(tasksFinished = user.tasksFinished + completedTasksNum))
        }

        updateAchievementsProgression(taskDurationCategory)
        when (taskDurationCategory) {
            TaskDurationCategory.Daily -> {
                handleTaskRotation(
                    DAILY_TASK_XP_GAIN,
                    DAILY_TASK_RP_GAIN,
                    NUMBER_OF_TASKS_DAILY,
                    TaskDurationCategory.Daily,
                    completedTasksNum
                )
            }
            TaskDurationCategory.Weekly -> {
                handleTaskRotation(
                    WEEKLY_TASK_XP_GAIN,
                    WEEKLY_TASK_RP_GAIN,
                    NUMBER_OF_TASKS_WEEKLY,
                    TaskDurationCategory.Weekly,
                    completedTasksNum
                )
            }
            TaskDurationCategory.Monthly -> {
                handleTaskRotation(
                    MONTHLY_TASK_XP_GAIN,
                    MONTHLY_TASK_XP_GAIN,
                    NUMBER_OF_TASKS_MONTHLY,
                    TaskDurationCategory.Monthly,
                    completedTasksNum
                )
            }
            else -> {}
        }
    }

    private suspend fun handleTaskRotation(
        xpGain: Int,
        rpGain: Int,
        numOfNewTasks: Int,
        taskDurationCategory: TaskDurationCategory,
        completedTasksNum: Int
    ) {
        userDataRepository.updateRankPoints(rpGain * completedTasksNum)
        userDataRepository.updateXpPoints(xpGain * completedTasksNum)
        resetTasksFromCategory(taskDurationCategory)
        selectNRandomTasksByDuration(taskDurationCategory, numOfNewTasks)
    }

    override fun getSelectedTasks(): Flow<List<Task>> = taskDao.getSelectedTasks()

    override suspend fun updateRows(rows: List<Task>) = taskDao.updateRows(rows)

    private suspend fun updateAchievementsProgression(taskDurationCategory: TaskDurationCategory) {
        updateCompletedTaskNumAchievements()
        getCompletedTasksByDuration(taskDurationCategory).collect { taskList ->
            taskList.forEach {
                when (it.specialTaskID) {
                    ID_RUN_3_KM -> {
                        achievementRepository.getAchievementById(ID_RUN_3_KM).collect { achievement ->
                            if ((achievement != null) && !achievement.achieved)
                                achievementRepository.update(achievement = achievement.copy(achieved = true))
                        }
                    }
                    ID_RUN_5_KM -> {
                        achievementRepository.getAchievementById(ID_RUN_5_KM).collect { achievement ->
                            if ((achievement != null) && !achievement.achieved)
                                achievementRepository.update(achievement = achievement.copy(achieved = true))
                        }
                    }
                    ID_RUN_7_KM -> {
                        achievementRepository.getAchievementById(ID_RUN_7_KM).collect { achievement ->
                            if ((achievement != null) && !achievement.achieved)
                                achievementRepository.update(achievement = achievement.copy(achieved = true))
                        }
                    }
                    ID_RUN_10_KM -> {
                        achievementRepository.getAchievementById(ID_RUN_10_KM).collect { achievement ->
                            if ((achievement != null) && !achievement.achieved)
                                achievementRepository.update(achievement = achievement.copy(achieved = true))
                        }
                    }
                    ID_READ_20_PAGES, ID_READ_50_PAGES, ID_READ_100_PAGES, ID_READ_250_PAGES -> {
                        val pages = when (it.specialTaskID) {
                            ID_READ_20_PAGES -> PAGES_20
                            ID_READ_50_PAGES -> PAGES_50
                            ID_READ_100_PAGES -> PAGES_100
                            ID_READ_250_PAGES -> PAGES_250
                            else -> 0
                        }
                        userDataRepository.updatePagesRead(pages)
                        userDataRepository.getUserStream().collect { user ->
                            val achievementIDToEdit = if (user.pagesRead >= READ_10_BOOKS_PAGE_NUM) {
                                ID_READ_10_BOOKS
                            } else if (user.pagesRead >= READ_5_BOOKS_PAGE_NUM) {
                                ID_READ_5_BOOKS
                            } else if (user.pagesRead >= READ_1_BOOK_PAGE_NUM) {
                                ID_READ_A_BOOK
                            } else if (user.pagesRead >= pages) {
                                it.specialTaskID
                            } else {
                                0
                            }

                            achievementRepository.getAchievementById(achievementIDToEdit).collect { achievement ->
                                if ((achievement != null) && !achievement.achieved)
                                    achievementRepository.update(achievement.copy(achieved = true))
                            }
                        }
                    }
                }
            }
        }
    }

    private suspend fun updateCompletedTaskNumAchievements() {
        userDataRepository.getUserStream().collect { user ->
            val finished = user.tasksFinished
            val achievementToCompleteID = if (finished >= 250) {
                ID_FINISH_250_TASKS
            } else if (finished >= 100) {
                ID_FINISH_100_TASKS
            } else if (finished >= 50) {
                ID_FINISH_50_TASKS
            } else if (finished >= 25) {
                ID_FINISH_25_TASKS
            } else if (finished >= 10) {
                ID_FINISH_10_TASKS
            } else if (finished >= 5) {
                ID_FINISH_5_TASKS
            } else if (finished >= 1) {
                ID_FINISH_FIRST_TASK
            } else {
                0
            }

            achievementRepository.getAchievementById(achievementToCompleteID).collect { achievement ->
                if ((achievement != null) && !achievement.achieved)
                    achievementRepository.update(achievement.copy(achieved = true))
            }
        }
    }
}
