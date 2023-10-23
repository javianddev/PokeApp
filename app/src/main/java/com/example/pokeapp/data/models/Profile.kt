package com.example.pokeapp.data.models

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime

@Entity(tableName = "PROFILE")
data class Profile(

    @PrimaryKey
    val id: Int,

    @NonNull
    @ColumnInfo(name = "trainer_name")
    val name: String,

    @NonNull
    @ColumnInfo(name = "birthdate")
    val birthdate: LocalDateTime,

    @NonNull
    @ColumnInfo(name = "birthplace")
    val birthplace: String,

    @NonNull
    @ColumnInfo(name = "defaultProfile")
    val defaultProfile: Boolean
)
