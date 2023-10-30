package com.example.pokeapp.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokeapp.data.repositories.TrainerRepository
import com.example.pokeapp.utils.toTrainer
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class EditTrainerViewModel @Inject constructor(private val trainerRepository: TrainerRepository): ViewModel() {

    private val _uiState = MutableStateFlow(EditTrainerUiState())
    val uiState = _uiState.asStateFlow()

    init{
        getProfile() //Esto hay que cambiarlo
    }

    fun setName(name: String){
        if (!name.isEmpty()){
           _uiState.update{
               it.copy(name = name)
           }
        }
    }

    fun setBirthdate(birthdate: LocalDate){
        _uiState.update{
            it.copy(birthdate = birthdate)
        }
    }

    fun setBirthplace(birthplace: String){
        if (!birthplace.isEmpty()) {
            _uiState.update{
                it.copy(birthplace = birthplace)
            }
        }
    }

    fun getProfile(){
        viewModelScope.launch{
            try{
                trainerRepository.getTrainerById(1).collect{result ->
                    _uiState.update{
                        uiState.value.copy(
                            name = result.name,
                            birthplace = result.birthplace,
                            birthdate = result.birthdate
                        )
                    }
                }
            }catch (e: Exception){
                Log.e(null, "Error getting trainer --> $e")
            }
        }
    }

    fun saveProfile(editTrainerUiState: EditTrainerUiState){
        viewModelScope.launch{
            try{
                trainerRepository.updateTrainer(editTrainerUiState.toTrainer())
            }catch(e: Exception) {
                Log.e(null, "Error saving trainer --> $e")
            }
        }
    }


}



data class EditTrainerUiState(
    val name: String = "",
    val birthplace: String = "",
    val birthdate: LocalDate = LocalDate.now().minusYears(18)
)