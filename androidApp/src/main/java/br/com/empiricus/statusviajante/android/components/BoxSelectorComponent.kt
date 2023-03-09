package br.com.empiricus.statusviajante.android.components

import android.widget.CalendarView
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CornerBasedShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import androidx.compose.ui.viewinterop.AndroidView

@Composable
fun boxSelector(
    categorias: List<String>,
    title: String,
    selecionado: MutableState<TextFieldValue>,
    modifier: Modifier = Modifier,
    keyboardType: KeyboardType = KeyboardType.Text,
    shape: CornerBasedShape = MaterialTheme.shapes.medium
){

    var expanded by remember { mutableStateOf(false) }
    var textFieldSize by remember { mutableStateOf(Size.Zero) }
    val icon = if (expanded) Icons.Filled.KeyboardArrowUp else Icons.Filled.KeyboardArrowDown


    Box {
        OutlinedTextField(
            readOnly = true,
            value = selecionado.value,
            onValueChange = { selecionado.value = it },
            modifier = modifier
                .wrapContentSize(Alignment.TopStart)
                .fillMaxWidth(0.77f)
                .height(60.dp)
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
            keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
            shape = shape,
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
            modifier = Modifier
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
            categorias.forEach {
                DropdownMenuItem(onClick = {
                    selecionado.value = TextFieldValue(it)
                    expanded = false
                }
                ) {
                    Text(text = it)
                }
            }
        }
    }
}
@Composable
fun boxSelectorCalendar(
    title: String,
    modifier: Modifier = Modifier,
    selecionado: MutableState<TextFieldValue>,

    ){
    var expanded by remember { mutableStateOf(false) }
    var textFieldSize by remember { mutableStateOf(Size.Zero) }


    Box {

        OutlinedTextField(
            modifier = modifier
                .fillMaxWidth(0.77f)
                .height(60.dp)
                .onGloballyPositioned { coordinates ->
                    textFieldSize = coordinates.size.toSize()
                },
            readOnly = true,
            value = selecionado.value,
            onValueChange = { selecionado.value = it },
            label = {
                Text(
                    text = title,
                    color = MaterialTheme.colors.secondary,
                    style = TextStyle(
                        shadow = Shadow(color = MaterialTheme.colors.secondary)
                    )
                ) },
            trailingIcon = {
                Icon(Icons.Filled.CalendarMonth, "Calendario",
                    Modifier.clickable { expanded = !expanded })
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
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
            modifier = Modifier
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color.Black,
                            Color.Gray
                        )
                    )
                )
        ) {
            AndroidView(
                modifier = Modifier.wrapContentSize(),
                factory = {CalendarView(it)},
                update = {
                    it.setOnDateChangeListener { _, year, month, day ->
                        val mes = month + 1
                        if ("$day".length == 1 && "$mes".length == 1){
                            selecionado.value = TextFieldValue("0$day/0$mes/$year")
                        }
                        else if ("$day".length == 1){
                            selecionado.value = TextFieldValue("0$day/$mes/$year")
                        }
                        else if ("$mes".length == 1){
                            selecionado.value = TextFieldValue("$day/0$mes/$year")
                        }
                        else{
                            selecionado.value = TextFieldValue("$day/$mes/$year")
                        }
                        expanded = false

                    }
                }
            )
        }
    }
}
