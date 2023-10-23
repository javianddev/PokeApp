package com.example.pokeapp.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokeapp.data.models.Profile
import com.example.pokeapp.data.repositories.ProfileRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(private val profileRepository: ProfileRepository): ViewModel(){

    private val _uiState = MutableStateFlow(ProfileUiState())
    val uiState = _uiState.asStateFlow()

    init {
        getProfile()
    }

    fun getProfile(){

        viewModelScope.launch{
            try{
                profileRepository.getProfileById(1).collect{result ->
                    _uiState.update{
                        uiState.value.copy(
                            profile = result
                        )
                    }
                }
            }catch (e: Exception){
                Log.e(null, "Error getting profile --> $e")
            }
        }

    }
}


data class ProfileUiState(
    val profile: Profile = Profile(1, "Rojo", LocalDateTime.now().minusYears(18), "Pueblo Paleta", true),
    //val medals: List<Medal> = emptyList()
    //val pokemonTeam: List<Pokemon> = emptyList()
)