package com.example.pokeapp.compose.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Error
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.pokeapp.R
import com.example.pokeapp.compose.navigation.AppScreens
import com.example.pokeapp.data.models.Pokemon
import com.example.pokeapp.remotedata.model.PokemonEntry
import com.example.pokeapp.viewmodels.HomeViewModel
import java.util.Locale

@Composable
fun HomeScreen(navController: NavController, viewModel: HomeViewModel = hiltViewModel(), modifier: Modifier = Modifier){

    val pokemons = viewModel.pokemons.collectAsLazyPagingItems()

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = modifier
            .fillMaxSize()
    ) { /*TODO Cuando realizamos la recarga de datos, nos manda al principio
                            de nuevo, habría que mirar eso también
                            Según parece esto es un bug, debería de hacerlo solo --> https://stackoverflow.com/questions/68611320/remember-lazycolumn-scroll-position-jetpack-compose/75084854#75084854*/
        items(pokemons.itemCount){
            if (pokemons[it] != null) {
                pokemons[it]?.let { it1 -> PokemonCard(it1, navController) }
            }
        }
        when (pokemons.loadState.append){
            is LoadState.NotLoading -> Unit
            LoadState.Loading ->
                item(span = { GridItemSpan(this.maxLineSpan) }){
                    LoadingPokemons()
                }
            is LoadState.Error -> {
                item(span = { GridItemSpan(this.maxLineSpan) }) {
                    ErrorPokemons(pokemons) //Este sale cuando se ha cortado la carga a la mitad
                }
            }
        }

        when (pokemons.loadState.refresh){
            is LoadState.NotLoading -> Unit
            LoadState.Loading ->
                item(span = { GridItemSpan(this.maxLineSpan) }){ //Este es el círculo que sale nada más entrar a la Aplicación, el que carga desde el principio

                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(dimensionResource(id = R.dimen.padding_medium))
                    ){
                        CircularProgressIndicator()
                    }
                }
            is LoadState.Error -> {
                item(span = { GridItemSpan(this.maxLineSpan) }) {
                    ErrorPokemons(pokemons) //Este sale cuando no ha cargado desde el principio
                }
            }
        }
    }

}

@Composable
fun PokemonCard(pokemon: PokemonEntry, navController: NavController){

    Card(
        elevation = CardDefaults.cardElevation(dimensionResource(id = R.dimen.default_card_elevation)),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.background),
        modifier = Modifier
            .padding(dimensionResource(id = R.dimen.padding_medium))
            .fillMaxWidth()
            .clickable { navController.navigate(AppScreens.PokemonScreen.route + "/${pokemon.id}") }
    ){
        Column(
            verticalArrangement = Arrangement.SpaceAround,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_medium))
        ){
            AsyncImage(
                model = ImageRequest.Builder(context = LocalContext.current)
                    .data(pokemon.imageUrl)
                    .crossfade(true)
                    .build(),
                error = painterResource(R.drawable.ic_broken_image),
                placeholder = painterResource(R.drawable.loading_img),
                contentDescription = pokemon.name,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(120.dp)
            )
            Text(
                text = "${pokemon.id}. ${pokemon.name.replaceFirstChar {
                    if (it.isLowerCase()) it.titlecase(
                        Locale.ROOT
                    ) else it.toString()
                }}", //se podía utilizar capitalize(Locale.Root) pero está deprecated, porque queda más limpio...
                style = MaterialTheme.typography.titleSmall
            )
        }

    }
}

@Composable
fun LoadingPokemons(){
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        contentAlignment = Alignment.Center
    ){
        CircularProgressIndicator(
            modifier = Modifier
                .size(width = 42.dp, height = 42.dp)
                .padding(dimensionResource(id = R.dimen.padding_small))
        )
    }
}

@Composable
fun ErrorPokemons(pokemons: LazyPagingItems<PokemonEntry>) {
    Card(
        elevation = CardDefaults.cardElevation(dimensionResource(id = R.dimen.default_card_elevation)),
        modifier = Modifier
            .padding(dimensionResource(id = R.dimen.padding_small))
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.error)
                .padding(8.dp)
        ) {
            Icon(
                imageVector = Icons.Filled.Error,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.background,
                modifier = Modifier
                    .clip(CircleShape)
                    .size(width = 42.dp, height = 42.dp)
            )
            Text(
                color = Color.White,
                text = stringResource(id = R.string.reload),
                fontSize = 16.sp,
                modifier = Modifier
                    .padding(start = 12.dp)
                    .align(CenterVertically)
                    .clickable { pokemons.refresh() }
            )
        }
    }
}
