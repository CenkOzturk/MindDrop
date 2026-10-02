package com.kukurodev.minddrop.feature.tracker

import kotlinx.datetime.LocalDate
import kotlinx.datetime.Month
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Clock

fun today(): LocalDate {
    return Clock.System.now()
        .toLocalDateTime(TimeZone.currentSystemDefault())
        .date
}

fun daysInMonth(
    year: Int,
    month: Month
): Int {
    return when (month) {
        Month.FEBRUARY -> {
            if (year % 4 == 0 &&
                (year % 100 != 0 || year % 400 == 0)
            ) {
                29
            } else {
                28
            }
        }

        Month.APRIL,
        Month.JUNE,
        Month.SEPTEMBER,
        Month.NOVEMBER -> 30

        else -> 31
    }
}

fun firstDayOffset(
    year: Int,
    month: Month
): Int {
    val firstDay = kotlinx.datetime.LocalDate(
        year = year,
        month = month,
        day = 1
    )

    return firstDay.dayOfWeek.ordinal
}