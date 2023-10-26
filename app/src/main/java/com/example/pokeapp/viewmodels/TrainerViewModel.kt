package com.example.pokeapp.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokeapp.data.models.Trainer
import com.example.pokeapp.data.repositories.TrainerRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class TrainerViewModel @Inject constructor(private val trainerRepository: TrainerRepository): ViewModel(){

    private val _uiState = MutableStateFlow(TrainerUiState())
    val uiState = _uiState.asStateFlow()

    init {
        getTrainer() //Esto hay que cambiarlo
    }

    fun getTrainer(){

        viewModelScope.launch{
            try{
                trainerRepository.getTrainerById(1).collect{result ->
                    _uiState.update{
                        uiState.value.copy(
                            trainer = result
                        )
                    }
                }
            }catch (e: Exception){
                Log.e(null, "Error getting trainer --> $e")
            }
        }

    }
}


data class TrainerUiState(
    val trainer: Trainer = Trainer(1, "Rojo", LocalDate.now().minusYears(18), "Pueblo Paleta"),
    //val medals: List<Medal> = emptyList()
    //val pokemonTeam: List<Pokemon> = emptyList()
)