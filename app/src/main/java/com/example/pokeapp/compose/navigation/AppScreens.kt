package com.example.pokeapp.compose.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Extension
import androidx.compose.material.icons.filled.Home
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.pokeapp.R

sealed class AppScreens(
    val route: String,
    val painter: ImageVector,
    val homeIcon: Int,
    val text: Int,
    ){

    data object Graph {
        const val HOME = "home_graph"
        const val TRAINER = "trainer_graph"
        const val GAMES = "games_graph"
    }
    data object HomeScreen: AppScreens("home", Icons.Filled.Home, R.string.home_icon, R.string.home)
    data object ProfileScreen: AppScreens("trainer", Icons.Filled.AccountCircle, R.string.trainer_icon,R.string.trainer)
    data object GamesScreen: AppScreens("games", Icons.Filled.Extension, R.string.options_icon, R.string.options)
    data object EditTrainer: AppScreens("edit_trainer", Icons.Filled.Create, R.string.edit_trainer_icon, R.string.edit_trainer) //Ruta secundaria de profile

    //Cambiar los datos de estas tres
    data object KantoMedal: AppScreens("kanto", Icons.Filled.Create, R.string.edit_trainer_icon, R.string.kanto_medals) //Ruta secundaria de game
    data object JohtoMedal: AppScreens("johto", Icons.Filled.Create, R.string.edit_trainer_icon, R.string.johto_medals) //Ruta secundaria de game
    data object HoennMedal: AppScreens("hoenn", Icons.Filled.Create, R.string.edit_trainer_icon, R.string.hoenn_medals) //Ruta secundaria de game

}


val routes = listOf(AppScreens.ProfileScreen, AppScreens.HomeScreen, AppScreens.GamesScreen)

val bottomRoutes = listOf("home", "trainer", "games")

val gameLinks = listOf(AppScreens.KantoMedal, AppScreens.JohtoMedal, AppScreens.HoennMedal)