package com.example.pokeapp.utils

import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Date
import java.util.Locale

fun Date.toFormattedString(): String {
    val simpleDateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    return simpleDateFormat.format(this)
}

fun String.toDate(): Date {
    val formatter = SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH)
    return formatter.parse(this)
}


fun LocalDateTime.toFormattedString(): String {
    return this.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
}

fun String.toLocalDateTime(): LocalDateTime{
    val formatter =  DateTimeFormatter.ofPattern("dd/MM/yyyy")
    return LocalDateTime.parse(this, formatter)
}