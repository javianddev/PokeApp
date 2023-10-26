package com.example.pokeapp.utils

import com.example.pokeapp.data.models.Trainer
import com.example.pokeapp.viewmodels.EditTrainerUiState

fun Trainer.toEditTrainerUiState(): EditTrainerUiState = EditTrainerUiState(
    name = name,
    birthdate = birthdate,
    birthplace = birthplace
)

fun EditTrainerUiState.toTrainer(): Trainer = Trainer(
    id = 1, //en un principio solo va a haber un perfil, así que lo aguanto con el id a 1
    name = name,
    birthdate = birthdate,
    birthplace = birthplace,
)