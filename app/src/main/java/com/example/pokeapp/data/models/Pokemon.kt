package com.example.pokeapp.data.models

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "POKEMON")
data class Pokemon(

    @PrimaryKey(autoGenerate = true)
    val id: Int,

    @NonNull
    @ColumnInfo(name = "pokedex_id")
    val pokedexId: Int,

    @NonNull
    @ColumnInfo(name = "name")
    val name: String,

    @NonNull
    @ColumnInfo(name = "image_url")
    val imageUrl: String
)
