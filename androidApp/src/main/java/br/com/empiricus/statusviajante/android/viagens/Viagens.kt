package br.com.empiricus.statusviajante.android.viagens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
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
import br.com.empiricus.statusviajante.android.components.*
import br.com.empiricus.statusviajante.model.MockListaViagens
import kotlinx.coroutines.launch


@Composable
fun Viagens(onNavCadastroViagens: () -> Unit, onNavViagem: () -> Unit) {
    MyApplicationTheme {
        val scope = rememberCoroutineScope()
        val scaffoldState = rememberScaffoldState()
        Scaffold(
            scaffoldState = scaffoldState,
            topBar = { topBarComponent() },
            bottomBar = { bottonBarComponent(
                colorBackButton = Color.Transparent,
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

        ) { it ->
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

                item {
                    Spacer(modifier = Modifier.height(25.dp))
                    Text(text = "SUAS VIAGENS", fontWeight = FontWeight.Bold, fontSize = 22.sp)
                }
                val viagens = MockListaViagens.listaViagem
                items(viagens.size){
                    listaViagemComponent(
                        onItemClick = { onNavCadastroViagens.invoke() },
                        id = viagens[it].id,
                        title = viagens[it].nome,
                        dataIda = viagens[it].dataInicio,
                        dataVolta = viagens[it].dataFinal
                    )

                }
                
                item {
                    outLinedButtonComponent(
                        onNavigationIconClick = {onNavCadastroViagens.invoke()},
                        title = "ADICIONAR NOVA VIAGEM"
                    )
                    Spacer(modifier = Modifier.height(25.dp))
                }
            }
        }
    }
}


@Preview
@Composable
fun previewViagens() {
    Viagens({},{})
}