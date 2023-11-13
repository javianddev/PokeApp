package com.example.pokeapp.compose.utils

sealed interface TrivialStatus{

    object Initial: TrivialStatus
    object Question: TrivialStatus
    object Fail: TrivialStatus
    object Win: TrivialStatus

}
