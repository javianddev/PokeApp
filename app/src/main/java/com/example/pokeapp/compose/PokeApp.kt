package com.example.pokeapp.compose

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.pokeapp.R
import com.example.pokeapp.compose.navigation.PokeAppNavHost
import com.example.pokeapp.compose.navigation.barsScreens
import com.example.pokeapp.compose.navigation.bottomRoutes
import com.example.pokeapp.compose.navigation.routes
import com.example.pokeapp.compose.utils.currentRoute


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PokeApp(){

    val navController = rememberNavController()

    val barState = rememberSaveable { (mutableStateOf(true)) }

    var showSheet by rememberSaveable { mutableStateOf(false) }

    val currentRoute = currentRoute(navController)
    if (currentRoute != null) {
        barState.value = barsScreens.any { route ->
            currentRoute.startsWith(route) || currentRoute.startsWith("$route/")
        }
    }
    Log.i("PokeApp", "Valor para el modal --> $showSheet")
    val modifier = Modifier
        .fillMaxWidth()
        .padding(
            horizontal = dimensionResource(id = R.dimen.padding_medium)
        )

    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        topBar = {
            TopPokeAppBar( scrollBehavior = scrollBehavior, navController = navController, barState = barState){
                showSheet = true
            }
         },
        bottomBar = {
            BottomPokeAppBar( navController = navController, barState = barState )
        },
    ){innerPadding ->
        PokeAppNavHost(navController, modifier = modifier.padding(innerPadding))
        if (showSheet){
            PokeHelp {
                showSheet = false
            }
        }
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopPokeAppBar(scrollBehavior: TopAppBarScrollBehavior, navController: NavHostController, barState: MutableState<Boolean>, showSheet: () -> Unit){

    AnimatedVisibility(
        visible = barState.value,
        enter = slideInVertically(initialOffsetY = { -it }),
        exit = slideOutVertically(targetOffsetY = { -it }),
        content = {
            CenterAlignedTopAppBar(
                title = { Image(
                    painter = painterResource(id = R.drawable.poketitle),
                    contentDescription = stringResource(id = R.string.app_name)
                ) },
                scrollBehavior = scrollBehavior,
                navigationIcon = {
                    if (currentRoute(navController) !in bottomRoutes) {
                        IconButton(onClick = { navController.popBackStack() }) {
                            Icon(
                                imageVector = Icons.Filled.ArrowBack,
                                contentDescription = stringResource(id = R.string.arrow_back),
                                tint = MaterialTheme.colorScheme.onPrimary
                            )
                        }
                    }
                },
                actions = {
                    IconButton(onClick = { showSheet() }) {
                        Icon(
                            imageVector = Icons.Filled.Info,
                            contentDescription = stringResource(id = R.string.info),
                            tint = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary
                ),
            )
        }
    )
}

@Composable
fun BottomPokeAppBar(navController: NavHostController, barState: MutableState<Boolean>, modifier: Modifier = Modifier) {

    AnimatedVisibility(
        visible = barState.value,
        enter = slideInVertically(initialOffsetY = { it }),
        exit = slideOutVertically(targetOffsetY = { it }),
        content = {
            BottomNavigation(
                backgroundColor = MaterialTheme.colorScheme.primary,
                modifier = modifier.height(dimensionResource(id = R.dimen.bottom_bar_height))
            ) {

                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination

                routes.forEach { screen ->
                    BottomNavigationItem(
                        icon = {
                            Icon(
                                imageVector = screen.painter,
                                contentDescription = stringResource(id = screen.homeIcon),
                                tint = MaterialTheme.colorScheme.onPrimary
                            )
                        },
                        selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                        onClick = {
                            navController.navigate(screen.route) {//screen.route
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    )
                }
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PokeHelp(openBottomSheet: () -> Unit) {

    val modalBottomSheetState = rememberModalBottomSheetState()

    ModalBottomSheet(
        onDismissRequest = { openBottomSheet() },
        sheetState = modalBottomSheetState,
        dragHandle = { BottomSheetDefaults.DragHandle() },
    ) {
        val textModifier = Modifier.padding(vertical = dimensionResource(id = R.dimen.padding_small))
        Column(
            Modifier
                .fillMaxSize()
                .padding(dimensionResource(id = R.dimen.padding_medium))
        ){
            Text(text = "Acerca de PokeApp", style = MaterialTheme.typography.titleMedium, modifier = Modifier.padding(
                bottom = dimensionResource(id = R.dimen.padding_medium)))
            Text("En esta aplicación puedes consultar los pokémons de la región de Kanto a modo de Pokédex. " +
                    "Podrás ver datos como sus estadísticas, peso o altura.", style = MaterialTheme.typography.labelMedium, modifier = textModifier)
            Text("Tienes una ficha de entrenador que puedes editar. En ella encontrarás tus datos, medallas conseguidas " +
                "y equipo pokémon favorito. ", style = MaterialTheme.typography.labelMedium, modifier = textModifier)
            Text( "También podrás jugar a un trivial. Empezarás en la región de Kanto y a medida que vayas completándolos se irán " +
                    "desbloqueando los siguientes. Cuando ganes uno, las medallas de esa región de tu ficha de entrenador se desbloquearán. ",
                style = MaterialTheme.typography.labelMedium, modifier = textModifier)
            Text("Esta aplicación ha sido creada utilizando Kotlin, Jetpack Compose, Room y con la ayuda de PokeApi por Fco Javier Giráldez Delgado.",
                style = MaterialTheme.typography.labelMedium, modifier = textModifier)
        }
    }

}

/*
@Preview(name = "Vista Principal")
@Composable
fun PokeAppScreenPreview(){
    HomeScreen()
}
*/
/*
@OptIn(ExperimentalMaterial3Api::class)
@Preview(name="Barra superior")
@Composable
fun TopAppBarPreview(){

    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    val navController = rememberNavController()

    //TopPokeAppBar(scrollBehavior, navController)
}


@Preview(name="Barra inferior")
@Composable
fun BottomAppBarPreview(){

    val navController = rememberNavController()

    //BottomPokeAppBar(navController, Modifier.fillMaxWidth())
}
*/

