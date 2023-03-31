package com.example.detoxrank.ui.tasks.task

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.detoxrank.data.task.Task
import com.example.detoxrank.data.task.TaskDurationCategory
import com.example.detoxrank.data.task.TasksRepository
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class TaskViewModel(
    savedStateHandle: SavedStateHandle,
    private val tasksRepository: TasksRepository
) : ViewModel() {
    var taskUiState by mutableStateOf(TaskUiState())
        private set

    private val taskId: Int = 0 // checkNotNull(savedStateHandle[ItemEditDestination.itemIdArg])

    init {
        viewModelScope.launch {
            taskUiState = tasksRepository.getTaskStream(taskId)
                .filterNotNull()
                .first()
                .toTaskUiState()
        }
    }

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

    suspend fun getNewTasks(taskDurationCategory: TaskDurationCategory, numberOfTasks: Int) {
        tasksRepository.resetTasksFromCategory(durationCategory = taskDurationCategory)
        tasksRepository.selectNRandomTasksByDuration(taskDurationCategory, numberOfTasks)
    }

    suspend fun insertTaskToDatabase() {
        if (taskUiState.isValid()) {
            tasksRepository.insertTask(taskUiState.toTask())
        }
    }

    suspend fun deleteTask(task: Task) {
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

//
//
//
//    fun removeCompletedTasks() {
//        _taskList.removeAll(_taskList.filter { it.completed.value })
//    }
//
//    fun resetTaskCompletionValues(tasks: List<Task>) {
//        tasks.forEach {
//            it.completed = mutableStateOf(false)
//        }
//    }
}