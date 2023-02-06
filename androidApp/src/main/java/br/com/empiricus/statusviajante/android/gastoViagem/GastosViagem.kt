package br.com.empiricus.statusviajante.android.gastoViagem

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.toSize
import androidx.compose.ui.window.isPopupLayout
import br.com.empiricus.statusviajante.android.MyApplicationTheme
import br.com.empiricus.statusviajante.android.components.*
import kotlinx.coroutines.launch

@Composable
fun GastosViagem(onBack: () -> Boolean) {
    val scope = rememberCoroutineScope()
    val scaffoldState = rememberScaffoldState()

    MyApplicationTheme {
        Scaffold(
            topBar = { topBarComponent() },
            bottomBar = { bottonBarComponent(
                onBack = {onBack.invoke()},
                onNavDrawer = {
                    scope.launch { scaffoldState.drawerState.open() }
                }) },
            drawerGesturesEnabled = scaffoldState.drawerState.isOpen,
            drawerContent = {
                drawerHeader()
                drawerBody(
                    itens = listaItensDrawer(),
                    onItemClick = {
                        when(it.id) {

                        }
                    }
                )
            }
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
                    Text(text = "Novo Gasto", fontWeight = FontWeight.Bold, fontSize = 32.sp)
                }

                item {

                    val valorGasto = remember { mutableStateOf(TextFieldValue()) }
                    outLinedTextFildComponent(valor = valorGasto, title = "Valor do Gasto")
                }

                item {
                    var selecionado: MutableState<String> = remember { mutableStateOf("") }
                    val categorias =
                        listOf("Lazer", "Hospedagem", "Transporte", "Alimentação", "Outros")

                    boxSelector(categorias = categorias, title = "Categoria", selecionado = selecionado)
                }

                item {

                    val dataGasto = remember { mutableStateOf("") }
                    boxSelectorCalendar(title = "Data", selecionado = dataGasto)
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