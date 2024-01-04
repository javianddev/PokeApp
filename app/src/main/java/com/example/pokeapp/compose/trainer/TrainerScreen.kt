package com.example.pokeapp.compose.trainer


import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ColorMatrix
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.pokeapp.R
import com.example.pokeapp.compose.navigation.AppScreens
import com.example.pokeapp.data.models.Pokemon
import com.example.pokeapp.data.models.Region
import com.example.pokeapp.data.models.Trainer
import com.example.pokeapp.utils.toFormattedString
import com.example.pokeapp.viewmodels.TrainerViewModel

@Composable
fun TrainerScreen(modifier: Modifier = Modifier, viewModel: TrainerViewModel = hiltViewModel(), navController: NavController){

    val trainerState by viewModel.uiState.collectAsState() /*TODO Hay que conseguir que se actualice la información automáticamente al editar el perfil*/
    val regionMedal = viewModel.regionMedal

    LazyColumn(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            TrainerInfo(trainerState.trainer, navController)
        }

        item {
            MedalsCard(regionMedal, /*trainerState.medals,*/ modifier = modifier)
        }

        item {
            Text(
                text = "Equipo Pokémon",
                style = MaterialTheme.typography.titleLarge
            )
            TeamCard(trainerState.pokemonTeam, modifier = modifier)
        }

    }
}

@Composable
fun TrainerInfo(trainer: Trainer, navController: NavController){
    Text(
        text = stringResource(id = R.string.trainer_info),
        style = MaterialTheme.typography.titleLarge
    )
    Card(
        elevation = CardDefaults.cardElevation(dimensionResource(id = R.dimen.default_card_elevation)),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.background),
        modifier = Modifier.padding(vertical = dimensionResource(id = R.dimen.padding_medium))
    ) {
        Row(
            modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_small))
        ) {
            Image(
                painter = painterResource(id = R.drawable.pokemon_trainer),
                contentDescription = stringResource(id = R.string.default_trainer_image),
                contentScale = ContentScale.Fit,
                modifier = Modifier.size(150.dp)
            )
            Column(
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = "Nombre de entrenador: ${trainer.name}",
                    style = MaterialTheme.typography.bodySmall
                )
                Text(
                    text = "Lugar de nacimiento: ${trainer.birthplace}",
                    style = MaterialTheme.typography.bodySmall
                )
                Text(
                    text = "Fecha de nacimiento: ${trainer.birthdate.toFormattedString()}",
                    style = MaterialTheme.typography.bodySmall
                )
                Spacer(modifier = Modifier.weight(1f))
                Button(
                    onClick = { navController.navigate(AppScreens.EditTrainer.route) },
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
                    modifier = Modifier.padding(top = dimensionResource(id = R.dimen.padding_small))
                ) {
                    Text(
                        text = stringResource(id = R.string.edit_trainer),
                        style = MaterialTheme.typography.labelSmall
                    )
                }
            }
        }
    }
    Text(
        text = "Medallas",
        style = MaterialTheme.typography.titleLarge
    )
}

@Composable
fun MedalsCard(regionMedal: List<Pair<Region, List<Int>>>, /*medals: List<Medal>,*/ modifier: Modifier = Modifier){

    val imageModifier = Modifier
        .padding(end = dimensionResource(id = R.dimen.padding_small))
        .size(dimensionResource(id = R.dimen.medal_image))

    Card(
        elevation = CardDefaults.cardElevation(dimensionResource(id = R.dimen.default_card_elevation)),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.background),
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = dimensionResource(id = R.dimen.padding_medium))
    ){
        Column(verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.vertical_space)), modifier = Modifier.padding(
            dimensionResource(id = R.dimen.padding_small))){
            regionMedal.forEach{
                Text(
                    text = "${stringResource(id = R.string.region_medal)} ${it.first.name}",
                    style = MaterialTheme.typography.titleMedium
                )

                val filter =  if (!it.first.medalAchieved){
                    ColorFilter.colorMatrix(ColorMatrix().apply { setToSaturation(0f) })
                }else{
                    null
                }
                Row(horizontalArrangement = Arrangement.SpaceEvenly, modifier = Modifier.fillMaxWidth()){
                    it.second.forEach{medal ->
                        Image(
                            painter = painterResource(id = medal),
                            contentDescription = stringResource(id = R.string.medal_achieved),
                            colorFilter = filter,
                            modifier = imageModifier
                        )
                    }
                    /*items(medals.filter{ it.id == region.id}){medal ->
                        Image( /*TODO Si averigüo la mejor manera de enlazar los drawables con la BBDD*/
                            painter = painterResource(id = medal.image),
                            contentDescription = medal.medalName,
                            modifier = imageModifier
                        )
                    }*/
                }
            }
        }
    }

}

@Composable
fun TeamCard(pokemonTeam: List<Pokemon>, modifier: Modifier = Modifier) {

    val imageModifier = Modifier
        .size(dimensionResource(id = R.dimen.medal_image))

    Card(elevation = CardDefaults.cardElevation(dimensionResource(id = R.dimen.default_card_elevation)),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.background),
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = dimensionResource(id = R.dimen.padding_medium))){
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly, modifier = Modifier.fillMaxWidth()
        ){
            for (i in 0..5){
                if (i < pokemonTeam.size){
                    AsyncImage(
                        model = ImageRequest.Builder(context = LocalContext.current)
                            .data(pokemonTeam[i].imageUrl)
                            .crossfade(true)
                            .build(),
                        error = painterResource(R.drawable.ic_broken_image),
                        placeholder = painterResource(R.drawable.loading_img),
                        contentDescription = pokemonTeam[i].name,
                        contentScale = ContentScale.Crop,
                        modifier = imageModifier
                    )
                } else {
                    IconButton(onClick = { /*TODO SALE UN MODAL CON UN SEARCHBAR DE MATERIAL EN EL QUE SALEN TODOS LOS POKIMON*/ }, modifier = Modifier.padding(
                        dimensionResource(id = R.dimen.padding_small))) {
                        Icon(
                            imageVector = Icons.Filled.AddCircle,
                            contentDescription = stringResource(id = R.string.add_pokemon),
                            tint = Color.Gray,
                            modifier = imageModifier
                        )
                    }
                }
            }
        }
    }

}
/*
@Preview("Mi perfil")
@Composable
fun ProfileScreenPreview(){
    TrainerScreen()
}*/