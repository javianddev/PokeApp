package com.example.pokeapp.viewmodels

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
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
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject


@HiltViewModel
class TrainerViewModel @Inject constructor(
    private val trainerRepository: TrainerRepository,
    private val regionRepository: RegionRepository,
    private val pokemonRepository: PokemonRepository
    //private val medalRepository: MedalRepositry
): ViewModel(){

    private val _uiState = MutableStateFlow(TrainerUiState())
    val uiState: StateFlow<TrainerUiState> = _uiState

    private val _regionMedal = mutableStateListOf<Pair<Region, List<Int>>>()
    val regionMedal: List<Pair<Region, List<Int>>> = _regionMedal

    init {
        getTrainerData()
    }

    private fun getTrainerData() {
        viewModelScope.launch {
            try {
                val trainer = trainerRepository.getTrainerById(1).first()
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
                        trainer = trainer,
                        regions = regions,
                        pokemonTeam = pokemonTeam
                    )
                }

            } catch (e: Exception) {
                Log.e(null, "Error getting trainer TrainerViewModel --> $e")
            }
        }
    }
    fun getPokedex(){
        viewModelScope.launch {
            /*TODO HACER LA LLAMADA A LA API PARA LA SEARCHBAR*/

        }
    }
}


data class TrainerUiState(
    val trainer: Trainer = Trainer(1, "Rojo", LocalDate.now().minusYears(18), "Pueblo Paleta"),
    val regions: List<Region> = emptyList(),
    //val medals: List<Medal> = emptyList() /*TODO HAY QUE AVERIGUAR UNA MANERA CORRECTA DE HACER REFERENCIA A LOS DRAWABLES EN BBDD*/
    val pokemonTeam: List<Pokemon> = emptyList()
)