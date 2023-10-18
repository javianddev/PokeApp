package com.example.pokeapp.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokeapp.data.repositories.ProfileRepository
import com.example.pokeapp.utils.toProfile
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class EditProfileViewModel @Inject constructor(private val profileRepository: ProfileRepository): ViewModel() {

    private val _uiState = MutableStateFlow(EditProfileUiState())
    val uiState = _uiState.asStateFlow()

    init{
        getProfile()
    }

    fun setName(name: String){
        if (!name.isEmpty()){
            _uiState.update{ currentState ->
                uiState.value.copy(
                    name = name
                )
            }
        }
    }

    fun setBirthdate(birthdate: Date){
            _uiState.update{ currentState ->
                uiState.value.copy(
                    birthdate = birthdate
                )
            }
    }

    fun setBirthplace(birthplace: String){
        if (!birthplace.isEmpty()){
            _uiState.update{ currentState ->
                uiState.value.copy(
                    birthplace = birthplace
                )
            }
        }
    }

    fun getProfile(){
        viewModelScope.launch{
            try{
                profileRepository.getProfileById(1).collect{result ->
                    _uiState.update{
                        uiState.value.copy(
                            name = result.name,
                            birthplace = result.birthplace,
                            birthdate = result.birthdate
                        )
                    }
                }
            }catch (e: Exception){
                Log.e(null, "Error getting profile --> $e")
            }
        }
    }

    fun saveProfile(editProfileUiState: EditProfileUiState){
        viewModelScope.launch{
            try{
                profileRepository.updateProfile(editProfileUiState.toProfile())
            }catch(e: Exception) {
                Log.e(null, "Error saving profile --> $e")
            }
        }
    }


}



data class EditProfileUiState(
    val name: String = "",
    val birthplace: String = "",
    val birthdate: Date = Date()
)