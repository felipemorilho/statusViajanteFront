package br.com.empiricus.statusviajante.android.viagens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.empiricus.statusviajante.android.MyApplicationTheme
import br.com.empiricus.statusviajante.android.components.*
import br.com.empiricus.statusviajante.model.MockGastoViagem
import br.com.empiricus.statusviajante.model.MockListaViagens
import kotlinx.coroutines.launch


@Composable
fun DescViagens(onNavNovoGasto: () -> Unit, onBack: () -> Boolean) {
    MyApplicationTheme {
        val scope = rememberCoroutineScope()
        val scaffoldState = rememberScaffoldState()
        Scaffold(
            scaffoldState = scaffoldState,
            topBar = { topBarComponent() },
            bottomBar = {
                bottonBarComponent(
                    onBack = { onBack.invoke() },
                    onNavDrawer = {
                        scope.launch { scaffoldState.drawerState.open() }
                    })
            },
            drawerGesturesEnabled = scaffoldState.drawerState.isOpen,
            drawerContent = {
                drawerHeader()
                drawerBody(
                    itens = listaItensDrawer(),
                    onItemClick = {
                        when (it.id) {

                        }
                    }
                )
            }

        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                MaterialTheme.colors.secondaryVariant,
                                MaterialTheme.colors.primaryVariant
                            )
                        )
                    )
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    val gastosTotais = MockGastoViagem.gastosTotais
                    val viagem = MockListaViagens.listaViagem

                    Row(
                        modifier = Modifier.fillMaxWidth(0.8f),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = viagem[0].nome, fontWeight = FontWeight.Bold, fontSize = 22.sp)
                        IconButton(onClick = {}) {
                            Icon(imageVector = Icons.Filled.Delete, contentDescription = "botão deletar")
                        }
                    }


                    


                    Row(
                        modifier = Modifier.fillMaxWidth(0.8f),
                        horizontalArrangement = Arrangement.Start
                    ) {
                        Text(text = "DATAS", fontWeight = FontWeight.Bold, fontSize = 15.sp)

                        Column(
                            modifier = Modifier.fillMaxWidth(0.95f),
                            horizontalAlignment = Alignment.End
                        ) {
                            Text(text = "${viagem[0].dataInicio} - ${viagem[0].dataFinal}", fontWeight = FontWeight.Bold, fontSize = 15.sp)
                        }
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth(0.8f),
                        horizontalArrangement = Arrangement.Start
                    ) {
                        Text(text = "GASTOS", fontWeight = FontWeight.Bold, fontSize = 15.sp)

                        Column(
                            modifier = Modifier.fillMaxWidth(0.95f),
                            horizontalAlignment = Alignment.End
                        ) {
                            Text(text = gastosTotais.toString(), fontWeight = FontWeight.Bold, fontSize = 15.sp)
                        }
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth(0.8f),
                        horizontalArrangement = Arrangement.Start
                    ) {
                        Text(text = "DESCRIÇÃO", fontWeight = FontWeight.Bold, fontSize = 15.sp)
                        Column(
                            modifier = Modifier.fillMaxWidth(0.95f),
                            horizontalAlignment = Alignment.End
                        ) {
                            Text(text = viagem[0].descricao, fontWeight = FontWeight.Bold, fontSize = 15.sp)
                        }
                    }
                    Spacer(modifier = Modifier.height(17.dp))
                }
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(0.65f)
                        .padding(it),
                    verticalArrangement = Arrangement.spacedBy(25.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    val gastos = MockGastoViagem.gastos

                    items(gastos.size) {
                        Column(
                            modifier = Modifier.fillMaxWidth(0.8f)
                        ) {
                            DescViagemComponent(
                                id = gastos[it].id,
                                title = gastos[it].descricao,
                                valor = gastos[it].valor,
                                onItemClick = {}
                            )
                        }
                    }
                }
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    outLinedButtonComponent(
                        onNavigationIconClick = { onNavNovoGasto.invoke() },
                        title = "ADICIONAR NOVO GASTO"
                    )
                    Spacer(modifier = Modifier.height(25.dp))

                    outLinedButtonComponent(
                        onNavigationIconClick = { },
                        title = "SALVAR"
                    )
                    Spacer(modifier = Modifier.height(25.dp))
                }
            }
        }
    }
}


@Preview
@Composable
fun previewDescViagens() {
    DescViagens ({},{true})
}