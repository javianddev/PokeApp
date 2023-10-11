package com.example.pokeapp.compose.profile


import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.example.pokeapp.R

@Composable
fun ProfileScreen(modifier: Modifier = Modifier){

    val imageModifier = Modifier
        .padding(end = dimensionResource(id = R.dimen.padding_small))
        .size(dimensionResource(id = R.dimen.medal_image))


    Card(
        modifier = modifier
            .fillMaxSize()
            .padding(dimensionResource(id = R.dimen.padding_medium)),
        elevation = CardDefaults.cardElevation(dimensionResource(id = R.dimen.default_card_elevation))
    ){
        Column(
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_medium))
        ){
            Row(){
                Image(
                    painter = painterResource(id = R.drawable.pokemon_trainer),
                    contentDescription = stringResource(id = R.string.default_trainer_image),
                    contentScale = ContentScale.Fit,
                    modifier = Modifier.size(dimensionResource(id = R.dimen.profile_image))
                )
                Column(modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_small))){
                    Text(
                        text = "${stringResource(id = R.string.trainer_name)}: ${stringResource(id = R.string.default_trainer_name)}",
                        style= MaterialTheme.typography.bodyLarge
                    )
                    Text(
                        text="${stringResource(id = R.string.born_date)}: ${stringResource(id = R.string.default_born_date)}",
                        style= MaterialTheme.typography.bodyLarge
                    )
                    Text(
                        text = "${stringResource(id = R.string.born_place)}: ${stringResource(id = R.string.default_born_place)}",
                        style= MaterialTheme.typography.bodyLarge
                    )
                    Spacer(Modifier.weight(1f))
                    Row(){
                        Image(
                            painter = painterResource(id = R.drawable.medalla_faro),
                            contentDescription = stringResource(id = R.string.default_medal_image),
                            contentScale = ContentScale.Fit,
                            modifier = imageModifier
                        )
                        Image(
                            painter = painterResource(id = R.drawable.medalla_cienaga),
                            contentDescription = stringResource(id = R.string.default_medal_image),
                            contentScale = ContentScale.Fit,
                            modifier = imageModifier
                        )
                        Image(
                            painter = painterResource(id = R.drawable.medalla_mina),
                            contentDescription = stringResource(id = R.string.default_medal_image),
                            contentScale = ContentScale.Fit,
                            modifier = imageModifier
                        )
                    }
                }
            }
        }
    }
}


@Preview("Mi perfil")
@Composable
fun ProfileScreenPreview(){
    val navController = rememberNavController()
    val modifier = Modifier.fillMaxWidth()
    ProfileScreen(modifier)
}