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
        shape = RoundedCornerShape(18.dp),
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
