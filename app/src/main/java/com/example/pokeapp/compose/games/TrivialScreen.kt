package com.example.pokeapp.compose.games

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.pokeapp.R
import com.example.pokeapp.compose.utils.TrivialStatus
import com.example.pokeapp.compose.utils.TypeWriterText
import com.example.pokeapp.data.models.Question
import com.example.pokeapp.data.models.Solution
import com.example.pokeapp.utils.Constants
import com.example.pokeapp.viewmodels.TrivialViewModel
import kotlin.reflect.KFunction2


@Composable
fun TrivialScreen(modifier: Modifier = Modifier, viewModel: TrivialViewModel = hiltViewModel(), navController: NavController){

    val trivial by viewModel.uiState.collectAsState()

    val trivialData = viewModel.trivialData

    Row(
        verticalAlignment = Alignment.Top,
        modifier = modifier
            .fillMaxSize()
            .padding(dimensionResource(id = R.dimen.padding_medium))
    ) {
        OakImage(trivial.oakImage) //La imagen cambia según el status que tenga el trivial
        if (trivial.status != TrivialStatus.Question){
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                TrivialCardMessage(trivial.messages[trivial.cont]) { viewModel.updateCont(trivial.messages) } //La card de los mensajes va avanzando conforme le hacemos click
                if ((trivial.status == TrivialStatus.Fail || trivial.status == TrivialStatus.Win) && trivial.cont == trivial.messages.size.minus(1)){
                    Button(
                        onClick = { navController.popBackStack() },
                        modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_large))
                    ){ //Volvemos a la pantalla de los juegos
                        Text("Volver")
                    }
                }
            }
        }
        if (trivial.status == TrivialStatus.Question){
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally)
            {
                TrivialQuestion(questionData = trivialData[trivial.cont])
                TrivialTimer(trivial.timeLeft)
                TrivialSolutions(trivialData[trivial.cont].second, viewModel::userGuess, trivial.pressedButton, trivial.enabledButton, trivial.timeLeft)
            }
        }
    }
}

@Composable
fun OakImage(oakImage: Int){

    Image(
        painter = painterResource(oakImage),
        contentDescription = stringResource(id = R.string.oak), //decorativo
        contentScale = ContentScale.Fit,
        modifier = Modifier
            .height(700.dp)
            .padding(end = dimensionResource(id = R.dimen.padding_medium))
    )
}

@Composable
fun TrivialCardMessage(message:String, onNextMessage:() -> Unit){

    Card(
        elevation = CardDefaults.cardElevation(dimensionResource(id = R.dimen.default_card_elevation)),
        modifier = Modifier
            .fillMaxWidth()
            .clickable(
                indication = null, //con esto quitamos la decoración del clickable
                interactionSource = remember { MutableInteractionSource() }
            ) {
                run { onNextMessage() }
            }
    ){
        Row(modifier = Modifier
            .padding(dimensionResource(id = R.dimen.padding_medium))
        ){
            TypeWriterText(message, MaterialTheme.typography.bodyMedium)
        }
    }

}

@Composable
fun TrivialQuestion(questionData: Pair<Question, List<Solution>>) {

    Log.i("Question Data", "$questionData")

    Card(
        elevation = CardDefaults.cardElevation(dimensionResource(id = R.dimen.default_card_elevation)),
        modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_medium))
    ) {
        Text(
            text = questionData.first.text,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_medium))
        )
    }

}

@Composable
fun TrivialSolutions(solutions: List<Solution>, onUserGuess: KFunction2<Boolean, Int, Unit>, pressedButton: Int, enabledButton: Boolean, timeLeft: Int) {

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(horizontal = dimensionResource(id = R.dimen.padding_large), vertical = dimensionResource(
            id = R.dimen.padding_medium
        ))
    ){
        items(solutions, key = {solution -> solution.id}) { solution ->

            Button(
                onClick = {
                    onUserGuess(solution.isCorrect, solution.id)
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor =
                        if ((solution.isCorrect && pressedButton == solution.id) ||
                            (solution.isCorrect && pressedButton != solution.id && pressedButton != Constants.DEFAULT_TRIVIAL_BUTTON))
                            Color.Green
                        else if (!solution.isCorrect && pressedButton == solution.id)
                            MaterialTheme.colorScheme.error
                        else if (solution.isCorrect && timeLeft == 0)
                            MaterialTheme.colorScheme.error
                        else
                            MaterialTheme.colorScheme.background,
                    disabledContainerColor =
                        if ((solution.isCorrect && pressedButton == solution.id) ||
                            (solution.isCorrect && pressedButton != solution.id && pressedButton != Constants.DEFAULT_TRIVIAL_BUTTON))
                            Color.Green
                        else if (!solution.isCorrect && pressedButton == solution.id)
                            MaterialTheme.colorScheme.error
                        else if (solution.isCorrect && timeLeft == 0)
                            MaterialTheme.colorScheme.error
                        else
                            MaterialTheme.colorScheme.background,
                    contentColor = Color.Black
                ),
                elevation = ButtonDefaults.buttonElevation(
                    defaultElevation = dimensionResource(id = R.dimen.default_card_elevation),
                    disabledElevation  = dimensionResource(id = R.dimen.default_card_elevation)),
                enabled = enabledButton,
                modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_small))
            )
            {
                Text(
                    text = solution.answer,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}

@Composable
fun TrivialTimer(timeLeft: Int){

    Text(
        text = "$timeLeft",
        style = MaterialTheme.typography.titleMedium
    )
}

/*@Preview(showSystemUi = true)
@Composable
fun TrivialPreview(){
    TrivialScreen()
}*/