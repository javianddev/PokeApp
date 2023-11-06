package com.example.pokeapp.compose.home

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.pokeapp.compose.utils.currentGraph


@Composable
fun HomeScreen(navController: NavController, modifier: Modifier = Modifier){

    Column(){
        Text(text = currentGraph(navController) ?: "")
    }


}
