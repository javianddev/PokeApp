package com.example.pokeapp.compose.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Extension
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.VideogameAsset
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.pokeapp.R

sealed class AppScreens(
    val route: String,
    val painter: ImageVector,
    val homeIcon: Int,
    val text: Int,
    ){

    data object HomeScreen: AppScreens("home", Icons.Filled.Home, R.string.home_icon, R.string.home)
    data object TrainerScreen: AppScreens("trainer", Icons.Filled.AccountCircle, R.string.trainer_icon,R.string.trainer)
    data object GamesScreen: AppScreens("games", Icons.Filled.Extension, R.string.options_icon, R.string.options)
    data object EditTrainer: AppScreens("edit_trainer", Icons.Filled.Create, R.string.edit_trainer_icon, R.string.edit_trainer) //Ruta secundaria de trainer
    data object Trivial: AppScreens("trivial", Icons.Filled.VideogameAsset, R.string.trivial, R.string.medals)

}


val routes = listOf(AppScreens.TrainerScreen, AppScreens.HomeScreen, AppScreens.GamesScreen)

val bottomRoutes = listOf(AppScreens.HomeScreen.route, AppScreens.TrainerScreen.route, AppScreens.GamesScreen.route)

val barsScreens = listOf(AppScreens.HomeScreen.route, AppScreens.TrainerScreen.route, AppScreens.GamesScreen.route, AppScreens.EditTrainer.route)

sealed class Graph(
    val route: String
){
    data object GAMES: Graph("games_graph")
    data object TRAINER: Graph("trainer_graph")
    data object HOME: Graph("home_graph")
    data object TRIVIAL: Graph("trivial_graph")
}