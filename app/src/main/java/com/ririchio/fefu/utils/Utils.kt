package com.ririchio.fefu.utils

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

fun Date.toString(format: String, locale: Locale = Locale.getDefault()): String {
    val formatter = SimpleDateFormat(format, locale)
    return formatter.format(this)
}

fun getHourWord(hours: Int): String {
    return when {
        hours % 10 == 1 && hours % 100 != 11 -> "час"
        hours % 10 in 2..4 && hours % 100 !in 12..14 -> "часа"
        else -> "часов"
    }
}

fun isSameDay(date1: Date, date2: Date): Boolean {
    val fmt = SimpleDateFormat("yyyyMMdd", Locale.getDefault())
    return fmt.format(date1) == fmt.format(date2)
}

fun getMinuteWord(minutes: Int): String {
    return when {
        minutes % 10 == 1 && minutes % 100 != 11 -> "минута"
        minutes % 10 in 2..4 && minutes % 100 !in 12..14 -> "минуты"
        else -> "минут"
    }
}

fun getRandomTimes(): List<Long> {
    val now = System.currentTimeMillis()

    val calendar = Calendar.getInstance().apply {
        timeInMillis = now
        set(Calendar.HOUR_OF_DAY, 0)
        set(Calendar.MINUTE, 0)
        set(Calendar.SECOND, 0)
        set(Calendar.MILLISECOND, 0)
    }
    val dayStart = calendar.timeInMillis

    val dayEnd = calendar.apply {
        set(Calendar.HOUR_OF_DAY, 23)
        set(Calendar.MINUTE, 59)
        set(Calendar.SECOND, 59)
        set(Calendar.MILLISECOND, 999)
    }.timeInMillis

    val maxDuration = 10 * 60 * 60 * 1000L

    val latestStart = dayEnd - maxDuration
    val start = (dayStart..latestStart).random()

    val maxEnd = minOf(start + maxDuration, dayEnd)
    val end = (start + 1..maxEnd).random()
    return listOf(start, end).sorted()
}

fun getRandomDistance(): String {
    val meters = (1_000..30_000).random()
    val kilometers = meters / 1000.0
    return String.format("%.2f км", kilometers)
}