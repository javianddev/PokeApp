package com.example.pokeapp.compose.navigation

import android.app.Activity
import android.content.pm.ActivityInfo
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.pokeapp.compose.home.HomeScreen
import com.example.pokeapp.compose.options.EditProfile
import com.example.pokeapp.compose.options.OptionsScreen
import com.example.pokeapp.compose.profile.ProfileScreen

@Composable
fun PokeAppNavHost(navController: NavHostController, modifier: Modifier = Modifier){

    val activity = (LocalContext.current as Activity)


    NavHost(
        navController = navController,
        startDestination = AppScreens.HomeScreen.route,
        modifier = modifier
    ){
        composable(AppScreens.HomeScreen.route){
            if (activity.requestedOrientation  == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE){
                activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
            }
            HomeScreen(navController) //Aquí ya iría un onClick para la navegación etc.
        }
        composable(AppScreens.ProfileScreen.route){
            activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
            ProfileScreen()
        }
        composable(AppScreens.OptionsScreen.route){
            if (activity.requestedOrientation  == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE){
                activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
            }
            OptionsScreen(navController)
        }
        composable(AppScreens.EditProfile.route){
            EditProfile(navController)
        }
    }

}