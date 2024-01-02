package com.example.pokeapp.data.models

import com.example.pokeapp.remotedata.model.Stat
import com.example.pokeapp.remotedata.model.Type


data class PokemonDetail(

    val id: Int,
    val name: String,
    val imageUrl: String,
    val stats: List<Stat>,
    val types: List<Type>,
    val height: Int,
    val weight: Int

)
