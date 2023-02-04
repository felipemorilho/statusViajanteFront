package br.com.empiricus.statusviajante.android.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize

@Composable
fun boxSelector(
    categorias: List<String>,
    title: String,
    modifier: Modifier = Modifier
){
    var expanded by remember { mutableStateOf(false) }
    var textFieldSize by remember { mutableStateOf(Size.Zero) }
    val icon = if (expanded) Icons.Filled.KeyboardArrowUp else Icons.Filled.KeyboardArrowDown
    var selecionado by remember { mutableStateOf("") }


    Box {
        OutlinedTextField(
            value = selecionado,
            onValueChange = { selecionado = it },
            modifier = modifier
                .wrapContentSize(Alignment.TopStart)
                .fillMaxWidth(0.8f)
                .onGloballyPositioned { coordinates ->
                    textFieldSize = coordinates.size.toSize()
                },
            label = {
                Text(
                    text = title,
                    color = MaterialTheme.colors.secondary,
                    style = TextStyle(
                        shadow = Shadow(color = MaterialTheme.colors.secondary)
                    )
                ) },
            trailingIcon = {
                Icon(icon, "Categorias",
                    Modifier.clickable { expanded = !expanded })
            },
            shape = MaterialTheme.shapes.medium,
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = MaterialTheme.colors.secondary,
                unfocusedBorderColor = MaterialTheme.colors.secondary,
                focusedLabelColor = MaterialTheme.colors.secondary,
                cursorColor = MaterialTheme.colors.secondary
            )
        )


        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = androidx.compose.ui.Modifier
                .width(with(LocalDensity.current){textFieldSize.width.toDp()})
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            MaterialTheme.colors.secondaryVariant,
                            MaterialTheme.colors.primaryVariant
                        )
                    )
                )
        ) {
            categorias.forEach { label ->
                DropdownMenuItem(onClick = {
                    selecionado = label
                    expanded = false
                }
                ) {
                    Text(text = label)
                }
            }
        }
    }
}
