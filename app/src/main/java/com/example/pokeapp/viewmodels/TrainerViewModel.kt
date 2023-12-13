package com.example.pokeapp.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokeapp.compose.utils.MedalResources
import com.example.pokeapp.compose.utils.RegionConstants
import com.example.pokeapp.data.models.Region
import com.example.pokeapp.data.models.Trainer
import com.example.pokeapp.data.repositories.RegionRepository
import com.example.pokeapp.data.repositories.TrainerRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject


@HiltViewModel
class TrainerViewModel @Inject constructor(
    private val trainerRepository: TrainerRepository,
    private val regionRepository: RegionRepository,
    //private val medalRepository: MedalRepositry
): ViewModel(){

    private val _uiState = MutableStateFlow(TrainerUiState())
    val uiState: StateFlow<TrainerUiState> = _uiState

    init {
        getTrainerData()
    }

    private fun getTrainerData(){
        viewModelScope.launch{
            try{
                async{
                    trainerRepository.getTrainerById(1).collect{ result ->
                        _uiState.update{currentState ->
                            currentState.copy(
                                trainer = result
                            )
                        }
                    }
                    regionRepository.getAllRegion().collect { result ->
                        _uiState.update{currentState ->
                            currentState.copy(
                                regions = result
                            )
                        }
                    }
                    /*medalRepository.getAllMedals().collect { result ->
                        _uiState.update{currentState ->
                            currentState.copy(
                                medals = result /*TODO HAY QUE AVERIGUAR UNA MANERA CORRECTA DE HACER REFERENCIA A LOS DRAWABLES EN BBDD*/
                            )
                        }
                    }*/
                }.await()
                for (region in _uiState.value.regions){
                    when (region.id){
                        RegionConstants.KANTO -> _uiState.value.regionWMedal.add(Pair(region, MedalResources.kantoMedals))
                        RegionConstants.JOHTO -> _uiState.value.regionWMedal.add(Pair(region, MedalResources.johtoMedals))
                        RegionConstants.HOENN -> _uiState.value.regionWMedal.add(Pair(region, MedalResources.hoennMedals))
                    }
                }
            }catch (e: Exception){
                Log.e(null, "Error getting trainer TrainerViewModel --> $e")
            }
        }
    }
}


data class TrainerUiState(
    val trainer: Trainer = Trainer(1, "Rojo", LocalDate.now().minusYears(18), "Pueblo Paleta"),
    val regions: List<Region> = emptyList(),
    val regionWMedal:MutableList<Pair<Region, List<Int>>> = mutableListOf()
    //val medals: List<Medal> = emptyList() /*TODO HAY QUE AVERIGUAR UNA MANERA CORRECTA DE HACER REFERENCIA A LOS DRAWABLES EN BBDD*/
    //val pokemonTeam: List<Pokemon> = emptyList()
)