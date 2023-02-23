package br.com.empiricus.statusviajante.android.gastoViagem

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import br.com.digitalhouse.dhwallet.util.DataResult
import br.com.empiricus.statusviajante.android.MyApplicationTheme
import br.com.empiricus.statusviajante.android.components.*
import br.com.empiricus.statusviajante.android.viagens.ViagensViewModel
import br.com.empiricus.statusviajante.integration.model.GastoViagem
import br.com.empiricus.statusviajante.integration.model.Viagem
import kotlinx.coroutines.launch


@Composable
fun DescViagens(id: String ,onNavNovoGasto: () -> Unit, onBack: () -> Boolean) {


    val scope = rememberCoroutineScope()
    val scaffoldState = rememberScaffoldState()
    val gastosViewModel: GastosViagemViewModel = viewModel()
    gastosViewModel.getGastosViagem(id.toLong())
    val gastosState by gastosViewModel.gastosViagemState.collectAsState()

    val viagemViewModel: ViagensViewModel = viewModel()
    viagemViewModel.getViagensById(id.toLong())
    val viagemState by viagemViewModel.viagemState.collectAsState()



    MyApplicationTheme {
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
                    onItemClick = {}
                )
            }

        ) {
            Column(
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
                when(viagemState) {
                    is DataResult.Loading -> CircularProgressIndicator()
                    is DataResult.Error -> ErrorMessage((viagemState as DataResult.Error).error)
                    is DataResult.Success -> contentDescriptionViagem((viagemState as DataResult.Success<Viagem>))
                    else -> {}
                }

                when(gastosState) {
                    is DataResult.Loading -> CircularProgressIndicator()
                    is DataResult.Error -> ErrorMessage((gastosState as DataResult.Error).error)
                    is DataResult.Success -> contentDescriptionGastos((gastosState as DataResult.Success<List<GastoViagem>>))
                    else -> {}
                }

                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    outLinedButtonComponent(
                        onNavigationIconClick = { onNavNovoGasto.invoke() },
                        title = "ADICIONAR NOVO GASTO"
                    )
                    Spacer(modifier = Modifier.height(20.dp))

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

@Composable
fun contentDescriptionViagem(
    retorno: DataResult.Success<Viagem>
){
    val viagem = retorno.data
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Row(
            modifier = Modifier.fillMaxWidth(0.8f),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = viagem.nome, fontWeight = FontWeight.Bold, fontSize = 22.sp)
            IconButton(onClick = {

            }) {
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
                Text(text = "${viagem.dataIda} - ${viagem.dataVolta}", fontWeight = FontWeight.Bold, fontSize = 15.sp)
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
                Text(text = viagem.gastoTotal.toString(), fontWeight = FontWeight.Bold, fontSize = 15.sp)
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
                Text(text = viagem.descricaoViagem, fontWeight = FontWeight.Bold, fontSize = 15.sp)
            }
        }
        Spacer(modifier = Modifier.height(17.dp))
    }
}

@Composable
fun contentDescriptionGastos(
    retorno: DataResult.Success<List<GastoViagem>>
){
    val gastos = retorno.data

    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.65f)
            .padding(bottom = 10.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        items(gastos.size) {
            Column(
                modifier = Modifier.fillMaxWidth(0.65f)
            ) {
                DescViagemComponent(
                    onItemClick = { /*TODO*/ },
                    id = gastos[it].id,
                    dataGasto = gastos[it].dataGasto,
                    valor = gastos[it].valorGasto,
                    moeda = gastos[it].moeda,
                    categoria = gastos[it].categoria,
                    descricao = gastos[it].descricaoGasto
                )
            }
        }
    }
}