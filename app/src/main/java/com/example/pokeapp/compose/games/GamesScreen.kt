package com.example.pokeapp.compose.games

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowRight
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.pokeapp.R
import com.example.pokeapp.compose.navigation.gameLinks

@Composable
fun GamesScreen(navController: NavController, modifier: Modifier = Modifier){

    LazyColumn(
        modifier = modifier.fillMaxWidth()
    ) {
        items(gameLinks) { game ->
            Card(
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.background),
                modifier = Modifier
                    .clickable { navController.navigate(game.route) }
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .padding(dimensionResource(id = R.dimen.padding_medium))
                ) {
                    Text(stringResource(id = game.text))
                    Spacer(Modifier.weight(1f))
                    Icon(
                        imageVector = Icons.Filled.ArrowRight,
                        contentDescription = stringResource(id = R.string.arrow_right),
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
    GamesScreen(navController)
}