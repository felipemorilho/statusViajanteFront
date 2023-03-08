package br.com.empiricus.statusviajante.android.gastoViagem

import ConfirmAlertDialog
import MyAlertDialog
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import br.com.empiricus.statusviajante.android.MyApplicationTheme
import br.com.empiricus.statusviajante.android.components.*
import br.com.empiricus.statusviajante.integration.model.GastoViagem
import br.com.empiricus.statusviajante.integration.util.DataResult
import kotlinx.coroutines.launch

@Composable
fun EditarGastosViagem(
    id: String,
    onBack: () -> Boolean,
    onNavHome: () -> Unit,
    onNavLogin: () -> Unit,
    onNavCadastroViagens: () -> Unit,
    onNavDadosUsuario: () -> Unit,
) {
    val scope = rememberCoroutineScope()
    val scaffoldState = rememberScaffoldState()
    val viewModel: GastosViagemViewModel = viewModel()
    val gastosState by viewModel.gastoViagemState.collectAsState()
    val focusManager = LocalFocusManager.current
    val attGastos = remember { mutableStateOf(false) }
    val navLogin = remember { mutableStateOf(false) }

    viewModel.getGastosById(id.toLong())

    MyApplicationTheme {
        Scaffold(
            topBar = { topBarComponent() },
            bottomBar = {
                bottonBarComponent(
                    onBack = { onBack.invoke() },
                    onNavDrawer = {
                        scope.launch { scaffoldState.drawerState.open() }
                    })
            },
            drawerContent = {
                drawerHeader()
                drawerBody(
                    itens = listaItensDrawer(),
                    onItemClick = {
                        when(it.id) {
                            "Home" -> {
                                onBack.invoke()
                                onNavHome.invoke()
                            }
                            "Criar Viagem" -> {
                                onBack.invoke()
                                onNavCadastroViagens.invoke()
                            }
                            "Meus Dados" -> {
                                onBack.invoke()
                                onNavDadosUsuario.invoke()
                            }
                            "Logout" -> {
                                navLogin.value = true
                            }
                        }
                    }
                )
            },
            floatingActionButton = {
                MyFloatingActionButtonDelete(
                    onConfirm = {
                        viewModel.deleteGastos(id.toLong())
                        onBack.invoke()
                    },
                    onCancel = {},
                    message = "Você está tentando deletar este Gasto!! \n" +
                            "Deseja prosseguir com a exclusão?",
                    title = "ATENÇÃO"
                )
            },
            floatingActionButtonPosition = FabPosition.Center,
            isFloatingActionButtonDocked = true,
        ) {
            if (navLogin.value){
            ConfirmAlertDialog(
                title = "Atenção",
                message = "Você deseja sair da sua conta?",
                onConfirm = { onNavLogin.invoke() },
                onCancel = {onBack.invoke()}
            )
        }
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
                    .padding(it)
                    .clickable(
                        indication = null,
                        interactionSource = remember { MutableInteractionSource() }) {
                        focusManager.clearFocus()
                    },
                verticalArrangement = Arrangement.spacedBy(25.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                when (gastosState) {
                    is DataResult.Loading -> CircularProgressIndicator()
                    is DataResult.Success -> {
                        ContentEditGastos(gastosState as DataResult.Success<GastoViagem>, viewModel = viewModel, onClick = {attGastos.value = true})}
                    else -> {}
                }
                when{
                    gastosState is DataResult.Success && attGastos.value -> {
                        MyAlertDialog(
                            title = "Uhuuu!!",
                            message = "Viagem Alterada com sucesso",
                            onDismiss = {onBack.invoke()}
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun ContentEditGastos(
    retorno: DataResult.Success<GastoViagem>,
    viewModel: GastosViagemViewModel,
    onClick: () -> Unit
){
    val gasto = retorno.data

    val valorGasto = remember { mutableStateOf(TextFieldValue(gasto.valorGasto.toString())) }
    val moedas = listOf("Real", "Dollar", "Euro", "Libra", "Peso")
    val moedaSelecionada = remember { mutableStateOf(TextFieldValue(gasto.moeda)) }
    val categorias = listOf("Lazer", "Hospedagem", "Transporte", "Alimentação", "Outros")
    val categoriaSelecionada = remember { mutableStateOf(TextFieldValue(gasto.categoria)) }
    val dataGasto = remember { mutableStateOf(TextFieldValue(gasto.dataGasto)) }
    val descricaoGasto = remember { mutableStateOf(TextFieldValue(gasto.descricaoGasto)) }
    val color = MaterialTheme.colors.secondary

    val erroGasto = remember { mutableStateOf(false) }
    val erroMoeda = remember { mutableStateOf(false) }
    val erroCategoria = remember { mutableStateOf(false) }
    val erroData = remember { mutableStateOf(false) }
    val erroDescricao = remember { mutableStateOf(false) }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(25.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ){

        item {

            Spacer(modifier = Modifier.height(60.dp))
            Text(text = gasto.descricaoGasto, fontWeight = FontWeight.Bold, fontSize = 32.sp)
        }

        item {
            outLinedTextFildComponent(valor = valorGasto, title = "Valor do Gasto", keyboardType = KeyboardType.Number)
            if (erroGasto.value){
                Text(
                    modifier = Modifier.fillMaxWidth(0.8f),
                    textAlign = TextAlign.Left,
                    text = "* Valor do gasto deve ser preenchido \n" +
                            "* Valor do gasto não pode ser menor que 0",
                    color = color
                )
            }
        }

        item {
            boxSelector(categorias = moedas, title = "Moeda", selecionado = moedaSelecionada)
            if (erroMoeda.value){
                Text(
                    modifier = Modifier.fillMaxWidth(0.8f),
                    textAlign = TextAlign.Left,
                    text = "* Moeda utilizada deve ser preenchida",
                    color = color
                )
            }
        }


        item {
            boxSelector(categorias = categorias, title = "Categoria", selecionado = categoriaSelecionada)
            if (erroCategoria.value){
                Text(
                    modifier = Modifier.fillMaxWidth(0.8f),
                    textAlign = TextAlign.Left,
                    text = "* Categoria de gasto deve ser preenchida",
                    color = color
                )
            }
        }

        item {
            boxSelectorCalendar(title = "Data", selecionado = dataGasto)
            if (erroData.value){
                Text(
                    modifier = Modifier.fillMaxWidth(0.8f),
                    textAlign = TextAlign.Left,
                    text = "* Data do gasto deve ser preenchida",
                    color = color
                )
            }
        }

        item {
            outLinedTextFildComponent(valor = descricaoGasto, title = "Descrição")
            if (erroDescricao.value){
                Text(
                    modifier = Modifier.fillMaxWidth(0.8f),
                    textAlign = TextAlign.Left,
                    text = "* Descrição deve ser preenchida",
                    color = color
                )
            }
        }

        item {
            outLinedButtonComponent(onNavigationIconClick = {

                when{ valorGasto.value.text.isEmpty() || valorGasto.value.text.toDouble() <= 0 -> erroGasto.value = true else -> erroGasto.value = false }
                when{ moedaSelecionada.value.text.isEmpty() -> erroMoeda.value = true else -> erroMoeda.value = false }
                when{ categoriaSelecionada.value.text.isEmpty() -> erroCategoria.value = true else -> erroCategoria.value = false }
                when{ dataGasto.value.text.isEmpty() -> erroData.value = true else -> erroData.value = false }
                when{ descricaoGasto.value.text.isEmpty() -> erroDescricao.value = true else -> erroDescricao.value = false }
                when{valorGasto.value.text.isNotEmpty() && !erroGasto.value && moedaSelecionada.value.text.isNotEmpty() && categoriaSelecionada.value.text.isNotEmpty() && dataGasto.value.text.isNotEmpty() && descricaoGasto.value.text.isNotEmpty() -> {
                    onClick.invoke()
                    viewModel.putGastos(
                            id = gasto.id,
                            gastoViagem = GastoViagem(
                                dataGasto = dataGasto.value.text,
                                categoria = categoriaSelecionada.value.text,
                                valorGasto = valorGasto.value.text.toDouble(),
                                moeda = moedaSelecionada.value.text,
                                descricaoGasto = descricaoGasto.value.text
                            ),
                        )
                    }
                }
            }, title = "Atualizar Gasto")
            Spacer(modifier = Modifier.height(25.dp))
        }
    }
}

