package com.example.pokeapp.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.pokeapp.remotedata.model.Pokemon
import com.example.pokeapp.remotedata.repositories.PokemonRepository
import com.example.pokeapp.utils.mapPokemonResToPokemon

class PokemonPagingSource(private val pokemonRepository: PokemonRepository): PagingSource<Int, Pokemon>() {

    override fun getRefreshKey(state: PagingState<Int, Pokemon>): Int? {
        return state.anchorPosition?.let {position ->
            val page = state.closestPageToPosition(position)
            page?.prevKey?.minus(1) ?: page?.nextKey?.plus(1) ?: position

        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Pokemon> {
       return try{
           val pageNumber = params.key ?: 0 //Se carga la primera página si params.key es nulo, al avanzar carga las siguientes
           val pageSize = 20 //20 pokemons únicamente
           val offset = pageNumber * pageSize //Se cargarán 20 pokemons por página

           val pokemons = mapPokemonResToPokemon(pokemonRepository.getPokemons(limit = pageSize, offset = offset))

           val nextPage = pageNumber + 1
           val nextKey = if (nextPage * pageSize >= 151) { //Al llegar a 151 paramos de conseguir más paginas
               null
           } else {
               nextPage //Cargamos la siguiente página
           }

           LoadResult.Page(
               data = pokemons,
               prevKey = null,
               nextKey = nextKey
           )
        }catch (e: Exception){
            LoadResult.Error(e)
        }
    }

}