package com.example.pokeapp.compose

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.VideogameAsset
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.pokeapp.R
import com.example.pokeapp.compose.home.HomeScreen
import com.example.pokeapp.compose.navigation.AppScreens
import com.example.pokeapp.compose.navigation.PokeAppNavHost


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PokeApp(){

    val navController = rememberNavController()

    val modifier = Modifier
        .fillMaxWidth()
        .padding(
            horizontal = dimensionResource(id = R.dimen.padding_medium),
            vertical = dimensionResource(id = R.dimen.padding_small)
        )

    //Diferentes comportamientos de desplazamiento para la barra superior, no sé cuál poner aún
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    //val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
    //val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()

    Scaffold(
        topBar = { TopPokeAppBar( scrollBehavior = scrollBehavior, navController = navController) },
        bottomBar = { BottomPokeAppBar( navController = navController ) },
    ){innerPadding ->
        PokeAppNavHost(navController, modifier = modifier.padding(innerPadding))
    }

}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopPokeAppBar(scrollBehavior: TopAppBarScrollBehavior, navController: NavHostController, modifier: Modifier = Modifier){

    CenterAlignedTopAppBar(
        title = { Image(
            painter = painterResource(id = R.drawable.poketitle),
            contentDescription = stringResource(id = R.string.app_name)
        ) },
        scrollBehavior = scrollBehavior,
        navigationIcon = {

            if (!currentRoute(navController).equals(AppScreens.HomeScreen.route)) {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = stringResource(id = R.string.arrow_back),
                        tint = MaterialTheme.colorScheme.onPrimary
                    )
                }
            }
        },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary
        ),
    )
}

@Composable
fun BottomPokeAppBar(navController: NavHostController, modifier: Modifier = Modifier){

    val iconModifier: Modifier = Modifier.size(dimensionResource(id = R.dimen.icon_medium))
    val currentRoute = currentRoute(navController)

    BottomAppBar(
        actions = {
            Row(
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically,
                modifier = modifier.fillMaxWidth()
            ){
                IconButton(onClick = {
                    if (currentRoute != AppScreens.ProfileScreen.route)
                        navController.navigate(AppScreens.ProfileScreen.route)
                }) {
                    Icon(
                        imageVector = Icons.Filled.AccountCircle,
                        contentDescription = stringResource(id = R.string.account_icon),
                        modifier = iconModifier
                    )
                }
                IconButton(onClick = { navController.navigate(AppScreens.HomeScreen.route) }){
                    Icon(
                        imageVector = Icons.Filled.Home,
                        contentDescription = stringResource(id = R.string.home_icon),
                        modifier = iconModifier
                    )
                }
                IconButton(onClick = { navController.navigate(AppScreens.GamesScreen.route) }){
                    Icon(
                        imageVector = Icons.Filled.VideogameAsset,
                        contentDescription = stringResource(id = R.string.game_icon),
                        modifier = iconModifier
                    )
                }
            }
        },
        containerColor = MaterialTheme.colorScheme.primary,
        modifier = Modifier.height(dimensionResource(id = R.dimen.bottom_bar_height))
    )

}

@Composable
private fun currentRoute(navController: NavHostController): String? {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    return navBackStackEntry?.destination?.route
}

@Preview(name = "Vista Principal")
@Composable
fun PokeAppScreenPreview(){

    val navController = rememberNavController()

    HomeScreen(navController)
}

@Preview(name="Barra inferior")
@Composable
fun BottomAppBarPreview(){

    val navController = rememberNavController()

    BottomPokeAppBar(navController, Modifier.fillMaxWidth())
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(name="Barra superior")
@Composable
fun TopAppBarPreview(){

    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    val navController = rememberNavController()

    TopPokeAppBar(scrollBehavior, navController)
}

