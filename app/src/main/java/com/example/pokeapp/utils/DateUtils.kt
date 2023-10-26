package com.example.pokeapp.utils

import java.time.LocalDate
import java.time.format.DateTimeFormatter

fun LocalDate.toFormattedString(): String {
    return this.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"))
}

fun String.toLocalDate(): LocalDate{
    val formatter =  DateTimeFormatter.ofPattern("dd-MM-yyyy")
    return LocalDate.parse(this, formatter)
}