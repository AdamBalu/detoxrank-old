package com.example.detoxrank.data.task

interface WMTasksRepository {
    fun getNewTasks()
    fun checkNewMonthTasks()
    fun checkNewMonthTasksTest()
}