package com.example.pokeapp.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.pokeapp.remotedata.model.Pokemon
import com.example.pokeapp.remotedata.repositories.PokemonRepository
import com.example.pokeapp.utils.mapPokemonResToPokemon
import java.lang.Integer.min

class PokemonPagingSource(private val pokemonRepository: PokemonRepository): PagingSource<Int, Pokemon>() {

    override fun getRefreshKey(state: PagingState<Int, Pokemon>): Int? {
        return state.anchorPosition?.let {position ->
            val page = state.closestPageToPosition(position)
            page?.prevKey?.minus(1) ?: page?.nextKey?.plus(1) ?: position

        }
    }

    //Explicación de como funciona PAGING 3
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Pokemon> {
       return try{
           //1.Obtenemos el número de página que se está cargando
           val pageNumber = params.key ?: 0
           //2. Establecemos cuantos elementos hay por página
           val pageSize = 20
           //3. Establecemos la cantidad máxima de pokemons
           val totalPokemons = 151 // Cambia esto por la cantidad total de pokemons que tienes

           if (pageNumber * pageSize >= totalPokemons) { //4. Ya no hay más elementos que cargar
               return LoadResult.Error(NoSuchElementException("No hay más pokemons para cargar"))
           }

           //5. Calculamos todos los elementos ya cargados, es decir, son todos los elementos cargados en página anteriores
           val offset = pageNumber * pageSize
           //6. Vemos la cantidad de pokemons que quedan por cargar. Por ejemplo, si el offset es de 70, pues quedan 151 - 70
           val remainingPokemons = totalPokemons - offset
           //7. Calcula los pokemons que se cargarán en la página actual. Si el tamaño de la página es 20 y quedan 70, pues cargaremos otros 20, así hasta que queden 11.
           val pokemonsToLoad = min(pageSize, remainingPokemons)

           //8. Se hace la llamada con los datos conseguidos
           val pokemons = mapPokemonResToPokemon(pokemonRepository.getPokemons(limit = pokemonsToLoad, offset = offset))

           //9. Aumentamos el número de página
           val nextPage = pageNumber + 1

           //10. Comprobamos que no se haya pasado de 151
           val nextKey = if (nextPage * pageSize >= 151) { //Al llegar a 151 paramos de conseguir más paginas
               null
           } else {
               nextPage //Cargamos la siguiente página
           }

           //11. Comprobamos la anterior página
           val prevKey = if (pageNumber > 0) {
               pageNumber - 1
           } else {
               null
           }

           //12. Devolvemos los datos cargados, junto a los valores de la anterior y próxima página
           LoadResult.Page(
               data = pokemons,
               prevKey = prevKey,
               nextKey = nextKey
           )
        }catch (e: Exception){
            LoadResult.Error(e)
        }
    }

}