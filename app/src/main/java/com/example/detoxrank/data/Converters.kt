package com.example.detoxrank.data

import androidx.room.TypeConverter
import com.example.detoxrank.data.chapter.ChapterDifficulty
import com.example.detoxrank.data.chapter.ChapterTag
import com.example.detoxrank.data.task.TaskDurationCategory
import com.example.detoxrank.data.task.TaskIconCategory
import com.example.detoxrank.data.user.Rank
import com.example.detoxrank.data.user.UiTheme

class Converters {
    @TypeConverter
    fun fromTaskDurationCategory(durationCategory: TaskDurationCategory): String = durationCategory.name
    @TypeConverter
    fun toTaskCategory(durationCategory: String): TaskDurationCategory = TaskDurationCategory.valueOf(durationCategory)
    @TypeConverter
    fun fromTaskIconCategory(iconCategory: TaskIconCategory): String = iconCategory.name
    @TypeConverter
    fun toTaskIconCategory(iconCategory: String): TaskIconCategory = TaskIconCategory.valueOf(iconCategory)

    @TypeConverter
    fun fromChapterDifficulty(difficulty: ChapterDifficulty): String = difficulty.name
    @TypeConverter
    fun toChapterDifficulty(difficulty: String): ChapterDifficulty = ChapterDifficulty.valueOf(difficulty)
    @TypeConverter
    fun fromChapterTag(tag: ChapterTag): String = tag.name
    @TypeConverter
    fun toChapterTag(tag: String): ChapterTag = ChapterTag.valueOf(tag)

    @TypeConverter
    fun fromTimerDifficulty(difficulty: TimerDifficulty): String = difficulty.name
    @TypeConverter
    fun toTimerDifficulty(difficulty: String): TimerDifficulty = TimerDifficulty.valueOf(difficulty)
    @TypeConverter
    fun fromSelectedTheme(theme: UiTheme): String = theme.name
    @TypeConverter
    fun toSelectedTheme(theme: String): UiTheme = UiTheme.valueOf(theme)
    @TypeConverter
    fun fromRank(rank: Rank): String = rank.name
    @TypeConverter
    fun toRank(rank: String): Rank = Rank.valueOf(rank)
}