package com.example.pokeapp.compose.options

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowRight
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import com.example.pokeapp.compose.navigation.optionLinks

@Composable
fun OptionsScreen(navController: NavController, modifier: Modifier = Modifier){

    LazyColumn(
        modifier = modifier.fillMaxWidth()
    ){
        items(optionLinks){option ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_medium))
            ){
                Text(stringResource(id = option.text))
                Spacer(Modifier.weight(1f))
                IconButton(onClick = { navController.navigate(option.route) }) {
                    Icon(
                        imageVector = Icons.Filled.ArrowRight,
                        contentDescription = stringResource(id = R.string.arrow_right),
                        modifier = Modifier.size(dimensionResource(id = R.dimen.icon_medium))
                    )
                }
            }
            Divider()
        }

    }

}



@Preview("Juegos")
@Composable
fun GamesScreenPreview(){

    val navController = rememberNavController()
    OptionsScreen(navController)
}