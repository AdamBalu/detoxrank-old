package com.example.detoxrank.data.task

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.detoxrank.data.Converters

enum class TaskDurationCategory {
    Daily, Weekly, Monthly, Uncategorized
}

enum class TaskIconCategory {
    Exercise,
    Health,
    Running,
    Walking,
    Swimming,
    Meditation,
    HealthyFood,
    UnhealthyFood,
    Cleaning,
    Cycling,
    Reading,
    Hiking,
    Fun,
    Places,
    Other
}

@Entity(tableName = "task")
data class Task(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val description: String,
    var completed: Boolean = false,
    @ColumnInfo(name = "duration_category")
    @TypeConverters(Converters::class)
    val durationCategory: TaskDurationCategory,
    @TypeConverters(Converters::class)
    val iconCategory: TaskIconCategory,
    @ColumnInfo(name = "selected")
    var selectedAsCurrentTask: Boolean = false,
    val language: String = "EN"
)
