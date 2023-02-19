package br.com.empiricus.statusviajante.android.viagens

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.R
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.empiricus.statusviajante.android.MyApplicationTheme
import br.com.empiricus.statusviajante.android.components.*
import br.com.empiricus.statusviajante.android.data.ViagemData
import kotlinx.coroutines.launch

@SuppressLint("UnrememberedMutableState", "MutableCollectionMutableState")
@Composable
fun Viagens(
    onNavCadastroViagens: () -> Unit,
    onNavViagem: () -> Unit
) {
    MyApplicationTheme {

        val scope = rememberCoroutineScope()
        val scaffoldState = rememberScaffoldState()

        val viagens = mutableStateListOf<ViagemData>()
        val viagem1 = ViagemData(1, "Euro Trip", "12.02.23", "15.02.23")
        val viagem2 = ViagemData(2, "Ohayou Tokyo", "08.06.23", "25.06.23")
        val viagem3 = ViagemData(3, "Yo no hablo espanol", "08.06.23", "25.06.23")
        val viagem4 = ViagemData(4, "Bora q bora bora", "08.06.23", "25.06.23")
        val viagem5 = ViagemData(5, "Lets go", "08.06.23", "25.06.23")
//        val viagens = MockListaViagens.listaViagem
//        println(viagens)

        viagens.add(viagem1)
        viagens.add(viagem2)
        viagens.add(viagem3)
        viagens.add(viagem4)
        viagens.add(viagem5)

        val filteredViagens = remember {
            mutableStateOf(mutableListOf<ViagemData>(viagem1, viagem2, viagem3, viagem4, viagem5))
        }


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
                    Text(text = "SUAS VIAGENS", fontWeight = FontWeight.Bold, fontSize = 22.sp)

                    Spacer(modifier = Modifier.height(5.dp))

                    SearchBar(onSearch = {
                        val result = viagens.filter { viagem ->
                            viagem.title.lowercase().contains(it.lowercase())
                        }

                        if (result.isNotEmpty()) {
                            filteredViagens.value = result.toMutableStateList()
                        } else {
                            filteredViagens.value = mutableListOf<ViagemData>()
                        }
                    })

                    LazyColumn(
                        modifier = Modifier
                            .fillMaxWidth(1f)
                            .fillMaxHeight(0.87f)
                            .padding(it),
                    ) {
                        item {

                            if (filteredViagens.value.size == 0) {
                                Text(
                                    text = "Ainda não existem viagens" ,
                                    fontWeight = FontWeight.Bold,
                                    textAlign = TextAlign.Center,
                                    modifier = Modifier.fillMaxWidth(),
                                    fontSize = 22.sp
                                )

//                                return@Column
                            }

                            filteredViagens.value.map {
                                listaViagemComponent(
                                    onItemClick = { onNavViagem.invoke() },
                                    id = it.id,
                                    title = it.title,
                                    dataIda = it.dataIda,
                                    dataVolta = it.dataVolta
                                )

                                Spacer(modifier = Modifier.height(5.dp))
                            }
                        }

                    }
                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .fillMaxSize()
                            .absoluteOffset(0.dp, -25.dp)
                    ) {
                        outLinedButtonComponent(
                            onNavigationIconClick = { onNavCadastroViagens.invoke() },
                            title = "ADICIONAR NOVA VIAGEM"
                        )
                        Spacer(modifier = Modifier.height(25.dp))
                    }

//                    items(users.size) {
//                        listaViagemComponent(
//                            onItemClick = { onNavViagem.invoke() },
////                            id = users[it].id,
//                            title = users[it].name,
////                            dataIda = viagens[it].dataInicio,
////                            dataVolta = viagens[it].dataFinal
//                        )
//                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun PreviewViagens() {
    Viagens({}, {})
}