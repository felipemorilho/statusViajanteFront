package br.com.empiricus.statusviajante.android.gastoViagem

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import br.com.digitalhouse.dhwallet.util.DataResult
import br.com.empiricus.statusviajante.android.MyApplicationTheme
import br.com.empiricus.statusviajante.android.components.*
import br.com.empiricus.statusviajante.integration.model.GastoViagem
import kotlinx.coroutines.launch

@Composable
fun GastosViagem(id:Long, onBack: () -> Boolean) {
    val scope = rememberCoroutineScope()
    val scaffoldState = rememberScaffoldState()
    val viewModel: GastosViagemViewModel = viewModel()
    val gastosState by viewModel.gastoViagemState.collectAsState()

    val valorGasto = remember { mutableStateOf(TextFieldValue()) }
    val moedaSelecionada: MutableState<String> = remember { mutableStateOf("") }
    val moedas = listOf("Real", "Dollar", "Euro", "Libra", "Peso")
    val categoriaSelecionada: MutableState<String> = remember { mutableStateOf("") }
    val categorias = listOf("Lazer", "Hospedagem", "Transporte", "Alimentação", "Outros")
    val dataGasto = remember { mutableStateOf("") }
    val descricaoGasto = remember { mutableStateOf(TextFieldValue()) }

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
            ){

                item {

                    Spacer(modifier = Modifier.height(60.dp))
                    Text(text = "Novo Gasto", fontWeight = FontWeight.Bold, fontSize = 32.sp)
                }

                item {
                    outLinedTextFildComponent(valor = valorGasto, title = "Valor do Gasto")
                }

                item {
                    boxSelector(categorias = moedas, title = "Moeda", selecionado = moedaSelecionada)
                }


                item {
                    boxSelector(categorias = categorias, title = "Categoria", selecionado = categoriaSelecionada)
                }

                item {
                    boxSelectorCalendar(title = "Data", selecionado = dataGasto)
                }

                item {
                    outLinedTextFildComponent(valor = descricaoGasto, title = "Descrição")
                }

                item {
                    Spacer(modifier = Modifier.height(90.dp))

                    when(gastosState) {
                        is DataResult.Loading -> CircularProgressIndicator()
                        is DataResult.Error -> ErrorMessage((gastosState as DataResult.Error).error)
                        is DataResult.Success -> {}
                        else -> {}
                    }
                    outLinedButtonComponent(onNavigationIconClick = {
                        viewModel.postGastos(
                            gastoViagem = GastoViagem(
                                dataGasto = dataGasto.value,
                                categoria = categoriaSelecionada.value,
                                valorGasto = valorGasto.value.text.toDouble(),
                                moeda = moedaSelecionada.value,
                                descricaoGasto = descricaoGasto.value.text
                            ),
                            id = id
                        )
                    }, title = "Cadastrar Gasto")
                }
            }
        }
    }
}
