package br.com.empiricus.statusviajante.android.viagens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import br.com.empiricus.statusviajante.integration.util.DataResult
import br.com.empiricus.statusviajante.android.MyApplicationTheme
import br.com.empiricus.statusviajante.android.components.*
import br.com.empiricus.statusviajante.integration.model.Viagem
import kotlinx.coroutines.launch


@Composable
fun Viagens(onNavCadastroViagens: () -> Unit, onNavViagem: () -> Unit, onItemDetail: (Long) -> Unit,) {

    val scope = rememberCoroutineScope()
    val viewModel: ViagensViewModel = viewModel()
    val viagensState by viewModel.viagensState.collectAsState()
    val context = LocalContext.current

    viewModel.getViagens()


    MyApplicationTheme {
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
                    onItemClick = {}
                )
            }

        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it)
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                MaterialTheme.colors.secondaryVariant,
                                MaterialTheme.colors.primaryVariant
                            )
                        )
                    )
            ) {

                Spacer(modifier = Modifier.height(25.dp))
                Text(text = "SUAS VIAGENS", fontWeight = FontWeight.Bold, fontSize = 22.sp)

                when(viagensState){
                    is DataResult.Loading -> {
                        LoadingIndicator()
                    }
                    is DataResult.Error -> {
                        ErrorMessage((viagensState as DataResult.Error).error)
                    }
                    is DataResult.Success -> {
                        ContenteViagens(viagensState as DataResult.Success<List<Viagem>>, onItemDetail)
                    }
                    else -> {}
                }

                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxSize()
                ) {
                    outLinedButtonComponent(
                        onNavigationIconClick = { onNavCadastroViagens.invoke() },
                        title = "ADICIONAR NOVA VIAGEM"
                    )
                    Spacer(modifier = Modifier.height(25.dp))
                }
            }
        }
    }
}

@Composable
fun ContenteViagens(
    retorno: DataResult.Success<List<Viagem>>,
    onItemDetail: (Long) -> Unit
){
    val viagens = retorno.data


    LazyColumn(
        modifier = Modifier
            .fillMaxWidth(0.8f)
            .fillMaxHeight(0.65f),
        verticalArrangement = Arrangement.spacedBy(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        items(viagens.size){
            val color = when{
                viagens[it].gastoTotal >= (viagens[it].orcamento * 50 / 100) && viagens[it].gastoTotal < (viagens[it].orcamento * 85 / 100) ->
                    Color.Yellow
                viagens[it].gastoTotal >= (viagens[it].orcamento * 85 / 100) ->
                    Color.Red
                else -> {
                    Color.Transparent
                }
            }
            listaViagemComponent(
                color = color,
                onItemClick = { onItemDetail.invoke(viagens[it].id) },
                title = viagens[it].nome,
                dataIda = viagens[it].dataIda,
                dataVolta = viagens[it].dataVolta
            )
        }
    }

}


@Preview
@Composable
fun previewViagens() {
    Viagens({}, {}) {}
}