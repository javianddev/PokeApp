package com.example.pokeapp.compose.games

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowRight
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.pokeapp.R
import com.example.pokeapp.compose.utils.messages


@Composable
fun TrivialScreen(modifier: Modifier = Modifier){

    var cont by remember { mutableStateOf(0) }

    Row(
        verticalAlignment = Alignment.Top,
        modifier = modifier
            .fillMaxSize()
            .padding(dimensionResource(id = R.dimen.padding_medium))
    ){
        Image(
            painter = painterResource(id = R.drawable.oak1),
            contentDescription = null, //decorativo
            contentScale = ContentScale.Fit
        )
        Card(

        ){
            Row(modifier = Modifier
                .padding(dimensionResource(id = R.dimen.padding_medium))
                .clickable(
                    indication = null, //con esto quitamos la decoración del clickable
                    interactionSource = remember{MutableInteractionSource()}
                ) {
                    if (cont != messages.size){
                        cont++
                    }
                }
            ){
                Text(
                    text = messages[cont],
                    style = MaterialTheme.typography.bodyMedium,
                )
                Icon(
                    imageVector = Icons.Filled.ArrowRight,
                    contentDescription = stringResource(id = R.string.next),
                    tint = Color.Black /*TODO Cambiar el color*/
                )
            }
        }
    }
}


@Preview(showSystemUi = true)
@Composable
fun KantoMedalPreview(){
    TrivialScreen()
}