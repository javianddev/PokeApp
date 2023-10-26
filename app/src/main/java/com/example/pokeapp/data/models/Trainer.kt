package com.example.pokeapp.data.models

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate

@Entity(tableName = "TRAINER")
data class Trainer(

    @PrimaryKey
    val id: Int,

    @NonNull
    @ColumnInfo(name = "trainer_name")
    val name: String,

    @NonNull
    @ColumnInfo(name = "birthdate")
    val birthdate: LocalDate,

    @NonNull
    @ColumnInfo(name = "birthplace")
    val birthplace: String,
)
