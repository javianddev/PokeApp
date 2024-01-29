package com.example.pokeapp.compose.games

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowRight
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.pokeapp.R
import com.example.pokeapp.compose.navigation.AppScreens
import com.example.pokeapp.viewmodels.GamesScreenViewModel

@Composable
fun GamesScreen(navController: NavController, viewModel:GamesScreenViewModel = hiltViewModel(), modifier: Modifier = Modifier){

    val gamesUiState by viewModel.uiState.collectAsState()

    LazyColumn(
        modifier = modifier.fillMaxWidth()
    ) {
        itemsIndexed(gamesUiState.regions) { index, region ->
            //Esto no se puede hacer en el ViewModel
            val (icon, contentDescription) = when {
                index == 0 || gamesUiState.regions.getOrNull(index - 1)?.medalAchieved == true ->
                    Icons.Filled.ArrowRight to R.string.next
                else ->
                    Icons.Filled.Lock to R.string.locked
            }

            Card(
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.background),
                modifier =
                //Dependiendo de si la región anterior está desbloqueada, podemos acceder a la actual (a no ser que sea la primera, que se accede siempre)
                    if(index == 0 || gamesUiState.regions.getOrNull(index - 1)?.medalAchieved == true){
                        Modifier
                            .clickable { navController.navigate(AppScreens.Trivial.route + "/${region.id}") }.testTag("trivial_$index")
                    } else{
                        Modifier
                    }
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .padding(dimensionResource(id = R.dimen.padding_medium))
                ) {
                    Text(stringResource(id = R.string.medals) + " ${region.name}")
                    Spacer(Modifier.weight(1f))
                    Icon(
                        imageVector = icon,
                        contentDescription = stringResource(id = contentDescription),
                        modifier = Modifier.size(dimensionResource(id = R.dimen.icon_medium))
                    )
                }
            }
        }
    }

}



@Preview("Juegos")
@Composable
fun GamesScreenPreview(){

    val navController = rememberNavController()
    GamesScreen(navController = navController)
}