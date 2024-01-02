package com.example.pokeapp.remotedata.services

import com.example.pokeapp.remotedata.model.PokemonInfo
import com.example.pokeapp.remotedata.model.PokemonResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiPokemonService {

    @GET("pokemon")
    suspend fun getPokemon(@Query("limit") limit: Int, @Query("offset") offset: Int): PokemonResponse

    @GET("pokemon/{id}")
    suspend fun getPokemonInfo(@Path("id") id: Int): PokemonInfo

}