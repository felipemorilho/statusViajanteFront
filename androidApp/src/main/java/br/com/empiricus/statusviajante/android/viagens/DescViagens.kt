package br.com.empiricus.statusviajante.android.viagens

import android.widget.Button
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.empiricus.statusviajante.android.MyApplicationTheme
import br.com.empiricus.statusviajante.android.Route
import br.com.empiricus.statusviajante.android.components.*
import br.com.empiricus.statusviajante.model.MockGastoViagem
import br.com.empiricus.statusviajante.model.MockListaViagens
import kotlinx.coroutines.launch


@Composable
fun DescViagens() {
    MyApplicationTheme {
        val scope = rememberCoroutineScope()
        val scaffoldState = rememberScaffoldState()
        Scaffold(
            scaffoldState = scaffoldState,
            topBar = { topBarComponent() },
            bottomBar = {
                bottonBarComponent(
                    colorBackButton = Color.Transparent,
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
            ) {

                val gastos = MockGastoViagem.gastos
                val gastosTotais = MockGastoViagem.gastosTotais
                val viagem = MockListaViagens.listaViagem

                item {
                    Spacer(modifier = Modifier.height(25.dp))
                    Text(text = "SUAS VIAGENS", fontWeight = FontWeight.Bold, fontSize = 22.sp)
                }

                item {
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
                }
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
                item {
                    outLinedButtonComponent(
                        onNavigationIconClick = {  },
                        title = "ADICIONAR NOVO GASTO"
                    )
                    Spacer(modifier = Modifier.height(55.dp))
                }

                item {
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
    DescViagens()
}