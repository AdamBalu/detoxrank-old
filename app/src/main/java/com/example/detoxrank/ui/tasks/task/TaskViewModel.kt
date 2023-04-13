package com.example.detoxrank.ui.tasks.task

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import com.example.detoxrank.data.task.Task
import com.example.detoxrank.data.task.TaskDurationCategory
import com.example.detoxrank.data.task.TasksRepository
import com.example.detoxrank.data.task.WMTasksRepository
import com.example.detoxrank.ui.utils.Constants.NUMBER_OF_TASKS_DAILY
import com.example.detoxrank.ui.utils.Constants.NUMBER_OF_TASKS_MONTHLY
import com.example.detoxrank.ui.utils.Constants.NUMBER_OF_TASKS_WEEKLY
import kotlinx.coroutines.delay

class TaskViewModel(
    application: Application,
    private val tasksRepository: TasksRepository,
    private val wmTasksRepository: WMTasksRepository
    ) : AndroidViewModel(application) {
    private val sharedPrefs = application.getSharedPreferences(
        application.packageName + "_preferences",
        Context.MODE_PRIVATE
    )

    suspend fun firstRunGetTasks() {
        val firstRun = sharedPrefs.getBoolean("first_run", true)
        Log.d("Tasks", "First run: $firstRun")
        if (firstRun) {
            getNewTasks(TaskDurationCategory.Daily, NUMBER_OF_TASKS_DAILY)
            getNewTasks(TaskDurationCategory.Weekly, NUMBER_OF_TASKS_WEEKLY)
            getNewTasks(TaskDurationCategory.Monthly, NUMBER_OF_TASKS_MONTHLY)
            wmTasksRepository.getNewTasks()
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

    fun checkNewMonthTasksTest() {
        wmTasksRepository.checkNewMonthTasksTest()
    }

    private suspend fun getNewTasks(taskDurationCategory: TaskDurationCategory, numberOfTasks: Int) {
        tasksRepository.resetTasksFromCategory(durationCategory = taskDurationCategory)
        tasksRepository.selectNRandomTasksByDuration(taskDurationCategory, numberOfTasks)
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