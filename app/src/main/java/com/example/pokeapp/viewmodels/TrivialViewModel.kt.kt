package com.example.pokeapp.viewmodels

import android.database.sqlite.SQLiteException
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokeapp.compose.utils.TrivialStatus
import com.example.pokeapp.data.models.Question
import com.example.pokeapp.data.models.Solution
import com.example.pokeapp.data.repositories.QuestionRepository
import com.example.pokeapp.data.repositories.SolutionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TrivialViewModel @Inject constructor(private val questionRepository: QuestionRepository,
                                           private val solutionRepository: SolutionRepository, savedStateHandle: SavedStateHandle): ViewModel(){

    private val _uiState = MutableStateFlow(TrivialUiState())
    val uiState: StateFlow<TrivialUiState> = _uiState

    private val _trivialData = mutableStateListOf<Pair<Question,List<Solution>>>()
    val trivialData: List<Pair<Question,List<Solution>>> = _trivialData

    private val regionId = checkNotNull(savedStateHandle.get<Int>("region_id"))

    init {
        getTrivialData()
    }

    //Esto no funciona del todo bien pero ya consigue las preguntas por lo menos
    private fun getTrivialData(){
        Log.d("TrivialData ", "gettingTrivialData...")
         val trivialData= mutableListOf<Pair<Question, List<Solution>>>()
         viewModelScope.launch(Dispatchers.IO){
            try{
               questionRepository.getQuestionsByRegionId(regionId).collect { questions ->
                    questions.map {
                        for (it in questions) {
                            solutionRepository.getSolutionsByQuestionId(it.id).collect { solutions ->
                                _trivialData.add(Pair(it, solutions))
                            }
                        }
                    }
                }

            } catch(e: SQLiteException){
                Log.e("ErrorGettingData", "$e")
            }
        }
        /*_uiState.update{currentState ->
            currentState.copy(
                trivialData = trivialData
            )
        }*/
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
       } else { //Actualizo el contador en todos los demás casos
           _uiState.update{currentState ->
               currentState.copy(
                   cont = currentState.cont.plus(1)
               )
           }
           Log.i("UpdateCont", "Se suma uno al contador --> ${_uiState.value.cont}")
       }
    }
}


data class TrivialUiState(

    val cont: Int = 0,
    val status: TrivialStatus = TrivialStatus.Initial,
    //Falta el contador de respuestas correctas.

)
