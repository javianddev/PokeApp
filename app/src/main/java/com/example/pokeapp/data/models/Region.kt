package com.example.pokeapp.data.models

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "REGION")
data class Region(

    @PrimaryKey(autoGenerate = true)
    val id:Int,

    @NonNull
    @ColumnInfo(name = "name")
    val name: String,

    @NonNull
    @ColumnInfo(name = "medalAchieved")
    val medalAchieved: Boolean
)
