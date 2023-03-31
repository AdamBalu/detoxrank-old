package com.example.detoxrank.data.local

import com.example.detoxrank.data.task.Task
import com.example.detoxrank.data.task.TaskDurationCategory
import com.example.detoxrank.data.task.TaskIconCategory

object LocalTasksDataProvider {
    val tasks = listOf(
        Task(
            description = "Eat an apple",
            durationCategory = TaskDurationCategory.Daily,
            iconCategory = TaskIconCategory.HealthyFood
        ),
        Task(
            description = "Exercise for at least 10 minutes",
            durationCategory = TaskDurationCategory.Daily,
            iconCategory = TaskIconCategory.Exercise
        ),
        Task(
            description = "Do a household chore",
            durationCategory = TaskDurationCategory.Daily,
            iconCategory = TaskIconCategory.Cleaning
        ),
        Task(
            description = "Clean your room",
            durationCategory = TaskDurationCategory.Daily,
            iconCategory = TaskIconCategory.Cleaning
        ),
        Task(
            description = "Observe dusk",
            durationCategory = TaskDurationCategory.Daily,
            iconCategory = TaskIconCategory.Health
        ),
        Task(
            description = "Read at least 20 pages",
            durationCategory = TaskDurationCategory.Daily,
            iconCategory = TaskIconCategory.Reading
        ),
        Task(
            description = "Meditate for 10 minutes",
            durationCategory = TaskDurationCategory.Daily,
            iconCategory = TaskIconCategory.Meditation
        ),
        Task(
            description = "Go for a run",
            durationCategory = TaskDurationCategory.Daily,
            iconCategory = TaskIconCategory.Running
        ),
        Task(
            description = "Don't eat sugar",
            durationCategory = TaskDurationCategory.Daily,
            iconCategory = TaskIconCategory.UnhealthyFood
        ),
        Task(
            description = "Go for a short walk",
            durationCategory = TaskDurationCategory.Daily,
            iconCategory = TaskIconCategory.Walking
        ),
        Task(
            description = "Don't eat waste food",
            durationCategory = TaskDurationCategory.Daily,
            iconCategory = TaskIconCategory.UnhealthyFood
        ),
        Task(
            description = "Prepare healthy dinner",
            durationCategory = TaskDurationCategory.Daily,
            iconCategory = TaskIconCategory.HealthyFood
        ),
        Task(
            description = "Clean your table",
            durationCategory = TaskDurationCategory.Daily,
            iconCategory = TaskIconCategory.Cleaning
        ),

        Task(
            description = "Eat at least 3 pieces of fruit",
            durationCategory = TaskDurationCategory.Weekly,
            iconCategory = TaskIconCategory.HealthyFood
        ),
        Task(
            description = "Learn how to prepare a new food",
            durationCategory = TaskDurationCategory.Weekly,
            iconCategory = TaskIconCategory.HealthyFood
        ),
        Task(
            description = "Go for a hike",
            durationCategory = TaskDurationCategory.Weekly,
            iconCategory = TaskIconCategory.Hiking
        ),
        Task(
            description = "Prepare lunch at least 2 times",
            durationCategory = TaskDurationCategory.Weekly,
            iconCategory = TaskIconCategory.HealthyFood
        ),
        Task(
            description = "Exercise at least 3 times for 10 minutes",
            durationCategory = TaskDurationCategory.Weekly,
            iconCategory = TaskIconCategory.Exercise
        ),
        Task(
            description = "Meditate 3 times",
            durationCategory = TaskDurationCategory.Weekly,
            iconCategory = TaskIconCategory.Meditation
        ),
        Task(
            description = "Go for a hike in the forest",
            durationCategory = TaskDurationCategory.Weekly,
            iconCategory = TaskIconCategory.Hiking
        ),
        Task(
            description = "Read 60 pages",
            durationCategory = TaskDurationCategory.Weekly,
            iconCategory = TaskIconCategory.Reading
        ),
        Task(
            description = "Go for a bike ride (or a long walk if you don't have one)",
            durationCategory = TaskDurationCategory.Weekly,
            iconCategory = TaskIconCategory.Cycling
        ),
        Task(
            description = "Do a sports activity at least 2 times",
            durationCategory = TaskDurationCategory.Weekly,
            iconCategory = TaskIconCategory.Cycling
        ),
        Task(
            description = "Go for a swim",
            durationCategory = TaskDurationCategory.Weekly,
            iconCategory = TaskIconCategory.Swimming
        ),
        Task(
            description = "Take a walk in the park",
            durationCategory = TaskDurationCategory.Weekly,
            iconCategory = TaskIconCategory.Walking
        ),
        Task(
            description = "Meditate in the park",
            durationCategory = TaskDurationCategory.Weekly,
            iconCategory = TaskIconCategory.Meditation
        ),

        Task(
            description = "Have a cinema night with friends",
            durationCategory = TaskDurationCategory.Monthly,
            iconCategory = TaskIconCategory.Fun
        ),
        Task(
            description = "Go for a long hike (at least 10km)",
            durationCategory = TaskDurationCategory.Monthly,
            iconCategory = TaskIconCategory.Hiking
        ),
        Task(
            description = "Eat at least 3 pieces of fruit each week",
            durationCategory = TaskDurationCategory.Monthly,
            iconCategory = TaskIconCategory.HealthyFood
        ),
        Task(
            description = "Read a book",
            durationCategory = TaskDurationCategory.Monthly,
            iconCategory = TaskIconCategory.Reading
        ),
        Task(
            description = "Go for a swim",
            durationCategory = TaskDurationCategory.Monthly,
            iconCategory = TaskIconCategory.Swimming
        ),
        Task(
            description = "Meditate each week at least once",
            durationCategory = TaskDurationCategory.Monthly,
            iconCategory = TaskIconCategory.Meditation
        ),
        Task(
            description = "Visit a museum",
            durationCategory = TaskDurationCategory.Monthly,
            iconCategory = TaskIconCategory.Places
        ),
        Task(
            description = "Visit some place you always wanted to go",
            durationCategory = TaskDurationCategory.Monthly,
            iconCategory = TaskIconCategory.Places
        ),
        Task(
            description = "Go on a trip",
            durationCategory = TaskDurationCategory.Monthly,
            iconCategory = TaskIconCategory.Hiking
        ),
        Task(
            description = "Exercise each week at least once",
            durationCategory = TaskDurationCategory.Monthly,
            iconCategory = TaskIconCategory.Exercise
        )
    )
}
