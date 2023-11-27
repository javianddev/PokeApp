package com.example.pokeapp.viewmodels

import android.database.sqlite.SQLiteException
import android.os.CountDownTimer
import android.util.Log
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokeapp.compose.utils.TrivialStatus
import com.example.pokeapp.data.models.Question
import com.example.pokeapp.data.models.Solution
import com.example.pokeapp.data.repositories.QuestionRepository
import com.example.pokeapp.data.repositories.RegionRepository
import com.example.pokeapp.data.repositories.SolutionRepository
import com.example.pokeapp.ui.theme.md_theme_background
import com.example.pokeapp.ui.theme.md_theme_error
import com.example.pokeapp.ui.theme.md_theme_success
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class TrivialViewModel @Inject constructor(private val questionRepository: QuestionRepository,
                                           private val solutionRepository: SolutionRepository,
                                           private val regionRepository: RegionRepository,
                                           savedStateHandle: SavedStateHandle): ViewModel(){

    private val _uiState = MutableStateFlow(TrivialUiState())
    val uiState: StateFlow<TrivialUiState> = _uiState

    private val _trivialData = mutableStateListOf<Pair<Question,List<Solution>>>()
    val trivialData: List<Pair<Question,List<Solution>>> = _trivialData

    private val regionId = checkNotNull(savedStateHandle.get<Int>("region_id"))

    init {
        _uiState.value = TrivialUiState()
        getTrivialData()
    }

    private fun getTrivialData(){
        Log.d("TrivialData ", "gettingTrivialData...")
         viewModelScope.launch{
            try{
               questionRepository.getQuestionsByRegionId(regionId).collect { questions ->
                  questions.map{
                          question ->
                                async{
                                    solutionRepository.getSolutionsByQuestionId(question.id).collect { solutions ->
                                        _trivialData.add(Pair(question, solutions.shuffled()))
                                }
                            }

                        }.awaitAll()
                    }
                _trivialData.shuffled()
            } catch(e: SQLiteException){
                Log.e("ErrorGettingData", "$e")
            }
        }
    }

    fun updateCont(messages: List<String>){
        if (_uiState.value.status == TrivialStatus.Initial && _uiState.value.cont.plus(1) == messages.size){ //Entrada a modo preguntas
           _uiState.update{currentState ->
               currentState.copy(
                   status = TrivialStatus.Question, //Pasamos a modo preguntas
                   cont = 0, //Reiniciamos el contador
               )
           }
           Log.i("UpdateCont", "Se reinicia el contador")
       } else if (_uiState.value.cont.plus(1) < messages.size) { //Actualizo el contador en todos los demás casos
           Log.i("UpdateCont", "Se suma uno al contador en todos los casos --> ${_uiState.value.cont} - ${_uiState.value.status} - ${messages.size}")
           _uiState.update{currentState ->
               currentState.copy(
                   cont = currentState.cont.plus(1)
               )
           }
           Log.i("UpdateCont", "Se suma uno al contador --> ${_uiState.value.cont}")
       }
    }

    /*fun timer(isPaused: Boolean){
        while (_uiState.value.timer > 0 && !isPaused){

            _uiState.update { currentState ->
                currentState.copy(
                    timer = currentState.timer.minus(1)
                )
            }
        }
    }*/

    fun userGuess(isCorrect: Boolean){
        viewModelScope.launch{
            if (isCorrect){
                _uiState.update{currentState ->
                    currentState.copy(
                        buttonColor = md_theme_success,
                        enabledButton = false
                    )
                }
                delay(3000L)
                if (_uiState.value.cont.plus(1) < trivialData.size) { //Acertamos y avanzamos
                    _uiState.update{currentState ->
                        currentState.copy(
                            cont = currentState.cont.plus(1),
                            buttonColor = md_theme_background,
                            enabledButton = true
                        )
                    }
                    //Y por supuesto, actualizamos la región, hemos conseguido las medallas
                    regionRepository.updateRegionMedal(regionId, 1) /*TODO PONER EL 1 Y EL 0 EN UNA INTERFAZ*/
                } else { //Llegamos a la última pregunta y la hemos acertado, hemos ganado el trivial
                    _uiState.update{currentState ->
                        currentState.copy(
                            status = TrivialStatus.Win,
                            cont = 0
                        )
                    }
                }
            } else {
                _uiState.update{currentState ->
                    currentState.copy(
                        buttonColor = md_theme_error,
                        enabledButton = false
                    )
                }
                delay(3000L)
                _uiState.update {currentState->
                    currentState.copy(
                        status = TrivialStatus.Fail,
                        cont = 0
                    )
                }
            }
        }
    }

}


data class TrivialUiState(

    val cont: Int = 0,
    val status: TrivialStatus = TrivialStatus.Initial,
    //val timer: Int = 10, /*TODO Timer*/
    val buttonColor: Color = md_theme_background,
    val enabledButton: Boolean = true

)
