package br.com.empiricus.statusviajante.android.viagens

import MyAlertDialog
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
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
import java.time.temporal.ChronoUnit

@Composable
fun EditarViagens(id: String, onBack: () -> Boolean) {

    val scope = rememberCoroutineScope()
    val scaffoldState = rememberScaffoldState()
    val viewModel: ViagensViewModel = viewModel()
    val viagemState by viewModel.viagemState.collectAsState()
    val attViagem = remember { mutableStateOf(false) }

    viewModel.getViagensById(id.toLong())

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
                    onItemClick = {
                        when (it.id) {

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
                    .padding(it)
            ) {
                when (viagemState) {
                    is DataResult.Loading -> { LoadingIndicator() }
                    is DataResult.Error -> { ErrorMessage((viagemState as DataResult.Error).error) }
                    is DataResult.Success -> {
                        ContentEditViagem(
                            retorno = viagemState as DataResult.Success<Viagem>,
                            viewModel = viewModel,
                            onClick = {attViagem.value = true}
                        )
                    }
                    else -> {}
                }
                when{
                    viagemState is DataResult.Success && attViagem.value -> {
                        MyAlertDialog(
                            title = "Uhuuu!!",
                            message = "Viagem Alterada com sucesso",
                            onDismiss = {onBack.invoke()}
                        )}
                }
            }
        }
    }
}

@Composable
fun ContentEditViagem(
    retorno: DataResult.Success<Viagem>,
    viewModel: ViagensViewModel,
    onClick: () -> Unit
){
    val viagem = retorno.data

    val nomeViagem = remember { mutableStateOf(TextFieldValue(viagem.nome)) }
    val origem = remember { mutableStateOf(TextFieldValue(viagem.origem)) }
    val destino = remember { mutableStateOf(TextFieldValue(viagem.destino)) }
    val dataInicio = remember { mutableStateOf(TextFieldValue(viagem.dataIda)) }
    val dataFinal = remember { mutableStateOf(TextFieldValue(viagem.dataVolta)) }
    val orcamentoTotal = remember { mutableStateOf(TextFieldValue(viagem.orcamento.toString())) }
    val quantidadeVianjantes = remember { mutableStateOf(TextFieldValue(viagem.qtdPessoas.toString())) }
    val descricao = remember { mutableStateOf(TextFieldValue(viagem.descricaoViagem)) }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(28.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            Spacer(modifier = Modifier.height(35.dp))
            outLinedTextFildComponent(valor = nomeViagem, title = "Nome da viagem")
        }

        item {
            Row(
                modifier = Modifier.fillMaxWidth(0.78f)
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
                }
            }
        }

        item {
            Row(
                modifier = Modifier.fillMaxWidth(0.78f)
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
                    Spacer(modifier = Modifier.height(25.dp))
                    outLinedTextFildComponent(
                        modifier = Modifier.fillMaxWidth(0.95f),
                        valor = orcamentoTotal,
                        title = "Orçamento total",
                        keyboardType = KeyboardType.Number
                    )
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
                    Spacer(modifier = Modifier.height(25.dp))
                    outLinedTextFildComponent(
                        modifier = Modifier.fillMaxWidth(0.95f),
                        valor = quantidadeVianjantes,
                        title = "Nº de viajantes",
                        keyboardType = KeyboardType.Number
                    )
                }
            }
        }

        item {
            outLinedTextFildComponent(valor = descricao, title = "Descrição")
            Spacer(modifier = Modifier.height(25.dp))
        }
        item {

            outLinedButtonComponent(
                onNavigationIconClick = {
                    viewModel.putViagem(id = viagem.id, viagem = Viagem(
                        nome = nomeViagem.value.text,
                        diasDeViagem =  1,
                        origem = origem.value.text,
                        destino = destino.value.text,
                        dataIda = dataInicio.value.text,
                        dataVolta = dataFinal.value.text,
                        gastoTotal = viagem.gastoTotal,
                        orcamento = orcamentoTotal.value.text.toDouble(),
                        qtdPessoas = quantidadeVianjantes.value.text.toInt(),
                        descricaoViagem = descricao.value.text
                    ))
                    onClick.invoke()
                },
                title = "Salvar mudanças")
            Spacer(modifier = Modifier.height(25.dp))
        }
    }
}
