package com.example.detoxrank.ui.utils


fun formatTime(seconds: String, minutes: String, hours: String): String {
    return "$hours:$minutes:$seconds"
}

fun formatTimeWithLetters(days: String, hours: String, minutes: String): String {
    return "${days}d ${hours}h ${minutes}m"
}

fun Int.pad(): String {
    return this.toString().padStart(2, '0')
}
