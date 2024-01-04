package com.example.pokeapp.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.pokeapp.data.models.Pokemon
import kotlinx.coroutines.flow.Flow

@Dao
interface PokemonDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(pokemon: Pokemon)

    @Update
    suspend fun update(pokemon: Pokemon)

    @Delete
    suspend fun delete(pokemon: Pokemon)

    @Query("SELECT * FROM POKEMON ORDER BY id ASC")
    fun getAllPokemon(): Flow<List<Pokemon>>

    @Query("SELECT * FROM POKEMON WHERE id = :id")
    fun getPokemonById(id: Int): Flow<Pokemon>

    @Query("SELECT * FROM POKEMON WHERE pokedex_id = :pokedexId")
    fun getPokemonByPokedexId(pokedexId: Int): Flow<Pokemon>

}