package com.example.pokeapp.compose.navigation

import android.app.Activity
import android.content.pm.ActivityInfo
import android.view.OrientationEventListener
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Modifier
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
import com.example.pokeapp.compose.home.PokemonScreen
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
/*TODO CUANDO INTENTO REGRESAR A INICIO CON EL BOTÓN INFERIOR, NO NAVEGA A APPSCREES.HOMESCREEN.ROUTE NO SE XQ, A PESAR DE SALIR CORRECTAMENTE EN EL DEBUG EL VALOR */
        activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        composable(AppScreens.HomeScreen.route){
            HomeScreen(navController = navController)
        }

        composable(AppScreens.PokemonScreen.route + "/{pokemon_id}", arguments = listOf(navArgument("pokemon_id") {type = NavType.IntType})){
            PokemonScreen()
        }

        //pokemonNavGraph(navController = navController)

        trainerNavGraph(navController = navController)

        gamesNavGraph(navController = navController, activity)

    }

}

fun NavGraphBuilder.pokemonNavGraph(navController: NavController){ /*TODO LO MISMO QUE EL TODO ANTERIOR*/
    navigation(
        route = Graph.POKEMON.route,
        startDestination = AppScreens.PokemonScreen.route + "/{pokemon_id}"
    ){
        composable(AppScreens.PokemonScreen.route + "/{pokemon_id}", arguments = listOf(navArgument("pokemon_id") {type = NavType.IntType})){
            PokemonScreen()
        }
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
            if (activity.requestedOrientation == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE){
                activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
            }
            GamesScreen(navController)
        }

        trivialNavGraph(navController, activity)
    }
}

fun NavGraphBuilder.trivialNavGraph(navController: NavController, activity: Activity){
    navigation(
        route = Graph.TRIVIAL.route,
        startDestination = AppScreens.Trivial.route + "/{region_id}"
    ){

        composable(AppScreens.Trivial.route + "/{region_id}", arguments = listOf(navArgument("region_id") { type = NavType.IntType})){
            /*
            Por si lo quiero hacer dinámico, aunque no me mola como funciona
            DisposableEffect(Unit) {
                val orientationEventListener = object : OrientationEventListener(activity) {
                    override fun onOrientationChanged(orientation: Int) {
                        /* Para cambiar la orientación controlando la posición del móvil
                        when (orientation) {
                            in 0..170 -> activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE
                            else -> activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
                        }*/
                        activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
                    }
                }

                orientationEventListener.enable()

                onDispose {
                    orientationEventListener.disable()
                    activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
                }
            }*/
            activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
            TrivialScreen(navController = navController)
        }
    }
}