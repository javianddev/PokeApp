package com.example.pokeapp.compose.trainer


import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Place
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DisplayMode
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.pokeapp.R
import com.example.pokeapp.compose.navigation.AppScreens
import com.example.pokeapp.utils.toFormattedString
import com.example.pokeapp.viewmodels.EditTrainerViewModel
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId


@SuppressLint("UnrememberedMutableState")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditTrainer(navController: NavController, viewModel: EditTrainerViewModel = hiltViewModel(), modifier: Modifier = Modifier,
) {

    val editTrainerState by viewModel.uiState.collectAsState()
    val focusManager = LocalFocusManager.current
    val formModifier = Modifier
        .padding(dimensionResource(id = R.dimen.padding_medium))
        .fillMaxWidth()

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .verticalScroll(rememberScrollState())
            .padding(dimensionResource(id = R.dimen.padding_medium))
    ) {

        OutlinedTextField(
            value = editTrainerState.name,
            onValueChange = { viewModel.setName(it) },
            singleLine = true,
            placeholder = { Text(text = stringResource(id = R.string.trainer_name)) },
            shape = RoundedCornerShape(dimensionResource(id = R.dimen.shape_medium)),
            leadingIcon = {
                Icon(
                    imageVector = Icons.Filled.AccountBox,
                    contentDescription = null
                )
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next
            ),
            keyboardActions = KeyboardActions(
                onNext = {
                    focusManager.clearFocus()
                }
            ),
            modifier = formModifier
        )

        OutlinedTextField(
            value = editTrainerState.birthplace,
            onValueChange = { viewModel.setBirthplace(it) },
            singleLine = true,
            placeholder = { Text(text = stringResource(id = R.string.birthplace)) },
            shape = RoundedCornerShape(dimensionResource(id = R.dimen.shape_medium)),
            leadingIcon = {
                Icon(
                    imageVector = Icons.Filled.Place,
                    contentDescription = null
                )
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    focusManager.clearFocus()
                }
            ),
            modifier = formModifier
        )

        /*******************DATEPICKER DIALOG INICIO************************/

        var showDatePicker by remember {mutableStateOf(false)}

        val datePickerState = rememberDatePickerState(
            initialDisplayMode = DisplayMode.Input,
            yearRange = IntRange(LocalDate.now().minusYears(90).year, LocalDate.now().minusYears(18).year)
        )

        OutlinedTextField(
            value = editTrainerState.birthdate.toFormattedString(),
            onValueChange = {datePickerState.selectedDateMillis?.let {
                Instant.ofEpochMilli(it).atZone(
                    ZoneId.systemDefault()).toLocalDate()
            }?.let { viewModel.setBirthdate(it) }},
            placeholder = { Text(text = stringResource(id = R.string.selected_birthdate))},
            shape =  RoundedCornerShape(dimensionResource(id = R.dimen.shape_medium)),
            leadingIcon = {
                Icon(
                    imageVector = Icons.Filled.CalendarMonth,
                    contentDescription = null
                )
            },
            readOnly = true,
            modifier = Modifier
                .padding(dimensionResource(id = R.dimen.padding_medium))
                .fillMaxWidth()
                .clickable {
                    showDatePicker = true
                    focusManager.clearFocus()
                },
        )


        if (showDatePicker){
            DatePicker(
                state = datePickerState,
                showModeToggle = false,
                title = {Text(stringResource(id = R.string.selected_birthdate))},
                modifier = formModifier
            )
        }

        /*******************DATEPICKER DIALOG FINAL*************************/




        Button(
            onClick = {
                /*datePickerState.selectedDateMillis?.let {
                    Instant.ofEpochMilli(it).atZone(
                        ZoneId.systemDefault()).toLocalDate()
                }?.let { viewModel.setBirthdate(it) }*/
                viewModel.saveProfile(editTrainerState)
                navController.popBackStack(AppScreens.TrainerScreen.route, inclusive = false)
            },
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
            modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_medium))
        ) {
            Text(
                text = stringResource(id = R.string.save_button)
            )
        }
    }

}

@Preview("Editar Entrenador")
@Composable
fun EditProfilePreview(){

    //EditTrainer()
}