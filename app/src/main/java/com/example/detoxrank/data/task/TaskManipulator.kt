package com.example.detoxrank.data.task

class TaskManipulator(private val tasksRepository: TasksRepository) {
    suspend fun getNewTasks(taskDurationCategory: TaskDurationCategory, numberOfTasks: Int) {
        tasksRepository.resetTasksFromCategory(durationCategory = taskDurationCategory)
        tasksRepository.selectNRandomTasksByDuration(taskDurationCategory, numberOfTasks)
    }
}