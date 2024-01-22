package com.example.pokeapp

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import com.example.pokeapp.compose.navigation.AppScreens
import com.example.pokeapp.compose.PokeApp
import com.example.pokeapp.navigationTest.assertCurrentRouteName
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test


@HiltAndroidTest
class PokeAppNavigationTest {

    @get:Rule(order = 1)
    var hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 2)
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
            /*navController = TestNavHostController(LocalContext.current).apply {
                navigatorProvider.addNavigator(ComposeNavigator())
            }*/
    }

    //Comprobamos la ruta de inicio de la app
    @Test
    fun pokeAppNavHost_verifyStartDestination(){

        navController.assertCurrentRouteName(AppScreens.HomeScreen.route)
    }

    //Comprobamos que la ruta de inicio NO tiene flecha para volver atrás
    /*@Test
    fun pokeAppNavHost_verifyBackNavigationNotShowOnHomeScreen(){
        val backText = composeTestRule.activity.getString(R.string.arrow_back)
        composeTestRule.onNodeWithContentDescription(backText).assertDoesNotExist()
    }*/

    @Test
    fun pokeAppNavHost_clickOnePokemon_navigatesToPokemonScreen(){
        composeTestRule.onNodeWithTag("pokemonNav").performClick()
        navController.assertCurrentRouteName(AppScreens.PokemonScreen.route + "/{pokemon_id}")
    }

}
