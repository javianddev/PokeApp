package com.example.pokeapp.compose.navigation

sealed class AppScreens(val route: String){

    object HomeScreen: AppScreens("home")
    object ProfileScreen: AppScreens("profile")
    object GamesScreen: AppScreens("games")
}
