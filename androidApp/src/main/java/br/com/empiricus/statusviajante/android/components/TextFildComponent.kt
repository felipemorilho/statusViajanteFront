package br.com.empiricus.statusviajante.android.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp

@Composable
fun outLinedTextFildComponent(
    valor : MutableState<TextFieldValue>,
    title: String,
    modifier: Modifier = Modifier,
    keyboardType: KeyboardType = KeyboardType.Text,
){
    OutlinedTextField(
        modifier = modifier
            .fillMaxWidth(0.78f)
            .height(60.dp),
        value = valor.value,
        onValueChange = { valor.value = it },
        label = {
            Text(
                text = title,
                color = MaterialTheme.colors.secondary,
                style = TextStyle(
                    shadow = Shadow(color = MaterialTheme.colors.secondary)
                )
            )
        },
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
        shape = MaterialTheme.shapes.medium,
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = MaterialTheme.colors.secondary,
            unfocusedBorderColor = MaterialTheme.colors.secondary,
            focusedLabelColor = MaterialTheme.colors.secondary,
            cursorColor = MaterialTheme.colors.secondary
        )
    )
}

@Composable
fun outLinedTextFildPassword (
    valor : MutableState<TextFieldValue>,
    title : String,
    modifier: Modifier = Modifier
) {

    val passwordVisible = remember {
        mutableStateOf(false)
    }

    OutlinedTextField(
        modifier = modifier
            .fillMaxWidth(0.78f)
            .height(60.dp),
        value = valor.value,
        onValueChange = { valor.value = it },
        label = {
            Text(
                text = title,
                color = MaterialTheme.colors.secondary,
                style = TextStyle(shadow = Shadow(color = MaterialTheme.colors.secondary))
            )
        },
        visualTransformation = if (passwordVisible.value.not()) PasswordVisualTransformation() else VisualTransformation.None,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        trailingIcon = {
            val icone = if (passwordVisible.value.not()) Icons.Filled.Visibility else Icons.Filled.VisibilityOff
            val description = if (passwordVisible.value.not()) "Visivel" else "Nao visivel"
            IconButton(onClick = { passwordVisible.value = passwordVisible.value.not() }) {
                Icon(
                    imageVector = icone,
                    contentDescription = description,
                    tint = MaterialTheme.colors.secondary
                )
            }
        },
        shape = MaterialTheme.shapes.medium,
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = MaterialTheme.colors.secondary,
            unfocusedBorderColor = MaterialTheme.colors.secondary,
            focusedLabelColor = MaterialTheme.colors.secondary,
            cursorColor = MaterialTheme.colors.secondary,
        )
    )
}

@Composable
fun OutLinedTextFildSearch (
    valor : MutableState<TextFieldValue>,
    title : String,
    modifier: Modifier = Modifier,
    trailingIcon: () -> Unit
) {

    val passwordVisible = remember {
        mutableStateOf(false)
    }

    OutlinedTextField(
        modifier = modifier
            .fillMaxWidth(0.78f)
            .height(60.dp),
        value = valor.value,
        onValueChange = { valor.value = it },
        label = {
            Text(
                text = title,
                color = MaterialTheme.colors.secondary,
                style = TextStyle(shadow = Shadow(color = MaterialTheme.colors.secondary))
            )
        },
        visualTransformation = if (passwordVisible.value.not()) PasswordVisualTransformation() else VisualTransformation.None,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        trailingIcon = {
        },
        shape = MaterialTheme.shapes.medium,
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = MaterialTheme.colors.secondary,
            unfocusedBorderColor = MaterialTheme.colors.secondary,
            focusedLabelColor = MaterialTheme.colors.secondary,
            cursorColor = MaterialTheme.colors.secondary,
        )
    )
}

@Composable
fun outLinedTextFildReadOnly(
    valor : MutableState<TextFieldValue>,
    title: String,
    modifier: Modifier = Modifier,
    keyboardType: KeyboardType = KeyboardType.Text,
){
    OutlinedTextField(
        readOnly = true,
        modifier = modifier
            .fillMaxWidth(0.78f)
            .height(60.dp),
        value = valor.value,
        onValueChange = { valor.value = it },
        label = {
            Text(
                text = title,
                color = MaterialTheme.colors.secondary,
                style = TextStyle(
                    shadow = Shadow(color = MaterialTheme.colors.secondary)
                )
            )
        },
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
        shape = MaterialTheme.shapes.medium,
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = MaterialTheme.colors.secondary,
            unfocusedBorderColor = MaterialTheme.colors.secondary,
            focusedLabelColor = MaterialTheme.colors.secondary,
            cursorColor = MaterialTheme.colors.secondary
        )
    )
}
