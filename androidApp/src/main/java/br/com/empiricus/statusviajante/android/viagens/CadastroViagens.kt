package br.com.empiricus.statusviajante.android.viagens

import ConfirmAlertDialog
import MyAlertDialog
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import br.com.empiricus.statusviajante.android.MyApplicationTheme
import br.com.empiricus.statusviajante.android.components.*
import br.com.empiricus.statusviajante.integration.model.Viagem
import br.com.empiricus.statusviajante.integration.util.DataResult
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun cadastroViagens(
    onBack: () -> Boolean,
    onNavHome: () -> Unit,
    onNavLogin: () -> Unit,
    onNavDadosUsuario: () -> Unit,
) {

    val scope = rememberCoroutineScope()
    val scaffoldState = rememberScaffoldState()
    val viewModel: ViagensViewModel = viewModel()
    val viagemState by viewModel.viagemState.collectAsState()
    val focusManager = LocalFocusManager.current


    val nomeViagem = remember { mutableStateOf(TextFieldValue()) }
    val origem = remember { mutableStateOf(TextFieldValue()) }
    val destino = remember { mutableStateOf(TextFieldValue()) }
    val dataInicio = remember { mutableStateOf(TextFieldValue()) }
    val dataFinal = remember { mutableStateOf(TextFieldValue()) }
    val orcamentoTotal = remember { mutableStateOf(TextFieldValue()) }
    val quantidadeVianjantes = remember { mutableStateOf(TextFieldValue()) }
    val descricao = remember { mutableStateOf(TextFieldValue()) }
    val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")

    val navLogin = remember { mutableStateOf(false) }
    val errorNome = remember { mutableStateOf(false) }
    val errorOrigem = remember { mutableStateOf(false) }
    val errorDestino = remember { mutableStateOf(false) }
    val errorDataInicio = remember { mutableStateOf(false) }
    val errorDataFinal = remember { mutableStateOf(false) }
    val errorOrcamento = remember { mutableStateOf(false) }
    val errorQuantidadeViajantes = remember { mutableStateOf(false) }
    val errorDescricao = remember { mutableStateOf(false) }



    MyApplicationTheme {
        Scaffold(
            scaffoldState = scaffoldState,
            topBar = {topBarComponent()},
            bottomBar = { bottonBarComponent(
                onBack = {onBack.invoke()},
                onNavDrawer = {
                    scope.launch { scaffoldState.drawerState.open() }
                }
            )},
            drawerGesturesEnabled = scaffoldState.drawerState.isOpen,
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
            }
        ){
            if (navLogin.value){
                ConfirmAlertDialog(
                    title = "Atenção",
                    message = "Você deseja sair da sua conta?",
                    onConfirm = { onNavLogin.invoke() },
                    onCancel = {onBack.invoke()}
                )
            }
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
                    .padding(it)
                    .clickable(
                        indication = null,
                        interactionSource = remember { MutableInteractionSource() }) {
                        focusManager.clearFocus()
                    },
                verticalArrangement = Arrangement.spacedBy(28.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                item {
                    Spacer(modifier = Modifier.height(35.dp))
                    outLinedTextFildComponent(valor = nomeViagem, title = "Nome da viagem")
                    if (errorNome.value){
                        MyAlertDialog(
                            title = "Erro",
                            message = "Nome da viagem deve ser preenchida",
                            onDismiss = {}
                        )
                    }
                }

                item {
                    Row(
                        modifier = Modifier.fillMaxWidth(0.85f)
                    ) {

                        Column(
                            modifier = Modifier.fillMaxWidth(0.5f),
                            horizontalAlignment = Alignment.Start
                        ) {
                            outLinedTextFildComponent(
                                modifier = Modifier.fillMaxWidth(0.95f),
                                valor = origem,
                                title = "Origem"
                            )
                            if (errorOrigem.value){
                                MyAlertDialog(
                                    title = "Erro",
                                    message = "Origem deve ser preenchida",
                                    onDismiss = {}
                                )
                            }
                        }

                        Column(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalAlignment = Alignment.End
                        ) {
                            outLinedTextFildComponent(
                                modifier = Modifier.fillMaxWidth(0.95f),
                                valor = destino,
                                title = "Destino"
                            )
                            if (errorDestino.value){
                                MyAlertDialog(
                                    title = "Erro",
                                    message = "Destino deve ser preenchida",
                                    onDismiss = {}
                                )
                            }
                        }
                    }
                }

                item {
                    Row(
                        modifier = Modifier.fillMaxWidth(0.85f)
                    ) {
                        Column(
                            modifier = Modifier.fillMaxWidth(0.5f),
                            horizontalAlignment = Alignment.Start
                        ) {
                            boxSelectorCalendar(
                                modifier = Modifier.fillMaxWidth(0.95f),
                                title = "Data inicio",
                                selecionado = dataInicio
                            )
                            if (errorDataInicio.value){
                                MyAlertDialog(
                                    title = "Erro",
                                    message = "Data inicial deve ser preenchida",
                                    onDismiss = {}
                                )
                            }
                            Spacer(modifier = Modifier.height(25.dp))
                            outLinedTextFildComponent(
                                modifier = Modifier.fillMaxWidth(0.95f),
                                valor = orcamentoTotal,
                                title = "Orçamento total",
                                keyboardType = KeyboardType.Number
                            )
                            if (errorOrcamento.value){
                                MyAlertDialog(
                                    title = "Erro",
                                    message = "Orçamento deve ser preenchida \nOrçamento não pode ser menor do que 1",
                                    onDismiss = {}
                                )
                            }
                        }

                        Column(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalAlignment = Alignment.End
                        ) {
                            boxSelectorCalendar(
                                modifier = Modifier.fillMaxWidth(0.95f),
                                title = "Data final",
                                selecionado = dataFinal
                            )
                            if (errorDataFinal.value){
                                MyAlertDialog(
                                    title = "Erro",
                                    message = "Data final deve ser preenchida \nData final não pode ser anterior a Data inicial",
                                    onDismiss = {}
                                )
                            }
                            Spacer(modifier = Modifier.height(25.dp))
                            outLinedTextFildComponent(
                                modifier = Modifier.fillMaxWidth(0.95f),
                                valor = quantidadeVianjantes,
                                title = "Nº de viajantes",
                                keyboardType = KeyboardType.Number
                            )
                            if (errorQuantidadeViajantes.value){
                                MyAlertDialog(
                                    title = "Erro",
                                    message = "Nº de viajantes deve ser preenchida",
                                    onDismiss = {}
                                )
                            }
                        }
                    }
                }

                item {
                    outLinedTextFildComponent(valor = descricao, title = "Descrição")
                    if (errorDescricao.value){
                        MyAlertDialog(
                            title = "Erro",
                            message = "Descrição deve ser preenchida",
                            onDismiss = {}
                        )
                    }
                    Spacer(modifier = Modifier.height(25.dp))
                }

                item {
                    when(viagemState) {
                        is DataResult.Loading -> { LoadingIndicator() }
                        is DataResult.Error -> { ErrorMessage((viagemState as DataResult.Error).error) }
                        is DataResult.Success -> {MyAlertDialog(
                            title = "Uhuuu!!",
                            message = "Viagem cadastrada com sucesso",
                            onDismiss = {onBack.invoke()}
                        )}
                        else -> {}
                    }
                    outLinedButtonComponent(
                        onNavigationIconClick = {
                            when{ nomeViagem.value.text.isEmpty() -> errorNome.value = true else -> errorNome.value = false }
                            when{ origem.value.text.isEmpty() -> errorOrigem.value = true else -> errorOrigem.value = false }
                            when{ destino.value.text.isEmpty() -> errorDestino.value = true else -> errorDestino.value = false }
                            when{ dataInicio.value.text.isEmpty() -> errorDataInicio.value = true else -> errorDataInicio.value = false }
                            when{ dataFinal.value.text.isEmpty() || LocalDate.parse(dataFinal.value.text, formatter) < LocalDate.parse(dataInicio.value.text, formatter) -> errorDataFinal.value = true else -> errorDataFinal.value = false }
                            when{ orcamentoTotal.value.text.isEmpty() || orcamentoTotal.value.text.toDouble() <= 0 -> errorOrcamento.value = true else -> errorOrcamento.value = false }
                            when{ quantidadeVianjantes.value.text.isEmpty() -> errorQuantidadeViajantes.value = true else -> errorQuantidadeViajantes.value = false }
                            when{ descricao.value.text.isEmpty() -> errorDescricao.value = true else -> errorDescricao.value = false }

                            when{nomeViagem.value.text.isNotEmpty() && origem.value.text.isNotEmpty() && destino.value.text.isNotEmpty() && dataInicio.value.text.isNotEmpty() &&
                                    dataFinal.value.text.isNotEmpty() && !errorDataFinal.value && orcamentoTotal.value.text.isNotEmpty() && !errorOrcamento.value &&
                                    quantidadeVianjantes.value.text.isNotEmpty() && descricao.value.text.isNotEmpty() ->

                                viewModel.postViagem(viagem = Viagem(
                                    nome = nomeViagem.value.text,
                                    origem = origem.value.text,
                                    destino = destino.value.text,
                                    dataIda = dataInicio.value.text,
                                    dataVolta = dataFinal.value.text,
                                    diasDeViagem = 0,
                                    gastoTotal = 0.0,
                                    orcamento = orcamentoTotal.value.text.toDouble(),
                                    qtdPessoas = quantidadeVianjantes.value.text.toInt(),
                                    descricaoViagem = descricao.value.text
                                ))
                            }
                        },
                        title = "Cadastrar viagem")
                    Spacer(modifier = Modifier.height(25.dp))
                }
            }
        }
    }
}