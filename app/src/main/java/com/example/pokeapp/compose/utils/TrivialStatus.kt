package com.example.pokeapp.compose.utils

sealed interface TrivialStatus{

    data object Initial: TrivialStatus
    data object Question: TrivialStatus
    data object Fail: TrivialStatus
    data object Win: TrivialStatus

}
