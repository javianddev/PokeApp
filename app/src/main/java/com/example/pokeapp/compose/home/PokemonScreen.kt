package com.example.pokeapp.compose.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material.icons.filled.Height
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.pokeapp.viewmodels.PokemonUiState
import com.example.pokeapp.viewmodels.PokemonViewModel
import com.example.pokeapp.R
import com.example.pokeapp.data.models.PokemonDetail
import com.example.pokeapp.remotedata.model.Stat
import com.example.pokeapp.remotedata.model.StatX
import com.example.pokeapp.remotedata.model.Type
import com.example.pokeapp.remotedata.model.TypeX
import com.example.pokeapp.utils.getColorType
import java.util.Locale

/*TODO
*  La imagen del pokemon se ve en toda la mitad, hacer como el del Pokemon Go mejor. Añadir las estadísticas*/
@Composable
fun PokemonScreen(navController: NavController, viewModel: PokemonViewModel = hiltViewModel(), modifier: Modifier = Modifier){

    val pokemonUiState = viewModel.pokemonUiState

    when (pokemonUiState){
        is PokemonUiState.Loading -> LoadingScreen(modifier = modifier.fillMaxSize())
        is PokemonUiState.Error -> ErrorScreen(modifier = modifier.fillMaxSize())
        is PokemonUiState.Success -> PokemonInfoScreen(modifier = modifier.fillMaxSize(), pokemon = pokemonUiState.pokemon)
    }

}

@Composable
fun PokemonInfoScreen(pokemon: PokemonDetail, modifier: Modifier = Modifier){

    Box(
        modifier = modifier
    ){
        PokemonImage(pokemon.imageUrl, pokemon.name)
        PokemonSimpleData(pokemon)
    }

}

@Composable
fun PokemonSimpleData(pokemon: PokemonDetail){
    Box(
        contentAlignment = Alignment.Center
    ){
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Text(
                text ="#${pokemon.id} ${pokemon.name.replaceFirstChar {
                    if (it.isLowerCase()) it.titlecase(
                        Locale.ROOT
                    ) else it.toString()
                }}",
                style = MaterialTheme.typography.titleMedium,
            )

            PokemonTypes(pokemon.types)
            PokemonFenotype(pokemon.height, pokemon.weight)
            
        }

    }
}

@Composable
fun PokemonFenotype(height: Int, weight: Int) {

    val weightToKgs = remember {
        Math.round(weight * 100f) / 1000f
    }
    val heightToMeters = remember {
        Math.round(height * 100f) / 1000f
    }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier
            .padding(dimensionResource(id = R.dimen.padding_medium))
            .fillMaxWidth()
    ){
        PokemonFenotypeItem(weightToKgs, Icons.Filled.FitnessCenter, "kg", Modifier.weight(1f))
        Spacer(
            modifier = Modifier
                .size(1.dp, 80.dp)
                .background(Color.LightGray)
        )
        PokemonFenotypeItem(heightToMeters,Icons.Filled.Height, "m", Modifier.weight(1f))
    }

}

@Composable
fun PokemonFenotypeItem(value:Float, icon: ImageVector, unit: String, modifier: Modifier = Modifier){

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ){
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = Color.Black
        )
        Text(
            text = "$value $unit",
            style = MaterialTheme.typography.labelLarge
        )
    }
}

@Composable
fun PokemonTypes(types: List<Type>) {

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_medium))
    ){
        for (type in types){
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = dimensionResource(id = R.dimen.padding_small))
                    .clip(CircleShape)
                    .background(getColorType(type))
                    .height(35.dp)
            ) {
                Text( text = type.type.name.replaceFirstChar {
                    if (it.isLowerCase()) it.titlecase(
                        Locale.ROOT
                    ) else it.toString()
                },
                    style = MaterialTheme.typography.labelLarge,
                    color = Color.White
                )
            }
        }
    }

}

@Composable
fun PokemonImage(imageUrl: String, name: String,modifier: Modifier = Modifier){
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier.padding(dimensionResource(id = R.dimen.padding_large))
    ){
        AsyncImage(
            model = ImageRequest.Builder(context = LocalContext.current)
                .data(imageUrl)
                .crossfade(true)
                .build(),
            error = painterResource(R.drawable.ic_broken_image),
            placeholder = painterResource(R.drawable.loading_img),
            contentDescription = name,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(200.dp)
        )
    }

}

@Composable
fun LoadingScreen(modifier: Modifier = Modifier){

}

@Composable
fun ErrorScreen(modifier: Modifier = Modifier){

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .padding(dimensionResource(id = R.dimen.padding_large))){
        Icon(
            imageVector = Icons.Filled.Error,
            contentDescription = null, //Decorativo
            modifier = Modifier.size(dimensionResource(id = R.dimen.icon_large))
        )
        Text(
            text = stringResource(id = R.string.error_poke_info),
            style = MaterialTheme.typography.labelLarge,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(vertical = dimensionResource(id = R.dimen.padding_medium))
        )
    }
}




@Preview(showBackground = true)
@Composable
fun ErrorScreenPreview(){
    ErrorScreen(Modifier.fillMaxSize())
}

@Preview(showBackground = true)
@Composable
fun PokemonInfoPreview(){

    val pokemon = PokemonDetail(
        id = 1,
        name="bulbasaur",
        imageUrl="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/1.png",
        stats=listOf(
            Stat(
                baseStat = 45,
                effort = 0,
                stat = StatX(
                    name = "hp",
                    url = "https://pokeapi.co/api/v2/stat/1/"
                )
            ),
            Stat(
                baseStat = 49,
                effort = 0,
                stat = StatX(
                    name = "attack",
                    url = "https://pokeapi.co/api/v2/stat/2/"
                )
            )
        ),
        types=listOf(
            Type(
                slot = 1,
                type = TypeX(
                    name = "grass",
                    url = "https://pokeapi.co/api/v2/type/12/"
                )
            ),
            Type(
                slot = 2,
                type = TypeX(
                    name = "poison",
                    url = "https://pokeapi.co/api/v2/type/4/"
                )
            )
        ),
        height=7,
        weight=69,
    )
    PokemonInfoScreen(pokemon, Modifier.fillMaxWidth())
}