package com.example.pokeapp.viewmodels

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.example.pokeapp.paging.PokemonPagingSource
import com.example.pokeapp.remotedata.model.Pokemon
import com.example.pokeapp.remotedata.repositories.PokemonRepository
import com.example.pokeapp.utils.mapPokemonResToPokemon
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val pokemonRepository: PokemonRepository): ViewModel(){

    val pokemonsPager = Pager(PagingConfig(pageSize = 20)){
        PokemonPagingSource(pokemonRepository)
    }.flow.cachedIn(viewModelScope) //mantenemos la información en caché durante la vida útil del viewModel
}



