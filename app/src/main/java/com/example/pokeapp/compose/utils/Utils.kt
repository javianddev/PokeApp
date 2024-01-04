package com.example.pokeapp.compose.utils

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Error
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.pokeapp.R
import kotlinx.coroutines.delay

@Composable
fun currentRoute(navController: NavController): String? {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    return navBackStackEntry?.destination?.route
}

@Composable
fun currentGraph(navController: NavController): String? {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    return navBackStackEntry?.destination?.navigatorName
}

@Composable
fun TypeWriterText(text: String, textStyle: TextStyle){

    var displayText by remember { mutableStateOf("") }

    LaunchedEffect(key1 = text){
        text.forEachIndexed { charIndex, _ ->
            displayText = text.substring(
                startIndex = 0,
                endIndex = charIndex + 1
            )
            delay(50)
        }
    }

    Text(
        text = displayText,
        style = textStyle,
    )
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
