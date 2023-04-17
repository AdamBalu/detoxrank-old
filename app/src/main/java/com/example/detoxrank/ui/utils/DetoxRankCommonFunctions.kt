package com.example.detoxrank.ui.utils

import android.text.format.Time
import androidx.annotation.DrawableRes
import androidx.compose.animation.ExperimentalAnimationApi
import com.example.detoxrank.R
import com.example.detoxrank.service.TimerService
import com.example.detoxrank.ui.utils.Constants.ID_FINISH_100_TASKS
import com.example.detoxrank.ui.utils.Constants.ID_FINISH_10_TASKS
import com.example.detoxrank.ui.utils.Constants.ID_FINISH_250_TASKS
import com.example.detoxrank.ui.utils.Constants.ID_FINISH_25_TASKS
import com.example.detoxrank.ui.utils.Constants.ID_FINISH_50_TASKS
import com.example.detoxrank.ui.utils.Constants.ID_FINISH_5_TASKS
import com.example.detoxrank.ui.utils.Constants.ID_FINISH_ALL_CH
import com.example.detoxrank.ui.utils.Constants.ID_FINISH_CH_1
import com.example.detoxrank.ui.utils.Constants.ID_FINISH_CH_2
import com.example.detoxrank.ui.utils.Constants.ID_FINISH_CH_3
import com.example.detoxrank.ui.utils.Constants.ID_FINISH_CH_4
import com.example.detoxrank.ui.utils.Constants.ID_FINISH_CH_5
import com.example.detoxrank.ui.utils.Constants.ID_FINISH_CH_6
import com.example.detoxrank.ui.utils.Constants.ID_FINISH_FIRST_TASK
import com.example.detoxrank.ui.utils.Constants.ID_READ_100_PAGES
import com.example.detoxrank.ui.utils.Constants.ID_READ_10_BOOKS
import com.example.detoxrank.ui.utils.Constants.ID_READ_20_PAGES
import com.example.detoxrank.ui.utils.Constants.ID_READ_250_PAGES
import com.example.detoxrank.ui.utils.Constants.ID_READ_50_PAGES
import com.example.detoxrank.ui.utils.Constants.ID_READ_5_BOOKS
import com.example.detoxrank.ui.utils.Constants.ID_READ_A_BOOK
import com.example.detoxrank.ui.utils.Constants.ID_RUN_10_KM
import com.example.detoxrank.ui.utils.Constants.ID_RUN_3_KM
import com.example.detoxrank.ui.utils.Constants.ID_RUN_5_KM
import com.example.detoxrank.ui.utils.Constants.ID_RUN_7_KM
import com.example.detoxrank.ui.utils.Constants.ID_START_TIMER
import com.example.detoxrank.ui.utils.Constants.ID_TIMER_14_DAYS
import com.example.detoxrank.ui.utils.Constants.ID_TIMER_30_DAYS
import com.example.detoxrank.ui.utils.Constants.ID_TIMER_3_DAYS
import com.example.detoxrank.ui.utils.Constants.ID_TIMER_7_DAYS
import com.example.detoxrank.ui.utils.Constants.TIMER_HOURLY_RP_GAIN


fun formatTime(seconds: String, minutes: String, hours: String): String {
    return "$hours:$minutes:$seconds"
}

fun formatTimeWithLetters(days: String, hours: String, minutes: String): String {
    return "${days}d ${hours}h ${minutes}m"
}

fun Int.pad(): String {
    return this.toString().padStart(2, '0')
}

@ExperimentalAnimationApi
fun calculateTimerRPGain(timerService: TimerService): Int {
    return timerService.days.value * TIMER_HOURLY_RP_GAIN * 24 + timerService.hours.value.toInt() * TIMER_HOURLY_RP_GAIN
}

@DrawableRes
fun getLevelDrawableId(level: Int): Int {
    return when (level) {
        1 -> R.drawable.level_1
        2 -> R.drawable.level_2
        3 -> R.drawable.level_3
        4 -> R.drawable.level_4
        5 -> R.drawable.level_5
        6 -> R.drawable.level_6
        7 -> R.drawable.level_7
        8 -> R.drawable.level_8
        9 -> R.drawable.level_9
        10 -> R.drawable.level_10
        11 -> R.drawable.level_11
        12 -> R.drawable.level_12
        13 -> R.drawable.level_13
        14 -> R.drawable.level_14
        15 -> R.drawable.level_15
        16 -> R.drawable.level_16
        17 -> R.drawable.level_17
        18 -> R.drawable.level_18
        19 -> R.drawable.level_19
        20 -> R.drawable.level_20
        21 -> R.drawable.level_21
        22 -> R.drawable.level_22
        23 -> R.drawable.level_23
        24 -> R.drawable.level_24
        25 -> R.drawable.level_25
        else -> R.drawable.level_1
    }
}

// level 25 is awarded for 39 236 accumulated xp
fun getCurrentLevelFromXP(xpPoints: Int): Int {
    var value = 100.0
    var accumulated = value.toInt()
    var result = 1
    while (accumulated < xpPoints) {
        accumulated += (value + value * 0.2).toInt()
        value += value * 0.2
        result++
    }
    if (result > 25)
        return 25
    return result
}

fun getCurrentProgressBarProgression(xpPoints: Int): Float {
    var value = 100.0
    var accumulated = value.toInt()
    var lowerBound = accumulated
    var result = 1
    while (accumulated < xpPoints) {
        accumulated += (value + value * 0.2).toInt()
        value += value * 0.2
        result++
        if (accumulated < xpPoints) {
            lowerBound = accumulated
        }
    }
    if (result > 25) {
        return 1f
    }
    return (xpPoints.toFloat() - lowerBound) / (accumulated - lowerBound)
}

fun calculateTimerFloatAddition(progressLength: Float, numberOfUnitsInBiggerUnit: Int): Float =
    progressLength / (numberOfUnitsInBiggerUnit - 1)

@DrawableRes
fun getAchievementDrawableFromId(id: Int, isDarkTheme: Boolean): Int {
    return if (isDarkTheme) {
        when (id) {
            ID_RUN_3_KM -> R.drawable.run3km
            ID_RUN_5_KM -> R.drawable.run5km
            ID_RUN_7_KM -> R.drawable.run7km
            ID_RUN_10_KM -> R.drawable.run10km
            ID_READ_20_PAGES -> R.drawable.read20pages
            ID_READ_50_PAGES -> R.drawable.read50pages
            ID_READ_100_PAGES -> R.drawable.read100pages
            ID_READ_250_PAGES -> R.drawable.read250pages
            ID_READ_A_BOOK -> R.drawable.read1book
            ID_READ_5_BOOKS -> R.drawable.read5books
            ID_READ_10_BOOKS -> R.drawable.read10books
            ID_FINISH_CH_1 -> R.drawable.finishchapterintroduction
            ID_FINISH_CH_2 -> R.drawable.finishchapterdopamine
            ID_FINISH_CH_3 -> R.drawable.finishchapterreinforcement
            ID_FINISH_CH_4 -> R.drawable.finishchaptertolerance
            ID_FINISH_CH_5 -> R.drawable.finishchapterhedoniccircuit
            ID_FINISH_CH_6 -> R.drawable.finishchaptersolution
            ID_FINISH_ALL_CH -> R.drawable.finishallchapters
            ID_FINISH_FIRST_TASK -> R.drawable.finishfirsttask
            ID_FINISH_5_TASKS -> R.drawable.finish5tasks
            ID_FINISH_10_TASKS -> R.drawable.finish10tasks
            ID_FINISH_25_TASKS -> R.drawable.finish25tasks
            ID_FINISH_50_TASKS -> R.drawable.finish50tasks
            ID_FINISH_100_TASKS -> R.drawable.finish100tasks
            ID_FINISH_250_TASKS -> R.drawable.finish250tasks
            ID_START_TIMER -> R.drawable.startthetimer
            ID_TIMER_3_DAYS -> R.drawable.timer3days
            ID_TIMER_7_DAYS -> R.drawable.timer7days
            ID_TIMER_14_DAYS -> R.drawable.timer14days
            ID_TIMER_30_DAYS -> R.drawable.timer30days
            else -> R.drawable.gold_1
        }
    } else {
        when (id) {
            ID_RUN_3_KM -> R.drawable.run3kmlight
            ID_RUN_5_KM -> R.drawable.run5kmlight
            ID_RUN_7_KM -> R.drawable.run7kmlight
            ID_RUN_10_KM -> R.drawable.run10kmlight
            ID_READ_20_PAGES -> R.drawable.read20pageslight
            ID_READ_50_PAGES -> R.drawable.read50pageslight
            ID_READ_100_PAGES -> R.drawable.read100pageslight
            ID_READ_250_PAGES -> R.drawable.read250pageslight
            ID_READ_A_BOOK -> R.drawable.read1book
            ID_READ_5_BOOKS -> R.drawable.read5books
            ID_READ_10_BOOKS -> R.drawable.read10books
            ID_FINISH_CH_1 -> R.drawable.finishchapterintroduction
            ID_FINISH_CH_2 -> R.drawable.finishchapterdopaminelight
            ID_FINISH_CH_3 -> R.drawable.finishchapterreinforcement
            ID_FINISH_CH_4 -> R.drawable.finishchaptertolerancelight
            ID_FINISH_CH_5 -> R.drawable.finishchapterhedoniccircuit
            ID_FINISH_CH_6 -> R.drawable.finishchaptersolution
            ID_FINISH_ALL_CH -> R.drawable.finishallchapters
            ID_FINISH_FIRST_TASK -> R.drawable.finishfirsttask
            ID_FINISH_5_TASKS -> R.drawable.finish5tasks
            ID_FINISH_10_TASKS -> R.drawable.finish10tasks
            ID_FINISH_25_TASKS -> R.drawable.finish25tasks
            ID_FINISH_50_TASKS -> R.drawable.finish50tasks
            ID_FINISH_100_TASKS -> R.drawable.finish100tasks
            ID_FINISH_250_TASKS -> R.drawable.finish250tasks
            ID_START_TIMER -> R.drawable.startthetimer
            ID_TIMER_3_DAYS -> R.drawable.timer3days
            ID_TIMER_7_DAYS -> R.drawable.timer7days
            ID_TIMER_14_DAYS -> R.drawable.timer14days
            ID_TIMER_30_DAYS -> R.drawable.timer30days
            else -> R.drawable.gold_1
        }
    }
}
