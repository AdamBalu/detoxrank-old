package com.example.detoxrank.data.user

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.detoxrank.data.Converters
import com.example.detoxrank.data.TimerDifficulty

enum class UiTheme {
    Default, Light, Dark, Monochrome, GreenShades, BlueShades
}

enum class Rank {
    Bronze1, Bronze2, Bronze3,
    Silver1, Silver2, Silver3,
    Gold1, Gold2, Gold3,
    Platinum1, Platinum2, Platinum3,
    Diamond1, Diamond2, Diamond3,
    Master1, Master2, Master3,
    Legend
}

@Entity(tableName = "user_data")
data class UserData(
    @PrimaryKey(autoGenerate = false)
    val id: Int = 1,
    val timerStartedTimeInMillis: Int,
    val tasksStartedTimeInMillis: Int,
    val brainPoints: Int,
    val rankPoints: Int,
    val xpPoints: Int,
    @TypeConverters(Converters::class)
    val chosenTimerDifficulty: TimerDifficulty,
    @TypeConverters(Converters::class)
    val selectedTheme: UiTheme
)
