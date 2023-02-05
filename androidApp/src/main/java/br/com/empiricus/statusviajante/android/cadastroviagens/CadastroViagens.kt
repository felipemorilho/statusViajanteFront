package br.com.empiricus.statusviajante.android.cadastroviagens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.com.empiricus.statusviajante.android.MyApplicationTheme
import br.com.empiricus.statusviajante.android.components.*
import kotlinx.coroutines.launch

@Composable
fun cadastroViagens(onBack: () -> Boolean) {

    val scope = rememberCoroutineScope()
    val scaffoldState = rememberScaffoldState()

    MyApplicationTheme {
        Scaffold(
            scaffoldState = scaffoldState,
            topBar = {topBarComponent()},
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

        ){
            LazyColumn(
                modifier = Modifier
                    .background(brush = Brush.verticalGradient(
                        colors = listOf(
                            MaterialTheme.colors.secondaryVariant,
                            MaterialTheme.colors.primaryVariant
                        )
                    ))
                    .fillMaxSize()
                    .padding(it),
                verticalArrangement = Arrangement.spacedBy(25.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                item {
                    Spacer(modifier = Modifier.height(35.dp))
                    val nomeViagem = remember { mutableStateOf(TextFieldValue()) }
                    outLinedTextFildComponent(valor = nomeViagem, title = "Nome da viagem")
                }

                item {
                    Row(
                        modifier = Modifier.fillMaxWidth(0.8f)
                    ) {
                        val origem = remember { mutableStateOf(TextFieldValue()) }
                        val destino = remember { mutableStateOf(TextFieldValue()) }

                        Column(
                            modifier = Modifier.fillMaxWidth(0.5f),
                            horizontalAlignment = Alignment.Start
                        ) {
                            outLinedTextFildComponent(
                                modifier = Modifier.fillMaxWidth(0.95f),
                                valor = origem,
                                title = "Origem"
                            )
                        }

                        Column(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalAlignment = Alignment.End
                        ) {
                            outLinedTextFildComponent(
                                modifier = Modifier.fillMaxWidth(0.95f),
                                valor = destino,
                                title = "Destino"
                            )
                        }
                    }
                }

                item {
                    var selecionado: MutableState<String> = remember { mutableStateOf("") }
                    val moedaCorrente = listOf(
                        "Real", "Dolar Americano", "Peso Argentino", "Euro", "Yen"
                    )

                    boxSelector(
                        categorias = moedaCorrente,
                        title = "Moeda Corrente",
                        selecionado = selecionado
                    )
                }

                item {
                    Row(
                        modifier = Modifier.fillMaxWidth(0.8f)
                    ) {
                        val dataInicio: MutableState<String> = remember { mutableStateOf("") }
                        val dataFinal : MutableState<String> = remember { mutableStateOf("") }
                        val orcamentoTotal = remember { mutableStateOf(TextFieldValue()) }
                        val orcamentoDiario = remember { mutableStateOf(TextFieldValue()) }

                        Column(
                            modifier = Modifier.fillMaxWidth(0.5f),
                            horizontalAlignment = Alignment.Start
                        ) {
                            boxSelectorCalendar(
                                modifier = Modifier.fillMaxWidth(0.95f),
                                title = "Data inicio",
                                selecionado = dataInicio
                            )
                            Spacer(modifier = Modifier.height(25.dp))
                            outLinedTextFildComponent(
                                modifier = Modifier.fillMaxWidth(0.95f),
                                valor = orcamentoTotal,
                                title = "Orçamento total"
                            )
                        }

                        Column(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalAlignment = Alignment.End
                        ) {
                            boxSelectorCalendar(
                                modifier = Modifier.fillMaxWidth(0.95f),
                                title = "Data final",
                                selecionado = dataFinal
                            )
                            Spacer(modifier = Modifier.height(25.dp))
                            outLinedTextFildComponent(
                                modifier = Modifier.fillMaxWidth(0.95f),
                                valor = orcamentoDiario,
                                title = "Orçamento diario"
                            )
                        }
                    }
                }
                item {
                    val quantidadeVianjantes = remember { mutableStateOf(TextFieldValue()) }
                    outLinedTextFildComponent(valor = quantidadeVianjantes, title = "Quantidade de viajantes no grupo")
                }

                item {
                    val descricao = remember { mutableStateOf(TextFieldValue()) }
                    outLinedTextFildComponent(valor = descricao, title = "Descrição")
                }

                item {
                    outLinedButtonComponent(onNavigationIconClick = {}, title = "Cadastrar viagem")
                    Spacer(modifier = Modifier.height(25.dp))
                }
            }
        }
    }
}


@Preview
@Composable
fun previewCadastroV(){
    cadastroViagens(onBack = {true})
}