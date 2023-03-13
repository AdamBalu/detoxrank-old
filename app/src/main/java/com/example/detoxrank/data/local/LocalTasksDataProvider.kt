package com.example.detoxrank.data.local

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import com.example.detoxrank.R
import com.example.detoxrank.data.Task
import com.example.detoxrank.data.TaskCategory

object LocalTasksDataProvider {
    // TODO add basic exercise task
    val tasks = listOf(
        Task(
            description = R.string.task_daily_apple,
            category = TaskCategory.Daily,
            icon = Icons.Filled.BakeryDining
        ),
        Task(
            description = R.string.task_daily_chore,
            category = TaskCategory.Daily,
            icon = Icons.Filled.Home
        ),
        Task(
            description = R.string.task_daily_clean,
            category = TaskCategory.Daily,
            icon = Icons.Filled.Home
        ),
        Task(
            description = R.string.task_daily_dusk,
            category = TaskCategory.Daily,
            icon = Icons.Filled.WbSunny
        ),
        Task(
            description = R.string.task_daily_read,
            category = TaskCategory.Daily,
            icon = Icons.Filled.Book
        ),
        Task(
            description = R.string.task_daily_meditate,
            category = TaskCategory.Daily,
            icon = Icons.Filled.SelfImprovement
        ),
        Task(
            description = R.string.task_daily_run,
            category = TaskCategory.Daily,
            icon = Icons.Filled.DirectionsRun
        ),
        Task(
            description = R.string.task_daily_sugar,
            category = TaskCategory.Daily,
            icon = Icons.Filled.Icecream
        ),
        Task(
            description = R.string.task_daily_walk,
            category = TaskCategory.Daily,
            icon = Icons.Filled.DirectionsWalk
        ),
        Task(
            description = R.string.task_daily_waste_food,
            category = TaskCategory.Daily,
            icon = Icons.Filled.Fastfood
        ),
        Task(
            description = R.string.task_daily_prepare_dinner,
            category = TaskCategory.Daily,
            icon = Icons.Filled.SoupKitchen
        ),
        Task(
            description = R.string.task_daily_clean_table,
            category = TaskCategory.Daily,
            icon = Icons.Filled.CleaningServices
        ),

        Task(
            description = R.string.task_weekly_eat_food,
            category = TaskCategory.Weekly,
            icon = Icons.Filled.BakeryDining
        ),
        Task(
            description = R.string.task_weekly_food,
            category = TaskCategory.Weekly
        ),
        Task(
            description = R.string.task_weekly_hike,
            category = TaskCategory.Weekly
        ),
        Task(
            description = R.string.task_weekly_lunch,
            category = TaskCategory.Weekly
        ),
        Task(
            description = R.string.task_weekly_meditate,
            category = TaskCategory.Weekly
        ),
        Task(
            description = R.string.task_weekly_hike_forest,
            category = TaskCategory.Weekly
        ),
        Task(
            description = R.string.task_weekly_read,
            category = TaskCategory.Weekly
        ),
        Task(
            description = R.string.task_weekly_ride,
            category = TaskCategory.Weekly
        ),
        Task(
            description = R.string.task_weekly_sport,
            category = TaskCategory.Weekly
        ),
        Task(
            description = R.string.task_weekly_swim,
            category = TaskCategory.Weekly
        ),
        Task(
            description = R.string.task_weekly_walk_park,
            category = TaskCategory.Weekly
        ),
        Task(
            description = R.string.task_weekly_meditate_park,
            category = TaskCategory.Weekly
        ),

        Task(
            description = R.string.task_monthly_cinema_night,
            category = TaskCategory.Monthly
        ),
        Task(
            description = R.string.task_monthly_hike,
            category = TaskCategory.Monthly
        ),
        Task(
            description = R.string.task_monthly_eat_food,
            category = TaskCategory.Monthly
        ),
        Task(
            description = R.string.task_monthly_read,
            category = TaskCategory.Monthly
        ),
        Task(
            description = R.string.task_monthly_swim,
            category = TaskCategory.Monthly
        ),
        Task(
            description = R.string.task_monthly_meditate,
            category = TaskCategory.Monthly
        ),
        Task(
            description = R.string.task_monthly_museum,
            category = TaskCategory.Monthly
        ),
        Task(
            description = R.string.task_monthly_desire,
            category = TaskCategory.Monthly
        ),
        Task(
            description = R.string.task_monthly_trip,
            category = TaskCategory.Monthly
        )
    )
}
