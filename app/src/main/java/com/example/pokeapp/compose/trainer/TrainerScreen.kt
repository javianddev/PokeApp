package com.example.pokeapp.compose.trainer


import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.pokeapp.R
import com.example.pokeapp.compose.navigation.AppScreens
import com.example.pokeapp.data.models.Region
import com.example.pokeapp.utils.toFormattedString
import com.example.pokeapp.viewmodels.TrainerViewModel

@Composable
fun TrainerScreen(modifier: Modifier = Modifier, viewModel: TrainerViewModel = hiltViewModel(), navController: NavController){

    val trainerState by viewModel.uiState.collectAsState()

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.verticalScroll(rememberScrollState())
    ){
        Text(
            text = "Ficha de entrenador",
            style = MaterialTheme.typography.titleLarge
        )
        Card(
            elevation = CardDefaults.cardElevation(dimensionResource(id = R.dimen.default_card_elevation)),
            modifier = Modifier.padding(vertical = dimensionResource(id = R.dimen.padding_medium))
        ){
            Row(
                modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_small))
            ){
                Image(
                    painter = painterResource(id = R.drawable.pokemon_trainer),
                    contentDescription = stringResource(id = R.string.default_trainer_image),
                    contentScale = ContentScale.Fit,
                    modifier = Modifier.size(150.dp)
                )
                Column(
                    horizontalAlignment = Alignment.Start
                ){
                    Text(
                        text = "Nombre de entrenador: ${trainerState.trainer.name}",
                        style = MaterialTheme.typography.bodySmall
                    )
                    Text(
                        text = "Lugar de nacimiento: ${trainerState.trainer.birthplace}",
                        style = MaterialTheme.typography.bodySmall
                    )
                    Text(
                        text = "Fecha de nacimiento: ${trainerState.trainer.birthdate.toFormattedString()}",
                        style = MaterialTheme.typography.bodySmall
                    )
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

        MedalsCard(trainerState.regions, modifier = modifier)

        Text(
            text = "Equipo Pokémon",
            style = MaterialTheme.typography.titleLarge
        )

        //TeamCard(profileState, modifier = modifier)
    }
}

@Composable
fun MedalsCard(regions: List<Region>, modifier: Modifier = Modifier){

    val imageModifier = Modifier
        .padding(end = dimensionResource(id = R.dimen.padding_small))
        .size(dimensionResource(id = R.dimen.medal_image))

    Card(
        elevation = CardDefaults.cardElevation(dimensionResource(id = R.dimen.default_card_elevation)),
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = dimensionResource(id = R.dimen.padding_medium))
    ){
        /*TODO*/
        LazyColumn(verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.vertical_space))){
            items(regions, key = {region -> region.id}){region ->
                Text(
                    text = "Medallas de ${region.name}",
                    style = MaterialTheme.typography.titleMedium
                )
                /*LazyRow(horizontalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.horizontal_space))){
                    items(){medal ->
                        TODO HAY QUE PONER LAS MEDALLAS POR REGIÓN
                    }
                }*/
            }
        }
    }

}

/*@Composable
fun TeamCard(profileState: ProfileUiState, modifier: Modifier) {
}*/
/*
@Preview("Mi perfil")
@Composable
fun ProfileScreenPreview(){
    TrainerScreen()
}*/