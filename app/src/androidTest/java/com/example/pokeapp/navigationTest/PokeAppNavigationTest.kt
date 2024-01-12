package com.example.pokeapp.navigationTest

import androidx.activity.ComponentActivity
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import com.example.pokeapp.compose.PokeApp
import com.example.pokeapp.compose.navigation.AppScreens
import com.example.pokeapp.R
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@get:Rule
val composeTestRule = createAndroidComposeRule<ComponentActivity>()

private lateinit var navController: TestNavHostController

@Before
fun setUpPokeAppNavHost(){
    composeTestRule.setContent{
        navController = TestNavHostController(LocalContext.current).apply {
            navigatorProvider.addNavigator(ComposeNavigator())
        }
        PokeApp()
    }
}

//Comprobamos la ruta de inicio de la app
@Test
fun pokeAppNavHost_verifyStartDestination(){
    navController.assertCurrentRouteName(AppScreens.HomeScreen.route)
}

//Comprobamos que la ruta de inicio NO tiene flecha para volver atrás
@Test
fun pokeAppNavHost_verifyBackNavigationNotShowOnHomeScreen(){
    val backText = composeTestRule.activity.getString(R.string.arrow_back)
    composeTestRule.onNodeWithContentDescription(backText).assertDoesNotExist()
}

