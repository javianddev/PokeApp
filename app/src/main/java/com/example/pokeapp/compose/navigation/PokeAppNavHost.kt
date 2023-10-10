package com.example.pokeapp.compose.navigation

import android.app.Activity
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.pokeapp.compose.home.HomeScreen
import com.example.pokeapp.compose.profile.ProfileScreen

@Composable
fun PokeAppNavHost(navController: NavHostController, modifier: Modifier = Modifier){

    val activity = (LocalContext.current as Activity)

    NavHost(navController = navController, startDestination = AppScreens.HomeScreen.route){
        composable(AppScreens.HomeScreen.route){
            HomeScreen(navController) //Aquí ya iría un onClick para la navegación etc.
        }
        composable(AppScreens.ProfileScreen.route){
            ProfileScreen(navController)
        }
    }

}