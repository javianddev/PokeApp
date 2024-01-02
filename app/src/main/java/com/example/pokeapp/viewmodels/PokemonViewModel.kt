package com.example.pokeapp.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokeapp.data.models.PokemonDetail
import com.example.pokeapp.remotedata.repositories.PokemonRepository
import com.example.pokeapp.utils.toPokemonDetail
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class PokemonViewModel @Inject constructor(private val pokemonRepository:PokemonRepository, savedStateHandle: SavedStateHandle): ViewModel() {


    var pokemonUiState: PokemonUiState by mutableStateOf(PokemonUiState.Loading)
        private set

    private val pokemonId = checkNotNull(savedStateHandle.get<Int>("pokemon_id"))

    init{
        getPokemon(pokemonId)
    }

    private fun getPokemon(pokemonId: Int){

        viewModelScope.launch{
            pokemonUiState = PokemonUiState.Loading
            pokemonUiState = try {
                PokemonUiState.Success(pokemonRepository.getPokemonInfo(pokemonId).toPokemonDetail())
            } catch (e: IOException) {
                PokemonUiState.Error
            } catch (e: HttpException) {
                PokemonUiState.Error
            }
        }

    }
}

sealed interface PokemonUiState {
    data class Success(val pokemon: PokemonDetail): PokemonUiState
    data object Error: PokemonUiState
    data object Loading: PokemonUiState
}