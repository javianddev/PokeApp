package com.example.pokeapp.data.models

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "QUESTION",
    foreignKeys = [
        ForeignKey(
            entity = Region::class,
            parentColumns = ["id"],
            childColumns = ["region_id"],
            onDelete = ForeignKey.RESTRICT
        )
    ]
)
data class Question(

    @PrimaryKey(autoGenerate = true)
    val id:Int,

    @NonNull
    @ColumnInfo(name = "text")
    val text: String,

    @NonNull
    @ColumnInfo(name = "region_id")
    val region_id: Int

)
