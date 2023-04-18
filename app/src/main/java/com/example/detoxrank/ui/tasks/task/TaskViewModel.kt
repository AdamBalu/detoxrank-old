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
        Log.d("Tasks", "First run: $firstRun")
        if (firstRun) {
            getNewTasksWithoutProgress(TaskDurationCategory.Daily, NUMBER_OF_TASKS_DAILY)
            getNewTasksWithoutProgress(TaskDurationCategory.Weekly, NUMBER_OF_TASKS_WEEKLY)
            getNewTasksWithoutProgress(TaskDurationCategory.Monthly, NUMBER_OF_TASKS_MONTHLY)
            withContext(Dispatchers.IO) {
                userDataRepository.updateMonthlyTasksLastRefreshTime(System.currentTimeMillis())
            }
            wmTasksRepository.getNewTasks()
            sharedPrefs.edit().putBoolean("first_run", false).apply()
        }
    }

    suspend fun getMonthlyTasks() {
        withContext(Dispatchers.IO) {
            getNewTasksWithoutProgress(TaskDurationCategory.Monthly, NUMBER_OF_TASKS_MONTHLY)
            userDataRepository.updateMonthlyTasksLastRefreshTime(System.currentTimeMillis())
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
            withContext(Dispatchers.IO) {
                userDataRepository.updateMonthlyTasksLastRefreshTime(System.currentTimeMillis())
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
}