package com.example.pokeapp.utils

import com.example.pokeapp.data.models.Profile
import com.example.pokeapp.viewmodels.EditProfileUiState

fun Profile.toEditProfileUiState(): EditProfileUiState = EditProfileUiState(
    name = name,
    birthdate = birthdate,
    birthplace = birthplace
)

fun EditProfileUiState.toProfile(): Profile = Profile(
    id = 1, //en un principio solo va a haber un perfil, así que lo aguanto con el id a 1
    name = name,
    birthdate = birthdate,
    birthplace = birthplace,
    defaultProfile = false
)