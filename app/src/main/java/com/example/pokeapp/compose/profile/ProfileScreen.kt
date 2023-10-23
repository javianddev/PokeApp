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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import com.example.pokeapp.R
import com.example.pokeapp.utils.toFormattedString
import com.example.pokeapp.viewmodels.ProfileViewModel

@Composable
fun ProfileScreen(viewModel: ProfileViewModel = hiltViewModel(), modifier: Modifier = Modifier){

    val imageModifier = Modifier
        .padding(end = dimensionResource(id = R.dimen.padding_small))
        .size(dimensionResource(id = R.dimen.medal_image))
    val profileState by viewModel.uiState.collectAsState()


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
                        text = profileState.profile.name,
                        style= MaterialTheme.typography.bodyLarge
                    )
                    Text(
                        text=profileState.profile.birthdate.toFormattedString(),
                        style= MaterialTheme.typography.bodyLarge
                    )
                    Text(
                        text = profileState.profile.birthplace,
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
    val viewModel: ProfileViewModel = hiltViewModel()
    val modifier = Modifier.fillMaxWidth()
    ProfileScreen(viewModel, modifier)
}