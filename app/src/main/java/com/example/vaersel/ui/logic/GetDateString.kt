package com.example.vaersel.ui.logic

import java.time.LocalDate
import java.time.format.DateTimeFormatter

fun getDateString(string: String): String {

    val date = LocalDate.parse(string, DateTimeFormatter.ofPattern("yyyy-MM-dd"))

    val weekday = when (date.dayOfWeek.value) {
        1 -> "Mandag"
        2 -> "Tirsdag"
        3 -> "Onsdag"
        4 -> "Torsdag"
        5 -> "Fredag"
        6 -> "Lørdag"
        else -> "Søndag"
    }
    val dayOfMonth = date.dayOfMonth
    val month = when(date.month.value) {
        1 -> "januar"
        2 -> "februar"
        3 -> "mars"
        4 -> "april"
        5 -> "mai"
        6 -> "juni"
        7 -> "juli"
        8 -> "august"
        9 -> "september"
        10 -> "oktober"
        11 -> "november"
        else -> "desember"
    }
    return "$weekday $dayOfMonth. $month"
}