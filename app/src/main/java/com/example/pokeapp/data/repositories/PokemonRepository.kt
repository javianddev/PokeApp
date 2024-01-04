package com.example.pokeapp.data.repositories

import com.example.pokeapp.data.dao.PokemonDao
import com.example.pokeapp.data.models.Pokemon
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PokemonRepository @Inject constructor(private val pokemonDao: PokemonDao){

    fun getAllPokemon() = pokemonDao.getAllPokemon()
    fun getPokemonById(id: Int) = pokemonDao.getPokemonById(id)
    fun getPokemonByPokedexId(pokedexId: Int) = pokemonDao.getPokemonByPokedexId(pokedexId)
    suspend fun insert(pokemon: Pokemon) = pokemonDao.insert(pokemon)
    suspend fun update(pokemon: Pokemon) = pokemonDao.update(pokemon)
    suspend fun delete(pokemon: Pokemon) = pokemonDao.delete(pokemon)

}