package com.example.detoxrank.ui

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import com.example.detoxrank.data.*
import com.example.detoxrank.data.local.LocalTimerDifficultyDataProvider.listOfDifficulties
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class DetoxRankViewModel : ViewModel() {

    /** UI state exposed to the UI **/
    private val _uiState = MutableStateFlow(DetoxRankUiState())
    val uiState: StateFlow<DetoxRankUiState> = _uiState.asStateFlow()

    private val _taskList = mutableStateListOf<Task>()
    val taskList: List<Task>
        get() = _taskList

    private val _difficultySelectShown = mutableStateOf(false)
    val difficultySelectShown: Boolean
        get() = _difficultySelectShown.value

    private val _difficultyList = listOfDifficulties
    val difficultyList: List<TimerDifficultyCard>
        get() = _difficultyList

    private var _selectedTimerDifficulty = TimerDifficulty.Easy
    val selectedTimerDifficulty: TimerDifficulty
        get() = _selectedTimerDifficulty

    init {
        initializeUIState()
    }

    fun setSelectedTimerDifficulty(value: TimerDifficulty) {
        _selectedTimerDifficulty = value
    }

    private fun initializeUIState() {

    }

    /**
     * Update [currentSection]
     */
    fun updateCurrentSection(section: Section) {
        _uiState.update {
            it.copy(
                currentSection = section
            )
        }
    }

    fun getProgressBarValue(): Float {
        return uiState.value.progressBarProgression
    }

    fun updateProgressBarProgression(valueToAdd: Float) {
        val progression = getProgressBarValue() + valueToAdd
        _uiState.update {
            it.copy(
                progressBarProgression = progression
            )
        }
    }

    fun resetProgressBarProgression() {
        _uiState.update {
            it.copy(
                progressBarProgression = 0f
            )
        }
    }

    fun addTask(task: Task) {
        _taskList.add(task)
    }

    private fun removeTask(task: Task) {
        _taskList.remove(task)
    }

//    fun changeTaskCompletedState(task: Task) {
//        val foundTask = _taskList.find { it === task }
//        if (foundTask != null)
//            foundTask.completed = mutableStateOf(!foundTask.completed)
//    }

    fun markTaskAsCompleted(task: Task, value: Boolean) {
        if (task.category != TaskCategory.Daily) return

        val taskIndex = _taskList.indexOf(task)

        _taskList[taskIndex] = _taskList[taskIndex].let {
            it.copy(
                description = it.description,
                category = it.category,
                completed = mutableStateOf(value)
            )
        }
    }

    fun cleanTaskList(tasks: List<Task>) {
        _taskList.removeAll(tasks)
    }

    fun calculateProgressBarAddition(chapter: Chapter): Float =
        if (chapter.chapterScreenNum != 0)
            (1 / (chapter.chapterScreenNum - 1).toFloat())
        else 0f

    fun fillTaskList(tasks: List<Task>) {
        addTaskToTaskList(tasks, _taskList, 3, TaskCategory.Monthly)
        addTaskToTaskList(tasks, _taskList, 4, TaskCategory.Daily)
        addTaskToTaskList(tasks, _taskList, 5, TaskCategory.Weekly)
    }

    private fun addTaskToTaskList(
        taskListData: List<Task>,
        listToFill: SnapshotStateList<Task>,
        taskAmount: Int,
        taskCategory: TaskCategory
    ) {
        while (
            listToFill
                .filter { it.category == taskCategory }
                .size < taskAmount
        ) {
            val toAdd = taskListData
                .filter { it.category == taskCategory }
                .random()

            // don't add tasks with the same description
            // (considering it as the same task if description is the same)
            if (listToFill.any { it.description == toAdd.description })
                continue

            listToFill.add(toAdd)
        }
    }

    fun removeCompletedTasks() {
        _taskList.removeAll(_taskList.filter { it.completed.value })
    }

    fun resetTaskCompletionValues(tasks: List<Task>) {
        tasks.forEach {
            it.completed = mutableStateOf(false)
        }
    }

    fun setDifficultySelectShown(value: Boolean) {
        _difficultySelectShown.value = value
    }
}
