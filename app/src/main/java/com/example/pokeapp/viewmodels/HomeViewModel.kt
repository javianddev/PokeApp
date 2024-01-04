package com.example.pokeapp.viewmodels


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.pokeapp.paging.PokemonPagingSource
import com.example.pokeapp.remotedata.model.PokemonEntry
import com.example.pokeapp.remotedata.repositories.PokedexRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val pokedexRepository: PokedexRepository): ViewModel(){

    private val _pokemons: MutableStateFlow<PagingData<PokemonEntry>> = MutableStateFlow(PagingData.empty())
    val pokemons = _pokemons.asStateFlow()

    init {
        getPokemons()
    }

    private fun getPokemons(){
        viewModelScope.launch {
            Pager(
                config = PagingConfig(20, enablePlaceholders = false)
            ){
                PokemonPagingSource(pokedexRepository)
            }.flow.cachedIn(viewModelScope).collect {
                _pokemons.value = it
            }
        }
    }

}



