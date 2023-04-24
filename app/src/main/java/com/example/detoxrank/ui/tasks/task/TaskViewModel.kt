package com.example.detoxrank.ui.tasks.task

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.detoxrank.data.task.Task
import com.example.detoxrank.data.task.TaskDurationCategory
import com.example.detoxrank.data.task.TasksRepository
import com.example.detoxrank.data.task.WMTasksRepository
import com.example.detoxrank.data.user.UserDataRepository
import com.example.detoxrank.ui.utils.Constants.NUMBER_OF_TASKS_DAILY
import com.example.detoxrank.ui.utils.Constants.NUMBER_OF_TASKS_MONTHLY
import com.example.detoxrank.ui.utils.Constants.NUMBER_OF_TASKS_WEEKLY
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Calendar

class TaskViewModel(
    application: Application,
    private val tasksRepository: TasksRepository,
    private val wmTasksRepository: WMTasksRepository,
    private val userDataRepository: UserDataRepository
    ) : AndroidViewModel(application) {
    private val sharedPrefs = application.getSharedPreferences(
        application.packageName + "_preferences",
        Context.MODE_PRIVATE
    )

    val wereTasksOpened = mutableStateOf(false)

    suspend fun firstRunGetTasks() {
        val firstRun = sharedPrefs.getBoolean("first_run", true)
        if (firstRun) {
            getNewTasksWithoutProgress(TaskDurationCategory.Daily, NUMBER_OF_TASKS_DAILY)
            getNewTasksWithoutProgress(TaskDurationCategory.Weekly, NUMBER_OF_TASKS_WEEKLY)
            getNewTasksWithoutProgress(TaskDurationCategory.Monthly, NUMBER_OF_TASKS_MONTHLY)
            withContext(Dispatchers.IO) {
                userDataRepository.updateDailyTasksLastRefreshTime(System.currentTimeMillis())
                userDataRepository.updateWeeklyTasksLastRefreshTime(System.currentTimeMillis())
                userDataRepository.updateMonthlyTasksLastRefreshTime(System.currentTimeMillis())
            }
//            wmTasksRepository.getNewTasks()
            sharedPrefs.edit().putBoolean("first_run", false).apply()
        }
    }

    var taskUiState by mutableStateOf(TaskUiState())
        private set

    fun updateUiState(newTaskUiState: TaskUiState) {
        taskUiState = newTaskUiState.copy()
    }

    private val _taskList = mutableStateListOf<Task>()
    val taskList: List<Task>
        get() = _taskList

    suspend fun updateTask() {
        if (taskUiState.isValid())
            tasksRepository.updateTask(taskUiState.toTask())
    }

    fun selectSpecialTasks() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                tasksRepository.selectSpecialTasks()
            }
        }
    }

    fun getCompletedTasksByDuration(taskDurationCategory: TaskDurationCategory): Flow<List<Task>> {
        return tasksRepository.getCompletedTasksByDuration(taskDurationCategory = taskDurationCategory)
    }

    suspend fun updateAchievements(taskDurationCategory: TaskDurationCategory) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                tasksRepository.updateAchievementsProgression(taskDurationCategory)
            }
        }
    }

    suspend fun getNewTasksWithoutProgress(taskDurationCategory: TaskDurationCategory, numberOfTasks: Int) {
        tasksRepository.resetTasksFromCategory(durationCategory = taskDurationCategory)
        tasksRepository.selectNRandomTasksByDuration(taskDurationCategory, numberOfTasks)
    }

    suspend fun getNewTasks(taskDurationCategory: TaskDurationCategory) {
        viewModelScope.launch {
            val time = System.currentTimeMillis()
            withContext(Dispatchers.IO) {
                if (taskDurationCategory == TaskDurationCategory.Daily) {
                    userDataRepository.updateDailyTasksLastRefreshTime(time)
                } else if (taskDurationCategory == TaskDurationCategory.Weekly) {
                    userDataRepository.updateWeeklyTasksLastRefreshTime(time)
                } else if (taskDurationCategory == TaskDurationCategory.Monthly) {
                    userDataRepository.updateMonthlyTasksLastRefreshTime(time)
                }
                tasksRepository.getNewTasks(taskDurationCategory = taskDurationCategory)
            }
        }
    }

    suspend fun insertTaskToDatabase() {
        if (taskUiState.isValid()) {
            tasksRepository.insertTask(taskUiState.toTask())
        }
    }

    suspend fun deleteTask(task: Task) {
        delay(600)
        tasksRepository.deleteTask(task)
    }

    suspend fun deleteAllTasksInDb() {
        val allTasks = tasksRepository.getAllTasksStream()
        allTasks.collect { tasks ->
            for (task in tasks) {
                tasksRepository.deleteTask(task)
            }
        }
    }

    suspend fun refreshTasks(calendarDaily: Calendar, calendarWeekly: Calendar, calendarMonthly: Calendar) {
        val day = calendarDaily.get(Calendar.DAY_OF_YEAR)
        val yearDaily = calendarDaily.get(Calendar.YEAR)

        // times of previously last refreshed weekly task
        val week = calendarWeekly.get(Calendar.WEEK_OF_YEAR)
        val yearWeekly = calendarWeekly.get(Calendar.YEAR)

        // times of previously last refreshed monthly task
        val month = calendarMonthly.get(Calendar.MONTH)
        val yearMonthly = calendarMonthly.get(Calendar.YEAR)

        val currentTime = Calendar.getInstance().apply {
            firstDayOfWeek = Calendar.MONDAY
        }

        currentTime.timeInMillis = System.currentTimeMillis()
        val isFromLastDay = (currentTime.get(Calendar.YEAR) >= yearDaily) && (currentTime.get(Calendar.DAY_OF_YEAR) - 1 >= day)
        val isFromLastWeek = (currentTime.get(Calendar.YEAR) >= yearWeekly) && (currentTime.get(Calendar.WEEK_OF_YEAR) - 1 >= week)
        val isFromLastMonth =  (currentTime.get(Calendar.YEAR) >= yearMonthly) && (currentTime.get(Calendar.MONTH) - 1 >= month)

        if (isFromLastDay)
            getNewTasks(TaskDurationCategory.Daily)
        if (isFromLastWeek)
            getNewTasks(TaskDurationCategory.Weekly)
        if (isFromLastMonth)
            getNewTasks(TaskDurationCategory.Monthly)
    }
}