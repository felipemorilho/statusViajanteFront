package br.com.empiricus.statusviajante.android.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp

@Composable
fun outLinedTextFildComponent(
    valor : MutableState<TextFieldValue>,
    title: String,
    modifier: Modifier = Modifier
){
    OutlinedTextField(
        modifier = modifier
            .fillMaxWidth(0.8f)
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
        shape = RoundedCornerShape(25.dp),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = MaterialTheme.colors.secondary,
            unfocusedBorderColor = MaterialTheme.colors.secondary,
            focusedLabelColor = MaterialTheme.colors.secondary,
            cursorColor = MaterialTheme.colors.secondary
        )
    )
}

