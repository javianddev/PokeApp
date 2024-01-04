package com.example.pokeapp.compose.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material.icons.filled.Height
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
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
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.pokeapp.R
import com.example.pokeapp.compose.utils.ErrorScreen
import com.example.pokeapp.data.models.PokemonDetail
import com.example.pokeapp.remotedata.model.Stat
import com.example.pokeapp.remotedata.model.StatX
import com.example.pokeapp.remotedata.model.Type
import com.example.pokeapp.remotedata.model.TypeX
import com.example.pokeapp.utils.getStatColor
import com.example.pokeapp.utils.getTypeColor
import com.example.pokeapp.viewmodels.PokemonUiState
import com.example.pokeapp.viewmodels.PokemonViewModel
import java.util.Locale
import kotlin.math.roundToInt

/*TODO
*  Hacer las estadísticas. Poner una imagen dinámica de fondo según el tipo*/
@Composable
fun PokemonScreen(viewModel: PokemonViewModel = hiltViewModel(), modifier: Modifier = Modifier){

    when (val pokemonUiState = viewModel.pokemonUiState){
        is PokemonUiState.Loading -> LoadingScreen(modifier = modifier.fillMaxSize())
        is PokemonUiState.Error -> ErrorScreen(modifier = modifier.fillMaxSize())
        is PokemonUiState.Success -> PokemonInfoScreen(modifier = modifier.fillMaxWidth(), pokemon = pokemonUiState.pokemon)
    }

}

@Composable
fun PokemonInfoScreen(pokemon: PokemonDetail, modifier: Modifier = Modifier){

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .verticalScroll(
                state = rememberScrollState(),
                enabled = true
            )
            .padding(vertical = dimensionResource(id = R.dimen.padding_medium))
    ){
        PokemonImage(pokemon.imageUrl, pokemon.name)
        PokemonData(pokemon)
    }

}

@Composable
fun PokemonData(pokemon: PokemonDetail){
    Card(
        elevation = CardDefaults.cardElevation(dimensionResource(id = R.dimen.default_card_elevation)),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.background),
        shape = RoundedCornerShape(
            bottomStartPercent = 6,
            bottomEndPercent = 6
        )
    ){
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .padding(dimensionResource(id = R.dimen.padding_medium))
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
            PokemonStats(pokemon.stats)
        }

    }
}

@Composable
fun PokemonFenotype(height: Int, weight: Int) {

    val weightToKgs = remember {
        (weight * 100f).roundToInt() / 1000f
    }
    val heightToMeters = remember {
        (height * 100f).roundToInt() / 1000f
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
        types.forEach{ type ->
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = dimensionResource(id = R.dimen.padding_small))
                    .clip(CircleShape)
                    .background(getTypeColor(type))
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
fun PokemonImage(imageUrl: String, name: String, modifier: Modifier = Modifier){

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxWidth()
    ){
        Image(
            painter = painterResource(id = R.drawable.bosque),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .clip(shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
        )
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
fun PokemonStats(stats: List<Stat>, modifier: Modifier = Modifier){

    Column(
        modifier = modifier.padding(dimensionResource(id = R.dimen.padding_small))
    ){
        Text(
            text = stringResource(id = R.string.stats),
            style = MaterialTheme.typography.titleLarge
        )
        stats.forEach{ stat ->
            PokemonBarStat(stat.baseStat, stat.stat)
        }
    }

}

@Composable
fun PokemonBarStat(baseStat: Int, stat: StatX){
    val progress = baseStat/100f


    Text(
        text = "${stat.name.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.ROOT) else it.toString() }} - $baseStat",
        style = MaterialTheme.typography.labelMedium,
        modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_small))
    )

    LinearProgressIndicator(
        progress = progress,
        color = getStatColor(baseStat),
        strokeCap = StrokeCap.Round,
        modifier = Modifier
            .fillMaxWidth()
            .height(12.dp)
    )
}

@Composable
fun LoadingScreen(modifier: Modifier = Modifier){

}


/* ------------------------ PREVIEWS ----------------------------*/

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

@Preview(showBackground = true)
@Composable
fun PokemonImagePreview(){
    PokemonImage("a", "bulbasaur", Modifier)
}