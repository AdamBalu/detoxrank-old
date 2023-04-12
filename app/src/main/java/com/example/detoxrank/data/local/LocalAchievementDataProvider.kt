package com.example.detoxrank.data.local

import com.example.detoxrank.*
import com.example.detoxrank.data.achievements.Achievement
import com.example.detoxrank.data.achievements.AchievementDifficulty

object LocalAchievementDataProvider {
    val allAchievements = listOf(
        Achievement(
            id = ID_RUN_3_KM,
            name = "Weekend runner",
            description = "Run 3km",
            difficulty = AchievementDifficulty.EASY
        ),
        Achievement(
            id = ID_RUN_5_KM,
            name = "Fancy running?",
            description = "Run 5km",
            difficulty = AchievementDifficulty.MEDIUM
        ),
        Achievement(
            id = ID_RUN_7_KM,
            name = "Run lover",
            description = "Run 7km",
            difficulty = AchievementDifficulty.VERY_HARD
        ),
        Achievement(
            id = ID_RUN_10_KM,
            name = "Eat, sleep, run, repeat",
            description = "Run 10km",
            difficulty = AchievementDifficulty.INSANE
        ),
        Achievement(
            id = ID_READ_20_PAGES,
            name = "Huh, what is this?",
            description = "Read 20 pages",
            difficulty = AchievementDifficulty.EASY
        ),
        Achievement(
            id = ID_READ_50_PAGES,
            name = "Getting used to it",
            description = "Read 50 pages",
            difficulty = AchievementDifficulty.EASY
        ),
        Achievement(
            id = ID_READ_100_PAGES,
            name = "It's not that bad...",
            description = "Read 100 pages",
            difficulty = AchievementDifficulty.MEDIUM
        ),
        Achievement(
            id = ID_READ_250_PAGES,
            name = "More, more, more!",
            description = "Read 500 pages",
            difficulty = AchievementDifficulty.HARD
        ),
        Achievement(
            id = ID_READ_A_BOOK,
            name = "My first book",
            description = "Read a book",
            difficulty = AchievementDifficulty.MEDIUM
        ),
        Achievement(
            id = ID_READ_5_BOOKS,
            name = "Feeling educated",
            description = "Read 5 books",
            difficulty = AchievementDifficulty.VERY_HARD
        ),
        Achievement(
            id = ID_READ_10_BOOKS,
            name = "Nerd",
            description = "Read 10 books",
            difficulty = AchievementDifficulty.INSANE
        ),
        Achievement(
            id = ID_FINISH_CH_1,
            name = "Beginnings",
            description = "Finish Chapter 1 - Introduction",
            difficulty = AchievementDifficulty.EASY
        ),
        Achievement(
            id = ID_FINISH_CH_2,
            name = "Dopamine",
            description = "Finish Chapter 2 - Dopamine",
            difficulty = AchievementDifficulty.EASY
        ),
        Achievement(
            id = ID_FINISH_CH_3,
            name = "It's strong now!",
            description = "Finish Chapter 3 - Reinforcement",
            difficulty = AchievementDifficulty.EASY
        ),
        Achievement(
            id = ID_FINISH_CH_4,
            name = "I'm not tolerating that",
            description = "Finish Chapter 4 - Tolerance",
            difficulty = AchievementDifficulty.EASY
        ),
        Achievement(
            id = ID_FINISH_CH_5,
            name = "Love it!",
            description = "Finish Chapter 5 - Hedonic circuit",
            difficulty = AchievementDifficulty.EASY
        ),
        Achievement(
            id = ID_FINISH_CH_6,
            name = "Get rid of 'em",
            description = "Finish Chapter 6 - Solution",
            difficulty = AchievementDifficulty.EASY
        ),
        Achievement(
            id = ID_FINISH_ALL_CH,
            name = "Knowledge is power",
            description = "Finish All Chapters",
            difficulty = AchievementDifficulty.HARD
        ),
        Achievement(
            id = ID_FINISH_FIRST_TASK,
            name = "Task this, task that...",
            description = "Finish your first task",
            difficulty = AchievementDifficulty.EASY
        ),
        Achievement(
            id = ID_FINISH_5_TASKS,
            name = "Tasking",
            description = "Finish 5 tasks",
            difficulty = AchievementDifficulty.EASY
        ),
        Achievement(
            id = ID_FINISH_10_TASKS,
            name = "You tasked me to do this",
            description = "Finish 10 tasks",
            difficulty = AchievementDifficulty.MEDIUM
        ),
        Achievement(
            id = ID_FINISH_25_TASKS,
            name = "Can't stop doing them",
            description = "Finish 25 tasks",
            difficulty = AchievementDifficulty.HARD
        ),
        Achievement(
            id = ID_FINISH_50_TASKS,
            name = "Nobody stops me",
            description = "Finish 50 tasks",
            difficulty = AchievementDifficulty.VERY_HARD
        ),
        Achievement(
            id = ID_FINISH_100_TASKS,
            name = "Task master",
            description = "Finish 100 tasks",
            difficulty = AchievementDifficulty.INSANE
        ),
        Achievement(
            id = ID_FINISH_250_TASKS,
            name = "Task legend",
            description = "Finish 250 tasks",
            difficulty = AchievementDifficulty.LEGENDARY
        ),
        Achievement(
            id = ID_START_TIMER,
            name = "Time's tickin'",
            description = "Start the timer",
            difficulty = AchievementDifficulty.EASY
        ),
        Achievement(
            id = ID_TIMER_3_DAYS,
            name = "Bored already?",
            description = "Have the timer on for 3 days",
            difficulty = AchievementDifficulty.MEDIUM
        ),
        Achievement(
            id = ID_TIMER_7_DAYS,
            name = "Staying strong",
            description = "Have the timer on for 7 days",
            difficulty = AchievementDifficulty.VERY_HARD
        ),
        Achievement(
            id = ID_TIMER_14_DAYS,
            name = "Guru",
            description = "Have the timer on for 14 days",
            difficulty = AchievementDifficulty.INSANE
        ),
        Achievement(
            id = ID_TIMER_30_DAYS,
            name = "Unstoppable",
            description = "Have the timer on for 30 days",
            difficulty = AchievementDifficulty.LEGENDARY
        )
    )
}
