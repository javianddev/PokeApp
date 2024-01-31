package com.example.pokeapp.navigationTest

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import com.example.pokeapp.HiltTestActivity
import com.example.pokeapp.R
import com.example.pokeapp.compose.navigation.AppScreens
import com.example.pokeapp.compose.PokeApp
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.runBlocking
import okhttp3.internal.wait
import org.junit.Before
import org.junit.Rule
import org.junit.Test

/*****TEST DE NAVEGACIÓN BÁSICA******/

@HiltAndroidTest
class PokeAppNavigationTest {

    @get:Rule(order = 0)
    var hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<HiltTestActivity>()
    private lateinit var navController: TestNavHostController

    @Before
     fun setUpPokeAppNavHost(){
        hiltRule.inject()
        composeTestRule.setContent {
            navController = TestNavHostController(composeTestRule.activity)
            navController.navigatorProvider.addNavigator(ComposeNavigator())
            PokeApp(navController)
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

    /*******************************************TRAINER***********************************************/

    @Test
    fun pokeAppNavHost_verifyNavigationToTrainerScreen(){
        val trainerText = composeTestRule.activity.getString(R.string.trainer_icon)
        composeTestRule.onNodeWithContentDescription(trainerText).performClick()
        navController.assertCurrentRouteName(AppScreens.TrainerScreen.route)
    }

    @Test
    fun pokeAppNavHost_verifyBackNavigationNotShowOnTrainerScreen(){
        val trainerText = composeTestRule.activity.getString(R.string.trainer_icon)
        composeTestRule.onNodeWithContentDescription(trainerText).performClick()
        val backText = composeTestRule.activity.getString(R.string.arrow_back)
        composeTestRule.onNodeWithContentDescription(backText).assertDoesNotExist()
    }

    @Test
    fun pokeAppNavHost_verifyNavigationToEditTrainerScreen(){
        val trainerText = composeTestRule.activity.getString(R.string.trainer_icon)
        composeTestRule.onNodeWithContentDescription(trainerText).performClick()
        composeTestRule.onNodeWithTag("edit_trainer").performClick()
        navController.assertCurrentRouteName(AppScreens.EditTrainer.route)
    }

    @Test
    fun pokeAppNavHost_verifyReturnToTrainerScreenFromEditTrainerScreen(){
        val trainerText = composeTestRule.activity.getString(R.string.trainer_icon)
        composeTestRule.onNodeWithContentDescription(trainerText).performClick()
        composeTestRule.onNodeWithTag("edit_trainer").performClick()
        composeTestRule.onNodeWithTag("save_data").performClick()
        navController.assertCurrentRouteName(AppScreens.TrainerScreen.route)

    }

    @Test
    fun pokeAppNavHost_verifyBackNavigationShowsOnEditTrainerScreen(){
        val trainerText = composeTestRule.activity.getString(R.string.trainer_icon)
        composeTestRule.onNodeWithContentDescription(trainerText).performClick()
        composeTestRule.onNodeWithTag("edit_trainer").performClick()
        val backText = composeTestRule.activity.getString(R.string.arrow_back)
        composeTestRule.onNodeWithContentDescription(backText).assertExists()
    }

    /*******************************************GAMES***********************************************/

    @Test
    fun pokeAppNavHost_verifyNavigationToGamesScreen(){
        val gamesText = composeTestRule.activity.getString(R.string.options_icon)
        composeTestRule.onNodeWithContentDescription(gamesText).performClick()
        navController.assertCurrentRouteName(AppScreens.GamesScreen.route)
    }

    @Test
    fun pokeAppNavHost_verifyNavigationToTrivialScreen(){
        val gamesText = composeTestRule.activity.getString(R.string.options_icon)
        composeTestRule.onNodeWithContentDescription(gamesText).performClick()
        composeTestRule.onNodeWithTag("trivial_0").performClick()
        navController.assertCurrentRouteName(AppScreens.Trivial.route + "/{region_id}")
    }

    /*******************************************HOME***********************************************/

    @Test
    fun pokeAppNavHost_verifyNavigationToHomeScreen(){
        val homeTest = composeTestRule.activity.getString(R.string.home_icon)
        val trainerText = composeTestRule.activity.getString(R.string.options_icon)
        composeTestRule.onNodeWithContentDescription(trainerText).performClick()
        composeTestRule.onNodeWithContentDescription(homeTest).performClick()
        navController.assertCurrentRouteName(AppScreens.HomeScreen.route)
    }

    @Test
    fun pokeAppNavHost_verifyNavigationToPokemonScreen() = runBlocking{
        Thread.sleep(2000) //Esperamos a que cargue la API
        composeTestRule.onNodeWithTag("pokemon1").performClick()
        navController.assertCurrentRouteName(AppScreens.PokemonScreen.route + "/{pokemon_id}")
    }

    @Test
    fun pokeAppNavHost_verifyBackNavigationShowsOnPokemonScreen(){
        Thread.sleep(2000) //Esperamos a que cargue la API
        composeTestRule.onNodeWithTag("pokemon1").performClick()
        val backText = composeTestRule.activity.getString(R.string.arrow_back)
        composeTestRule.onNodeWithContentDescription(backText).assertExists()
    }

    @Test
    fun pokeAppNavHost_verifyBackNavigationToHomeScreen(){
        Thread.sleep(2000) //Esperamos a que cargue la API
        composeTestRule.onNodeWithTag("pokemon1").performClick()
        val backText = composeTestRule.activity.getString(R.string.arrow_back)
        composeTestRule.onNodeWithContentDescription(backText).performClick()
        navController.assertCurrentRouteName(AppScreens.HomeScreen.route)
    }

}
