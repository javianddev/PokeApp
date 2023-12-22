package com.example.pokeapp.viewmodels

import android.database.sqlite.SQLiteException
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokeapp.data.repositories.TrainerRepository
import com.example.pokeapp.utils.toTrainer
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.IOException
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class EditTrainerViewModel @Inject constructor(private val trainerRepository: TrainerRepository): ViewModel() {

    private val _uiState = MutableStateFlow(EditTrainerUiState())
    val uiState: StateFlow<EditTrainerUiState> = _uiState


    init{
        getProfile() //Según la documentación del proyecto de ejemplo 'INVENTORY APP' de Android, esto es posible hacerlo ante poca carga de datos.
    }

    fun setName(name: String){
       _uiState.update{
           it.copy(name = name)
        }
    }

    fun setBirthdate(birthdate: LocalDate){
        _uiState.update{
            it.copy(birthdate = birthdate)
        }
    }

    fun setBirthplace(birthplace: String){
        _uiState.update{
            it.copy(birthplace = birthplace)
        }
    }

    fun formNotBlank(): Boolean {
        return !(uiState.value.name.isEmpty() || uiState.value.birthplace.isEmpty())
    }

    private fun getProfile() {
        viewModelScope.launch{
            try {
                trainerRepository.getTrainerById(1).collect { result ->
                    _uiState.update {currentState ->
                        currentState.copy(
                            name = result.name,
                            birthplace = result.birthplace,
                            birthdate = result.birthdate
                        )
                    }
                }
            }
            catch(e: SQLiteException){
                Log.e("EditTrainerViewModel", "Exception getting trainer -->  $e")
            }
        }
    }

    fun saveTrainer(editTrainerUiState: EditTrainerUiState){
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