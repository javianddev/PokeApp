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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.pokeapp.R
import com.example.pokeapp.compose.utils.TrivialMessages
import com.example.pokeapp.compose.utils.TrivialStatus
import com.example.pokeapp.compose.utils.TypeWriterText
import com.example.pokeapp.data.models.Question
import com.example.pokeapp.data.models.Solution
import com.example.pokeapp.viewmodels.TrivialViewModel


@Composable
fun TrivialScreen(viewModel: TrivialViewModel = hiltViewModel(), navController: NavController, modifier: Modifier = Modifier){

    val trivial by viewModel.uiState.collectAsState()

    val trivialData = viewModel.trivialData

    var messages by remember {mutableStateOf(TrivialMessages.initialMessages)}

    messages = when (trivial.status) {
        is TrivialStatus.Initial -> TrivialMessages.initialMessages
        is TrivialStatus.Question -> TrivialMessages.questionMessages
        is TrivialStatus.Win -> TrivialMessages.winnerMessages
        is TrivialStatus.Fail -> TrivialMessages.failMessages
    }

    Row(
        verticalAlignment = Alignment.Top,
        modifier = modifier
            .fillMaxSize()
            .padding(dimensionResource(id = R.dimen.padding_medium))
    ) {
        OakImage(trivial.status) //La imagen cambia según el status que tenga el trivial
        if (trivial.status != TrivialStatus.Question){
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                TrivialCardMessage(messages[trivial.cont]) { viewModel.updateCont(messages) } //La card de los mensajes va avanzando conforme le hacemos click
                if ((trivial.status == TrivialStatus.Fail || trivial.status == TrivialStatus.Win) && trivial.cont == messages.size.minus(1)){
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
                TrivialQuestion(questionData = trivialData[trivial.cont],)
                TrivialTimer()
                TrivialSolutions(trivialData[trivial.cont].second, viewModel::userGuess, trivial.buttonColor, trivial.enabledButton)
            }
        }
    }
}

@Composable
fun OakImage(status: TrivialStatus){

    var oakImage by remember {mutableStateOf(R.drawable.oak1)}

    oakImage = when (status) {
        is TrivialStatus.Win -> R.drawable.oak2  //oak3
        is TrivialStatus.Initial -> R.drawable.oak2 //oak1
        is TrivialStatus.Fail -> R.drawable.oak2
        is TrivialStatus.Question -> R.drawable.oak2
    }

    Image(
        painter = painterResource(oakImage),
        contentDescription = null, //decorativo
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
fun TrivialSolutions(solutions: List<Solution>, onUserGuess:(Boolean) -> Unit, buttonColor: Color, enabledButton: Boolean) {

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(horizontal = dimensionResource(id = R.dimen.padding_large), vertical = dimensionResource(
            id = R.dimen.padding_medium
        ))
    ){
        items(solutions, key = {solution -> solution.id}) { solution ->
            Button(
                onClick = {
                    onUserGuess(solution.isCorrect)
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (solution.isCorrect) buttonColor else MaterialTheme.colorScheme.background,
                    disabledContainerColor = if (solution.isCorrect) buttonColor else MaterialTheme.colorScheme.background,
                    contentColor = Color.Black /*TODO poner otro color*/
                ),
                elevation = ButtonDefaults.buttonElevation(dimensionResource(id = R.dimen.default_card_elevation)),
                enabled = enabledButton,
                modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_small))
            )
            {
                Text(
                    text = "${solution.answer}",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}

@Composable
fun TrivialTimer(){

    Text(
        text = "xd", /*TODO*/
        style = MaterialTheme.typography.titleMedium
    )
}

/*@Preview(showSystemUi = true)
@Composable
fun TrivialPreview(){
    TrivialScreen()
}*/