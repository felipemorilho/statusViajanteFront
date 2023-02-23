package br.com.empiricus.statusviajante.android.gastoViagem

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
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
import androidx.lifecycle.viewmodel.compose.viewModel
import br.com.digitalhouse.dhwallet.util.DataResult
import br.com.empiricus.statusviajante.android.MyApplicationTheme
import br.com.empiricus.statusviajante.android.components.*
import br.com.empiricus.statusviajante.android.gastoViagem.GastosViagemViewModel
import br.com.empiricus.statusviajante.integration.model.GastoViagem
import br.com.empiricus.statusviajante.integration.model.Viagem

import kotlinx.coroutines.launch
import br.com.empiricus.statusviajante.android.components.DescViagemComponent as DescViagemComponent

@Composable
fun EditGasto(id: String, onBack: () -> Boolean) {
    val scope = rememberCoroutineScope()
    val scaffoldState = rememberScaffoldState()
    val gastosViewModel: GastosViagemViewModel = viewModel()
    gastosViewModel.getGastosViagem(id.toLong())
    val gastoViagemState by gastosViewModel.gastosViagemState.collectAsState()

//    var selecionado: MutableState<String> = remember { mutableStateOf("") }
//    val valorGasto = remember { mutableStateOf(TextFieldValue())}
//    val moeda = remember { mutableStateOf(TextFieldValue())}
//    val categoria = listOf("Alimentação", "Hospedagem", "Transporte", "Lazer", "Outros")
//    val dataGasto = remember { mutableStateOf(TextFieldValue("")) }
//    val descricaoGasto = remember { mutableStateOf(TextFieldValue()) }

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
            Column(
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

                when(gastoViagemState) {
                    is DataResult.Loading -> CircularProgressIndicator()
                    is DataResult.Error -> ErrorMessage((gastoViagemState as DataResult.Error).error)
                    is DataResult.Success -> contentEditGasto((gastoViagemState as DataResult.Success<GastoViagem>))
                    else -> {}
                }


            }

        }
    }
}

@Composable
fun contentEditGasto (
    retorno: DataResult.Success<GastoViagem>
){

    val gasto = retorno.data

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
                Text(text = "Editar Gasto", fontWeight = FontWeight.Bold, fontSize = 22.sp)
            }

        }

        Row (
            modifier = Modifier.fillMaxWidth(0.8f),
            horizontalArrangement = Arrangement.Start
        ) {

            Spacer(modifier = Modifier.height(60.dp))
            Text(text = "Editar Gasto", fontWeight = FontWeight.Bold, fontSize = 32.sp)
        }

        Row (
            modifier = Modifier.fillMaxWidth(0.8f),
            horizontalArrangement = Arrangement.Start
        ) {
            Text(text = gasto.valorGasto.toString(), fontWeight = FontWeight.Bold, fontSize = 15.sp)
        }

            Column(
                modifier = Modifier.fillMaxWidth(0.95f),
                horizontalAlignment = Alignment.End
            ) {
                Text(text = gasto.valorGasto.toString(), fontWeight = FontWeight.Bold, fontSize = 15.sp)
            }

        Row (
            modifier = Modifier.fillMaxWidth(0.8f),
            horizontalArrangement = Arrangement.Start
        ) {
            Text(text = gasto.moeda, fontWeight = FontWeight.Bold, fontSize = 15.sp)
        }

        val moedaSelecionada: MutableState<String> = remember { mutableStateOf("") }
        val moedas = listOf("Real", "Dollar", "Euro", "Libra", "Peso")

            Column(
                modifier = Modifier.fillMaxWidth(0.95f),
                horizontalAlignment = Alignment.End
            ) {
                boxSelector(categorias = moedas, title = "Moeda", selecionado = moedaSelecionada)
            }

        Row (
            modifier = Modifier.fillMaxWidth(0.8f),
            horizontalArrangement = Arrangement.Start
        ) {
            Text(text = gasto.categoria, fontWeight = FontWeight.Bold, fontSize = 15.sp)
        }

        val categoriaSelecionada: MutableState<String> = remember { mutableStateOf("") }
        val categorias = listOf("Lazer", "Hospedagem", "Transporte", "Alimentação", "Outros")

        Column(
            modifier = Modifier.fillMaxWidth(0.95f),
            horizontalAlignment = Alignment.End
        ) {
            boxSelector(categorias = categorias, title = "Categoria", selecionado = categoriaSelecionada)
        }

        Row (
            modifier = Modifier.fillMaxWidth(0.8f),
            horizontalArrangement = Arrangement.Start
        ) {
            Text(text = gasto.dataGasto.toString(), fontWeight = FontWeight.Bold, fontSize = 15.sp)
        }

        Column(
            modifier = Modifier.fillMaxWidth(0.95f),
            horizontalAlignment = Alignment.End
        ) {
            Text(text = gasto.dataGasto.toString(), fontWeight = FontWeight.Bold, fontSize = 15.sp)
        }

        Row (
            modifier = Modifier.fillMaxWidth(0.8f),
            horizontalArrangement = Arrangement.Start
        ) {
            Text(text = gasto.descricaoGasto, fontWeight = FontWeight.Bold, fontSize = 15.sp)
        }

        Column(
            modifier = Modifier.fillMaxWidth(0.95f),
            horizontalAlignment = Alignment.End
        ) {
            Text(text = gasto.descricaoGasto, fontWeight = FontWeight.Bold, fontSize = 15.sp)
        }

        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
    //                    outLinedButtonComponent(onNavigationIconClick = {
    //                        viewModel.putGastos(
    //                            gastoViagem = GastoViagem(
    //                                dataGasto = dataGasto.value,
    //                                categoria = categoriaSelecionada.value,
    //                                valorGasto = valorGasto.value.text.toDouble(),
    //                                moeda = moedaSelecionada.value,
    //                                descricaoGasto = descricaoGasto.value.text
    //                            ),
    //                            id = id
    //                        )
    //                    }, title = "Cadastrar Gasto")
            DescViagemComponent(
                onItemClick = { /*TODO*/ },
                id = gasto.id,
                dataGasto = gasto.dataGasto,
                valor = gasto.valorGasto,
                moeda = gasto.moeda,
                categoria = gasto.categoria,
                descricao = gasto.descricaoGasto
            )
        }
        Spacer(modifier = Modifier.height(25.dp))
    }


