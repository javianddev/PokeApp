package com.example.pokeapp.compose.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.example.pokeapp.compose.home.HomeScreen
import com.example.pokeapp.compose.games.EditProfile
import com.example.pokeapp.compose.games.GamesScreen
import com.example.pokeapp.compose.profile.ProfileScreen

@Composable
fun PokeAppNavHost(navController: NavHostController, modifier: Modifier = Modifier){


    NavHost(
        navController = navController,
        startDestination = AppScreens.HomeScreen.route,
        route = AppScreens.Graph.HOME,
        modifier = modifier
    ){
        composable(AppScreens.HomeScreen.route){
            HomeScreen() //Aquí ya iría un onClick para la navegación etc.
        }
        composable(AppScreens.ProfileScreen.route){
            ProfileScreen { navController.navigate(AppScreens.Graph.TRAINER) }
        }
        composable(AppScreens.GamesScreen.route){
            GamesScreen(navController)
        }

        profileNavGraph(navController = navController)

    }

}


fun NavGraphBuilder.profileNavGraph(navController: NavHostController){
    navigation(
        route = AppScreens.Graph.TRAINER,
        startDestination = AppScreens.EditTrainer.route
    ){
        composable(AppScreens.EditTrainer.route){
            EditProfile(onClick = {
              navController.popBackStack(route = AppScreens.ProfileScreen.route, inclusive = false)
            })
        }
    }
}

