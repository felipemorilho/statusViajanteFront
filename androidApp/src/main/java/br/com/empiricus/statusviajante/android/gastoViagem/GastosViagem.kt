package br.com.empiricus.statusviajante.android.gastoViagem

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.toSize
import br.com.empiricus.statusviajante.android.MyApplicationTheme
import br.com.empiricus.statusviajante.android.components.outLinedButtonComponent
import br.com.empiricus.statusviajante.android.components.outLinedTextFildComponent
import br.com.empiricus.statusviajante.android.components.topBarComponent

@Composable
fun GastosViagem(onBack: () -> Boolean) {
    MyApplicationTheme {
        Scaffold(
            topBar = { topBarComponent(onClickNav = {onBack.invoke()}) }
        ) {
            LazyColumn(
                modifier = Modifier
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                MaterialTheme.colors.secondaryVariant,
                                MaterialTheme.colors.primaryVariant
                            )
                        )
                    )
                    .fillMaxSize()
                    .padding(it),
                verticalArrangement = Arrangement.spacedBy(25.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ){

                item {

                    Spacer(modifier = Modifier.height(60.dp))
                    val titulo = remember { mutableStateOf(TextFieldValue()) }
                    Text(text = "Novo Gasto", fontWeight = FontWeight.Bold, fontSize = 32.sp)
                }

                item {

                    val valorGasto = remember { mutableStateOf(TextFieldValue()) }
                    outLinedTextFildComponent(valor = valorGasto, title = "Valor do Gasto")
                }

                item{

                    var expanded by remember { mutableStateOf(false) }
                    val categorias = listOf("Lazer", "Hospedagem", "Transporte", "Alimentação", "Outros")
                    var selecionado by remember { mutableStateOf("") }

                    var textFieldSize by remember { mutableStateOf(Size.Zero) }

                    val icon = if (expanded) Icons.Filled.KeyboardArrowUp else Icons.Filled.KeyboardArrowDown

                    OutlinedTextField(
                        value = selecionado,
                        shape = RoundedCornerShape(18.dp),
                        onValueChange = { selecionado = it},
                        modifier = Modifier
                            .fillMaxWidth(0.8f)
                            .onGloballyPositioned { coordinates ->
                                textFieldSize = coordinates.size.toSize()
                            },
                        label = { Text("Label")},
                        trailingIcon = {
                            Icon(icon,"contentDescription",
                                Modifier.clickable { expanded = !expanded })
                        }
                        )

                    DropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false },
                        modifier = Modifier
                            .fillMaxWidth(0.8f)
                    ) {
                        categorias.forEach { label ->
                            DropdownMenuItem(onClick = {
                                selecionado = label
                                expanded = false
                            }) {
                                Text(text = label)
                            }
                        }
                    }
                }

                item {

                    val dataGasto = remember { mutableStateOf(TextFieldValue()) }
                    outLinedTextFildComponent(valor = dataGasto, title = "Data")
                }

                item {

                    val descricaoGasto = remember { mutableStateOf(TextFieldValue()) }
                    outLinedTextFildComponent(valor = descricaoGasto, title = "Descrição")
                }

                item {

                    Spacer(modifier = Modifier.height(90.dp))
                    outLinedButtonComponent(onNavigationIconClick = {onBack.invoke()}, title = "Cadastrar Gasto")
                }
            }
        }
    }
}


@Preview
@Composable
fun gastosViagemPreview(){
    GastosViagem (onBack = {true})
}