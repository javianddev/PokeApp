package com.example.pokeapp.compose.navigation

import android.app.Activity
import android.content.pm.ActivityInfo
import android.content.res.Configuration
import android.view.OrientationEventListener
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.navArgument
import com.example.pokeapp.compose.home.HomeScreen
import com.example.pokeapp.compose.games.GamesScreen
import com.example.pokeapp.compose.games.TrivialScreen
import com.example.pokeapp.compose.trainer.EditTrainer
import com.example.pokeapp.compose.trainer.TrainerScreen

@Composable
fun PokeAppNavHost(navController: NavHostController, modifier: Modifier = Modifier){

    val activity = LocalContext.current as Activity

    NavHost(
        navController = navController,
        startDestination = AppScreens.HomeScreen.route,
        route = Graph.HOME.route,
        modifier = modifier
    ){

        activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        composable(AppScreens.HomeScreen.route){
            HomeScreen(navController = navController) //Aquí ya iría un onClick para la navegación etc.
        }

        trainerNavGraph(navController = navController)

        gamesNavGraph(navController = navController, activity)

    }

}


fun NavGraphBuilder.trainerNavGraph(navController: NavController){
    navigation(
        route = Graph.TRAINER.route,
        startDestination = AppScreens.TrainerScreen.route
    ){

        composable(AppScreens.TrainerScreen.route){
            TrainerScreen(navController = navController)
        }

        composable(AppScreens.EditTrainer.route){
            EditTrainer(navController = navController)
        }
    }
}

fun NavGraphBuilder.gamesNavGraph(navController: NavController, activity: Activity){
    navigation(
        route = Graph.GAMES.route,
        startDestination = AppScreens.GamesScreen.route
    ){

        composable(AppScreens.GamesScreen.route){
            GamesScreen(navController)
        }

        trivialNavGraph(navController, activity)
    }
}

fun NavGraphBuilder.trivialNavGraph(navController: NavController, activity: Activity){
    navigation(
        route = Graph.TRIVIAL.route,
        startDestination = AppScreens.Trivial.route + "/{region_id}"
    ){/*TODO A ESTO HAY QUE ECHARLE UN OJO*/

        composable(AppScreens.Trivial.route + "/{region_id}", arguments = listOf(navArgument("region_id") { type = NavType.IntType})){
            DisposableEffect(Unit) {
                val orientationEventListener = object : OrientationEventListener(activity) {
                    override fun onOrientationChanged(orientation: Int) {
                        when (orientation) {
                            in 0..225 -> activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE
                            else -> activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
                        }
                    }
                }

                orientationEventListener.enable()

                onDispose {
                    orientationEventListener.disable()
                    activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
                }
            }
            TrivialScreen(navController = navController)
        }
    }
}