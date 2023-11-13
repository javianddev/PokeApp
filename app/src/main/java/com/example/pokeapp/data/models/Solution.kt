package com.example.pokeapp.data.models

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "SOLUTION",
    foreignKeys = [
        ForeignKey(
            entity = Question::class,
            parentColumns = ["id"],
            childColumns = ["question_id"],
            onDelete = ForeignKey.RESTRICT
        )
    ]
)
data class Solution(

    @PrimaryKey(autoGenerate = true)
    val id: Int,

    @NonNull
    @ColumnInfo(name = "answer")
    val answer: String,

   /* @NonNull Este campo hay que ponerlo sí o sí
    @ColumnInfo(name="isCorrect")
    val isCorrect: Int,*/

    @NonNull
    @ColumnInfo(name = "question_id", index = true)
    val question_id: Int



)
