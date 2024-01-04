package com.example.pokeapp.data.models

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "MEDAL",
    foreignKeys = [
        ForeignKey(
            entity = Region::class,
            parentColumns = ["id"],
            childColumns = ["region_id"],
            onDelete = ForeignKey.RESTRICT
        )
    ])
data class Medal(

    @PrimaryKey(autoGenerate = true)
    val id: Int,

    @NonNull
    @ColumnInfo(name = "medal_name")
    val medalName: String,

    @NonNull
    @ColumnInfo(name = "image")
    val image: Int,

    @NonNull
    @ColumnInfo(name = "region_id", index = true)
    val regionId: Int
)
