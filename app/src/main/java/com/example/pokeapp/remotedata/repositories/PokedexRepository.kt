package com.example.pokeapp.remotedata.repositories

import com.example.pokeapp.remotedata.services.ApiPokemonService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PokedexRepository @Inject constructor(private val apiPokemonService: ApiPokemonService) {

    suspend fun getPokemons(limit: Int, offset: Int) = apiPokemonService.getPokemon(limit, offset)

    suspend fun getPokemonInfo(id: Int) = apiPokemonService.getPokemonInfo(id)

}