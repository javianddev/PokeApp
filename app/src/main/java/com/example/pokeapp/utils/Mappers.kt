package com.example.pokeapp.utils

import com.example.pokeapp.data.models.Trainer
import com.example.pokeapp.remotedata.model.Pokemon
import com.example.pokeapp.remotedata.model.PokemonResponse
import com.example.pokeapp.viewmodels.EditTrainerUiState

fun EditTrainerUiState.toTrainer(): Trainer = Trainer(
    id = 1, //en un principio solo va a haber un perfil, así que lo aguanto con el id a 1
    name = name,
    birthdate = birthdate,
    birthplace = birthplace,
)

fun mapPokemonResToPokemon(pokemonResponse: PokemonResponse): List<Pokemon> {
    return pokemonResponse.results.map{result ->
        val id = if(result.url.endsWith("/")) {
            result.url.dropLast(1).takeLastWhile { it.isDigit() }
        } else {
            result.url.takeLastWhile { it.isDigit() }
        }
        Pokemon(
            id = id,
            name= result.name,
            imageUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/$id.png", //En PokeApi, los sprites van con esta URL
        )
    }
}