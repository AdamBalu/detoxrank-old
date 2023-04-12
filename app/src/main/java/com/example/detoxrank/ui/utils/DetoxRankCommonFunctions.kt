package com.example.detoxrank.ui.utils

import androidx.annotation.DrawableRes
import com.example.detoxrank.R


fun formatTime(seconds: String, minutes: String, hours: String): String {
    return "$hours:$minutes:$seconds"
}

fun formatTimeWithLetters(days: String, hours: String, minutes: String): String {
    return "${days}d ${hours}h ${minutes}m"
}

fun Int.pad(): String {
    return this.toString().padStart(2, '0')
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

@DrawableRes
fun getAchievementDrawableFromId(id: Int): Int {
    return when (id) {
        else -> R.drawable.gold_1
    }
}
