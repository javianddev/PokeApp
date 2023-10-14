package com.example.pokeapp.compose.options

import android.app.DatePickerDialog
import android.widget.DatePicker
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.pokeapp.R
import java.time.LocalDateTime
import java.util.Calendar
import java.util.Date

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditProfile(navController: NavController, modifier: Modifier = Modifier){

    val focusManager = LocalFocusManager.current
    val formModifier = Modifier
        .padding(dimensionResource(id = R.dimen.padding_medium))
        .fillMaxWidth()


    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .verticalScroll(rememberScrollState())
            .padding(dimensionResource(id = R.dimen.padding_medium))
    ){
        OutlinedTextField(
            value = "",
            onValueChange = {},
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
            value = "",
            onValueChange = {},
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

        PokeDatePickerDialog(formModifier)
    }

}

@Composable
fun PokeDatePickerDialog(modifier: Modifier = Modifier) {

    val mContext = LocalContext.current

    val mYear: Int
    val mMonth: Int
    val mDay: Int

    val mCalendar = Calendar.getInstance()

    mYear = mCalendar.get(Calendar.YEAR)
    mMonth = mCalendar.get(Calendar.MONTH)
    mDay = mCalendar.get(Calendar.DAY_OF_MONTH)

    mCalendar.time = Date()

    val mDate = remember { mutableStateOf("") }

    val mDatePickerDialog = DatePickerDialog(
        mContext,
        { _: DatePicker, mYear: Int, mMonth: Int, mDayOfMonth: Int ->
            mDate.value = "$mDayOfMonth/${mMonth + 1}/$mYear"
        }, mYear, mMonth, mDay
    )

    Column(
        modifier = modifier
    ) {

        Button(
            onClick = { mDatePickerDialog.show() },
            shape = RoundedCornerShape(dimensionResource(id = R.dimen.shape_medium)),
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
        ){
            Text(text = stringResource(id = R.string.select_birthdate), color = MaterialTheme.colorScheme.onPrimary)
        }

        Text(text = "${stringResource(id = R.string.selected_birthdate)}: ${mDate.value}", textAlign = TextAlign.Center)
    }
}

@Preview("Editar Perfil")
@Composable
fun EditProfilePreview(){

    val navController = rememberNavController()

    EditProfile(navController)
}