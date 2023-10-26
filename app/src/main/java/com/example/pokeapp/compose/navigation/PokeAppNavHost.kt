package com.example.pokeapp.compose.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.example.pokeapp.compose.home.HomeScreen
import com.example.pokeapp.compose.games.GamesScreen
import com.example.pokeapp.compose.trainer.EditTrainer
import com.example.pokeapp.compose.trainer.TrainerScreen

@Composable
fun PokeAppNavHost(navController: NavHostController, modifier: Modifier = Modifier){

    NavHost(
        navController = navController,
        startDestination = AppScreens.HomeScreen.route,
        route = Graph.HOME.route,
        modifier = modifier
    ){

        composable(AppScreens.HomeScreen.route){
            HomeScreen() //Aquí ya iría un onClick para la navegación etc.
        }

        composable(AppScreens.TrainerScreen.route){
            TrainerScreen(navController = navController)
        }

        profileNavGraph(navController = navController)

        composable(AppScreens.GamesScreen.route){
            GamesScreen(navController)
        }

    }

}


fun NavGraphBuilder.profileNavGraph(navController: NavController){
    navigation(
        route = Graph.TRAINER.route,
        startDestination = AppScreens.EditTrainer.route
    ){

        composable(AppScreens.EditTrainer.route){
            EditTrainer(navController = navController)
        }
    }
}
