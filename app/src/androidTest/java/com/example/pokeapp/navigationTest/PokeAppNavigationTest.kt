package com.example.pokeapp.navigationTest

import androidx.activity.ComponentActivity
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsOn
import androidx.compose.ui.test.junit4.ComposeTestRule
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.pokeapp.MainActivity
import com.example.pokeapp.compose.navigation.AppScreens
import com.example.pokeapp.R
import com.example.pokeapp.compose.PokeApp
import com.example.pokeapp.compose.navigation.PokeAppNavHost
import dagger.hilt.android.testing.CustomTestApplication
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class PokeAppNavigationTest {


    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    private lateinit var navController: TestNavHostController

    @Before
     fun setUpPokeAppNavHost(){
        composeTestRule.setContent{
            navController = TestNavHostController(LocalContext.current).apply {
                navigatorProvider.addNavigator(ComposeNavigator())
            }
            PokeAppNavHost(navController = navController)
        }
    }

    //Comprobamos la ruta de inicio de la app
    @Test
    fun pokeAppNavHost_verifyStartDestination() = runTest {
        Thread.sleep(5000)
        navController.assertCurrentRouteName(AppScreens.HomeScreen.route)
    }

    //Comprobamos que la ruta de inicio NO tiene flecha para volver atrás
    @Test
    fun pokeAppNavHost_verifyBackNavigationNotShowOnHomeScreen(){
        val backText = composeTestRule.activity.getString(R.string.arrow_back)
        composeTestRule.onNodeWithContentDescription(backText).assertDoesNotExist()
    }

    @Test
    fun pokeAppNavHost_clickOnePokemon_navigatesToPokemonScreen(){
        composeTestRule.onNodeWithTag("pokemonNav").performClick()
        navController.assertCurrentRouteName(AppScreens.PokemonScreen.route + "/{pokemon_id}")
    }

}
