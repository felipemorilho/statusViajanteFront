package br.com.empiricus.statusviajante.android.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun outLinedButtonComponent(
    onNavigationIconClick: ()-> Unit,
    title: String,
    modifier: Modifier = Modifier
) {
    OutlinedButton(onClick = onNavigationIconClick,
        shape = RoundedCornerShape(30.dp),
        colors = ButtonDefaults.buttonColors(MaterialTheme.colors.primary),
        modifier = modifier.fillMaxWidth(0.8f)
            .height(62.dp)
    )   {
        Text(
            text = title,
            textAlign = TextAlign.Center,
            style = TextStyle(
                color = MaterialTheme.colors.secondaryVariant,
                fontSize = 18.sp
            )
        )
    }
}

@Composable
fun outLinedTextFildPassword (
    valor : MutableState<TextFieldValue>,
    nome : String,
    modifier: Modifier = Modifier
) {

    val passwordVisible = remember {
        mutableStateOf(false)
    }

    OutlinedTextField(
        modifier = modifier
            .fillMaxWidth(0.8f)
            .height(60.dp),
        value = valor.value,
        onValueChange = { valor.value = it },
        label = {
            Text(
                text = nome,
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
        shape = RoundedCornerShape(25.dp),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = MaterialTheme.colors.secondary,
            unfocusedBorderColor = MaterialTheme.colors.secondary,
            focusedLabelColor = MaterialTheme.colors.secondary,
            cursorColor = MaterialTheme.colors.secondary,

            )
    )

}