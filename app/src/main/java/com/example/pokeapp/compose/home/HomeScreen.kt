package com.example.pokeapp.compose.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.example.pokeapp.R
import com.example.pokeapp.remotedata.model.Pokemon
import com.example.pokeapp.viewmodels.HomeViewModel


@Composable
fun HomeScreen(navController: NavController, viewModel: HomeViewModel = hiltViewModel(), modifier: Modifier = Modifier){

    val pokemons = viewModel.pokemonsPager.collectAsLazyPagingItems()

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(dimensionResource(id = R.dimen.padding_small)),
        verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_small))
    ){ //Lo óptimo sería usar LazyVerticalGrid, pero a día de hoy no está implementado con Paging3
        items(pokemons){pokemon ->
            if (pokemon != null) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth(0.5f) //50% del ancho
                        .aspectRatio(1f) //Cuadrado
                ) {
                    PokemonCard(pokemon)
                }
            }
        }
        when (pokemons.loadState.append){
            is LoadState.NotLoading -> Unit
            LoadState.Loading ->
                item{
                    LoadingPokemons() /*TODO Hacer LoadingPokemons*/
                }
            is LoadState.Error -> {
                item {
                    ErrorPokemons() /*TODO* Hacer ErrorPokemons*/
                }
            }
        }

        /*TODO Seguir esto*/

        when (pokemons.loadState.refresh){

        }
    }

}

@Composable
fun PokemonCard(pokemon: Pokemon){

    Card(
        elevation = CardDefaults.cardElevation(dimensionResource(id = R.dimen.default_card_elevation)),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.background),
        modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_medium))
    ){
        Text(text = pokemon.name)
    }
}
