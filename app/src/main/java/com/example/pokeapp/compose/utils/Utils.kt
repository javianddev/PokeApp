package com.example.pokeapp.compose.utils

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.TextStyle
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
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


