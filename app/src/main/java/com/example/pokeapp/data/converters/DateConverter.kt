package com.example.pokeapp.data.converters

import androidx.room.TypeConverter
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class DateConverter {
    @TypeConverter
    fun fromTimestamp(value: String?): LocalDate? {
        return LocalDate.parse(value, DateTimeFormatter.ofPattern("dd/MM/yyyy"))
    }

    @TypeConverter
    fun dateToTimestamp(date: LocalDate?): String? {
        return DateTimeFormatter.ofPattern("dd/MM/yyyy").format(date)
    }
}