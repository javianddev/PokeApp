package com.example.pokeapp.compose.options

import android.app.DatePickerDialog
import android.widget.DatePicker
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.pokeapp.R
import com.example.pokeapp.utils.toDate
import com.example.pokeapp.utils.toFormattedString
import com.example.pokeapp.viewmodels.EditProfileViewModel
import java.util.Calendar
import java.util.Date


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditProfile(navController: NavController, viewModel: EditProfileViewModel = hiltViewModel(), modifier: Modifier = Modifier){

    val editProfileState by viewModel.uiState.collectAsState()
    val focusManager = LocalFocusManager.current
    val formModifier = Modifier
        .padding(dimensionResource(id = R.dimen.padding_medium))
        .fillMaxWidth()
    var birthdate by rememberSaveable { mutableStateOf(Date().time) }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .verticalScroll(rememberScrollState())
            .padding(dimensionResource(id = R.dimen.padding_medium))
    ){
        OutlinedTextField(
            value = editProfileState.name,
            onValueChange = {viewModel.setName(it)},
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
            value = editProfileState.birthplace,
            onValueChange = {viewModel.setBirthplace(it)},
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

        /************************************************/
        /**************INICIO DIALOG********************/
        /***********************************************/

        val interactionSource = remember { MutableInteractionSource() }
        val isPressed: Boolean by interactionSource.collectIsPressedAsState()

        var selectedDate by rememberSaveable { mutableStateOf(editProfileState.birthdate.toFormattedString()) }

        val context = LocalContext.current

        val year: Int = editProfileState.birthdate.year
        val month: Int = editProfileState.birthdate.month
        val day: Int = editProfileState.birthdate.day

        val datePickerDialog =
            DatePickerDialog(context, { _: DatePicker, year: Int, month: Int, dayOfMonth: Int ->
                focusManager.clearFocus()
                val newDate = Calendar.getInstance()
                newDate.set(year, month, dayOfMonth)
                selectedDate = editProfileState.birthdate.toFormattedString()
            }, year, month, day)

        //Limitamos hasta 75 años y subimos hasta 18 años, por poner un límite al control de fechas
        val limitC = Calendar.getInstance()
        limitC.add(Calendar.YEAR, -75)
        datePickerDialog.datePicker.minDate = limitC.time.time
        //Hay que limitar las fechas de cumpleaños
        limitC.add(Calendar.YEAR, 57)
        datePickerDialog.datePicker.maxDate = limitC.time.time

        OutlinedTextField(
            value = selectedDate,
            onValueChange = {viewModel.setBirthdate(it.toDate())},
            readOnly = true,
            leadingIcon = {
                Icon(
                    imageVector = Icons.Filled.CalendarMonth,
                    contentDescription = null
                )
            },
            interactionSource = interactionSource,
            modifier = formModifier
        )
        if (isPressed) {
            datePickerDialog.show()
        }

        /************************************************/
        /**************FIN DIALOG***********************/
        /***********************************************/

        var savedProfile by remember { mutableStateOf(false) }

        Button(
            onClick = {
                viewModel.saveProfile(editProfileState)
                savedProfile = true
            },
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
            modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_medium))
        ) {
            Text(
                text = stringResource(id = R.string.save_button)
            )
        }

        if (savedProfile){
            Text(
                text = stringResource(id = R.string.saved_profile),
                textAlign = TextAlign.Center,
                modifier = formModifier
            )
        }
    }

}

@Preview("Editar Perfil")
@Composable
fun EditProfilePreview(){

    val navController = rememberNavController()

    EditProfile(navController)
}