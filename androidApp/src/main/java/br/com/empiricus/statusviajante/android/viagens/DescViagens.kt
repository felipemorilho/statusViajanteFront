package br.com.empiricus.statusviajante.android.viagens

import android.annotation.SuppressLint
import android.icu.lang.UCharacter
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.empiricus.statusviajante.android.MyApplicationTheme
import br.com.empiricus.statusviajante.android.components.*
import br.com.empiricus.statusviajante.android.data.GastosData
import br.com.empiricus.statusviajante.android.data.ViagemData
import br.com.empiricus.statusviajante.model.MockGastoViagem
import br.com.empiricus.statusviajante.model.MockListaViagens
import kotlinx.coroutines.launch

@SuppressLint("UnrememberedMutableState", "MutableCollectionMutableState")
@Composable
fun DescViagens(
    onNavNovoGasto: () -> Unit,
    onBack: () -> Boolean
) {
    MyApplicationTheme {

        val scope = rememberCoroutineScope()
        val scaffoldState = rememberScaffoldState()

        val gastosTotais = MockGastoViagem.gastosTotais
        val viagem = MockListaViagens.listaViagem

        val gastos = mutableStateListOf<GastosData>()
        val gasto1 = GastosData(1, "Pagamento Hotel", 50.0)
        val gasto2 = GastosData(2, "Taxi", 60.0)
        val gasto3 = GastosData(3, "Ingresso show", 75.0)
        val gasto4 = GastosData(4, "Restaurante", 37.0)
        val gasto5 = GastosData(5, "Paque Aquatico", 59.0)

        gastos.add(gasto1)
        gastos.add(gasto2)
        gastos.add(gasto3)
        gastos.add(gasto4)
        gastos.add(gasto5)

        val filteredGastos = remember {
            mutableStateOf(mutableListOf<GastosData>(gasto1, gasto2, gasto3, gasto4, gasto5))
        }

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
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
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
                    modifier = Modifier
                        .fillMaxWidth(0.85f)
                        .fillMaxHeight(1f)
                        .padding(it),
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Spacer(modifier = Modifier.height(25.dp))
                    Text(
                        text = UCharacter.toUpperCase(viagem[0].nome),
                        fontWeight = FontWeight.Bold,
                        fontSize = 22.sp,
                    )
                    Spacer(modifier = Modifier.height(7.dp))

                    Column(modifier = Modifier.fillMaxWidth(1f)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(1f),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(text = "DATAS", fontWeight = FontWeight.Bold, fontSize = 15.sp)

                            Text(
                                text = "${viagem[0].dataInicio} - ${viagem[0].dataFinal}",
                                fontWeight = FontWeight.Bold,
                                fontSize = 15.sp
                            )

                        }
                        Row(
                            modifier = Modifier.fillMaxWidth(1f),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(text = "GASTOS", fontWeight = FontWeight.Bold, fontSize = 15.sp)

                            Text(
                                text = gastosTotais.toString(),
                                fontWeight = FontWeight.Bold,
                                fontSize = 15.sp
                            )

                        }
                        Row(
                            modifier = Modifier.fillMaxWidth(1f),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(text = "DESCRIÇÃO", fontWeight = FontWeight.Bold, fontSize = 15.sp)

                            Text(
                                text = viagem[0].descricao,
                                fontWeight = FontWeight.Bold,
                                fontSize = 15.sp
                            )

                        }
                    }

//                    Spacer(modifier = Modifier.height(5.dp))

                    SearchBar(onSearch = {
                        val result = gastos.filter { gasto ->
                            gasto.title.lowercase().contains(it.lowercase())
                        }

                        if (result.isNotEmpty()) {
                            filteredGastos.value = result.toMutableStateList()
                        } else {
                            filteredGastos.value = mutableListOf<GastosData>()
                        }
                    })

                    LazyColumn(
                        modifier = Modifier
                            .fillMaxWidth(1f)
                            .fillMaxHeight(0.83f)
                            .padding(it),
                    ) {
                        item {

                            if (filteredGastos.value.size == 0) {
                                Text(
                                    text = "Ainda não existem gastos",
                                    fontWeight = FontWeight.Bold,
                                    textAlign = TextAlign.Center,
                                    modifier = Modifier.fillMaxWidth(),
                                    fontSize = 22.sp
                                )

//                                return@Column
                            }

                            filteredGastos.value.map {
                                DescViagemComponent(
                                    id = it.id,
                                    title = it.title,
                                    valor = it.valor,
                                    onItemClick = {}
                                )

                                Spacer(modifier = Modifier.height(5.dp))
                            }
                        }
                    }
                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.fillMaxSize()
                            .absoluteOffset(0.dp, -55.dp)
                    ) {
                        outLinedButtonComponent(
                            onNavigationIconClick = { onNavNovoGasto.invoke() },
                            title = "ADICIONAR NOVO GASTO"
                        )

                        Spacer(modifier = Modifier.height(10.dp))

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
}


@Preview
@Composable
fun PreviewDescViagens() {
    DescViagens({}, { true })
}