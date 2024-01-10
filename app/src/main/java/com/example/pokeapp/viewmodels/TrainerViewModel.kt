package com.example.pokeapp.viewmodels

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokeapp.compose.utils.MedalResources
import com.example.pokeapp.compose.utils.RegionConstants
import com.example.pokeapp.data.models.Pokemon
import com.example.pokeapp.data.models.Region
import com.example.pokeapp.data.models.Trainer
import com.example.pokeapp.data.repositories.PokemonRepository
import com.example.pokeapp.data.repositories.RegionRepository
import com.example.pokeapp.data.repositories.TrainerRepository
import com.example.pokeapp.remotedata.model.PokemonEntry
import com.example.pokeapp.remotedata.repositories.PokedexRepository
import com.example.pokeapp.utils.mapPokemonResToPokemon
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject
import java.io.IOException
import retrofit2.HttpException
import java.util.Locale

@HiltViewModel
class TrainerViewModel @Inject constructor(
    private val trainerRepository: TrainerRepository,
    private val regionRepository: RegionRepository,
    private val pokemonRepository: PokemonRepository,
    private val pokedexRepository: PokedexRepository
    //private val medalRepository: MedalRepositry
): ViewModel(){

    private val _uiState = MutableStateFlow(TrainerUiState())
    val uiState: StateFlow<TrainerUiState> = _uiState

    private val _regionMedal = mutableStateListOf<Pair<Region, List<Int>>>()
    val regionMedal: List<Pair<Region, List<Int>>> = _regionMedal

    private val _modalSheet = mutableStateOf(false)
    val modalSheet: MutableState<Boolean> = _modalSheet

    private val _searchBarState = mutableStateOf(SearchBarStateUi())
    val searchBarState: MutableState<SearchBarStateUi> = _searchBarState
    var pokedexUiState: PokedexUiState by mutableStateOf(PokedexUiState.Loading)
        private set

    init {
        getInitData()
    }

    private fun getInitData() {
        viewModelScope.launch {
            try {
                val regions: List<Region> = regionRepository.getAllRegion().first()
                val pokemonTeam: List<Pokemon> = pokemonRepository.getAllPokemon().first()

                for (region in regions){
                    when (region.id){
                        RegionConstants.KANTO -> _regionMedal.add(Pair(region, MedalResources.kantoMedals))
                        RegionConstants.JOHTO -> _regionMedal.add(Pair(region, MedalResources.johtoMedals))
                        RegionConstants.HOENN -> _regionMedal.add(Pair(region, MedalResources.hoennMedals))
                    }
                }

                _uiState.update{currentState->
                    currentState.copy(
                        regions = regions,
                        pokemonTeam = pokemonTeam
                    )
                }

            } catch (e: Exception) {
                Log.e(null, "Error getting trainer TrainerViewModel --> $e")
            }
        }
    }

    fun getPokemonTeam(){
        viewModelScope.launch {
            try {
                val pokemonTeam: List<Pokemon> = pokemonRepository.getAllPokemon().first()

                _uiState.update{currentState->
                    currentState.copy(
                        pokemonTeam = pokemonTeam
                    )
                }

            } catch (e: Exception) {
                Log.e(null, "Error getting trainer TrainerViewModel --> $e")
            }
        }
    }

    fun getTrainerData(){
        viewModelScope.launch {
            try {
                val trainer = trainerRepository.getTrainerById(1).first()

                _uiState.update{currentState->
                    currentState.copy(
                        trainer = trainer
                    )
                }

            } catch (e: Exception) {
                Log.e(null, "Error getting trainer TrainerViewModel --> $e")
            }
        }
    }

    fun getPokedex(){
        viewModelScope.launch {
            pokedexUiState = PokedexUiState.Loading
            pokedexUiState = try {
                PokedexUiState.Success(mapPokemonResToPokemon(pokedexRepository.getFullPokedex(151)))
            } catch (e: IOException) {
                PokedexUiState.Error
            } catch (e: HttpException) {
                PokedexUiState.Error
            }
        }
    }

    fun openModalSheet(indexTeam: Int){
        _modalSheet.value = true
        _uiState.update {currentState ->
            currentState.copy(
                indexButton = indexTeam
            )
        }
    }

    fun closeModalSheet(){
        _modalSheet.value = false
        _uiState.update {currentState ->
            currentState.copy(
                indexButton = 0
            )
        }
        _searchBarState.value = _searchBarState.value.copy(query = "")
    }

    fun setQuery(query: String){
        _searchBarState.value = _searchBarState.value.copy(query = query.lowercase())
    }

    fun setActive(active: Boolean){
        _searchBarState.value = _searchBarState.value.copy(active = active)
    }

    fun setPokemonTeam(pokemon: PokemonEntry){
        viewModelScope.launch {
            try {
                val pokeToInsert = Pokemon(
                    id = _uiState.value.indexButton,
                    pokedexId = pokemon.id.toInt(),
                    name = pokemon.name,
                    imageUrl = pokemon.imageUrl
                )
                Log.i("SETPOKEMONTEAM", "$pokeToInsert")
                pokemonRepository.insert(pokeToInsert)
            } catch (e: Exception){
                Log.e("TrainerSetPokemonTeam", "Error saving Pokemon: $e")
            }
        }
    }
}


data class TrainerUiState(
    val trainer: Trainer = Trainer(1, "Rojo", LocalDate.now().minusYears(18), "Pueblo Paleta"),
    val regions: List<Region> = emptyList(),
    //val medals: List<Medal> = emptyList() /*TODO HAY QUE AVERIGUAR UNA MANERA CORRECTA DE HACER REFERENCIA A LOS DRAWABLES EN BBDD*/
    val pokemonTeam: List<Pokemon> = emptyList(),
    val indexButton: Int = 0
)

data class SearchBarStateUi(
    val active: Boolean = false,
    val query: String = "",
)

sealed interface PokedexUiState {
    data class Success(val pokemons: List<PokemonEntry>) : PokedexUiState
    data object Error : PokedexUiState
    data object Loading : PokedexUiState
}