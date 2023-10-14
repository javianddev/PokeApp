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
    val text: Int){

    object HomeScreen: AppScreens("home", Icons.Filled.Home, R.string.home_icon, R.string.home)
    object ProfileScreen: AppScreens("profile", Icons.Filled.AccountCircle, R.string.profile_icon,R.string.profile)
    object OptionsScreen: AppScreens("options", Icons.Filled.Extension, R.string.options_icon, R.string.options)
    object EditProfile: AppScreens("games/edit_profile", Icons.Filled.Create, R.string.edit_profile_icon, R.string.edit_profile)
}


val routes = listOf(AppScreens.ProfileScreen, AppScreens.HomeScreen, AppScreens.OptionsScreen)

val optionLinks = listOf(AppScreens.EditProfile)