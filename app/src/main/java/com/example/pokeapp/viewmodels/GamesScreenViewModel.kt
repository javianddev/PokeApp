package com.example.pokeapp.viewmodels

import android.database.sqlite.SQLiteException
import android.util.Log
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowRight
import androidx.compose.material.icons.filled.Lock
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokeapp.R
import com.example.pokeapp.data.models.Region
import com.example.pokeapp.data.repositories.RegionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GamesScreenViewModel @Inject constructor(private val regionRepository: RegionRepository): ViewModel() {

    private val _uiState = MutableStateFlow(GamesUiState())
    val uiState: StateFlow<GamesUiState> = _uiState

    init {
        getRegions()
    }

    private fun getRegions(){

        viewModelScope.launch{
            try{
                regionRepository.getAllRegion().collect { result ->
                    _uiState.update{currentState ->
                        currentState.copy(
                            regions = result
                        )
                    }
                }
            }catch(e: SQLiteException){
                Log.e("Error getting all regions --> ", "$e")
            }
        }

    }

}





data class GamesUiState(
    val regions: List<Region> = emptyList()
)