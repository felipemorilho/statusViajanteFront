package br.com.empiricus.statusviajante.android.cadastroviagens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.com.empiricus.statusviajante.android.MyApplicationTheme
import br.com.empiricus.statusviajante.android.Route
import br.com.empiricus.statusviajante.android.components.*

@Composable
fun cadastroViagens(onNavTest: () -> Unit) {
    MyApplicationTheme {
        Scaffold(
            topBar = { topBarComponent() }
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
                                modifier = Modifier.fillMaxWidth(0.9f),
                                valor = origem,
                                title = "Origem"
                            )
                        }

                        Column(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalAlignment = Alignment.End
                        ) {
                            outLinedTextFildComponent(
                                modifier = Modifier.fillMaxWidth(0.9f),
                                valor = destino,
                                title = "Destino"
                            )
                        }
                    }
                }

                item {
                    val moedaPrincipal = remember { mutableStateOf(TextFieldValue()) }
                    outLinedTextFildComponent(valor = moedaPrincipal, title = "Moeda corrente")
                }

                item {
                    Row(
                        modifier = Modifier.fillMaxWidth(0.8f)
                    ) {
                        val dataInicio = remember { mutableStateOf(TextFieldValue()) }
                        val dataFinal = remember { mutableStateOf(TextFieldValue()) }
                        val orcamentoTotal = remember { mutableStateOf(TextFieldValue()) }
                        val orcamentoDiario = remember { mutableStateOf(TextFieldValue()) }

                        Column(
                            modifier = Modifier.fillMaxWidth(0.5f),
                            horizontalAlignment = Alignment.Start
                        ) {
                            outLinedTextFildDate(
                                modifier = Modifier.fillMaxWidth(0.95f),
                                valor = dataInicio,
                                title = "Data inicio"
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
                            outLinedTextFildDate(
                                modifier = Modifier.fillMaxWidth(0.95f),
                                valor = dataFinal,
                                title = "Data final"
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
                    Spacer(modifier = Modifier.height(25.dp))
                    outLinedButtonComponent(onNavigationIconClick = {onNavTest.invoke()}, title = "Cadastrar viagem")
                }
            }
        }
    }
}


@Preview
@Composable
fun previewCadastroV(){
    cadastroViagens(onNavTest = {})
}