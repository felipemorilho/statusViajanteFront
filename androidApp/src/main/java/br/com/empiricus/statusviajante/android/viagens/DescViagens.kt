package br.com.empiricus.statusviajante.android.viagens

import ConfirmAlertDialog
import androidx.activity.OnBackPressedCallback
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import br.com.empiricus.statusviajante.android.MyApplicationTheme
import br.com.empiricus.statusviajante.android.components.*
import br.com.empiricus.statusviajante.android.gastoViagem.GastosViagemViewModel
import br.com.empiricus.statusviajante.integration.model.GastoViagem
import br.com.empiricus.statusviajante.integration.model.Viagem
import br.com.empiricus.statusviajante.integration.util.DataResult
import kotlinx.coroutines.launch
import java.text.DecimalFormat


@Composable
fun DescViagens(
    id: String,
    onNavNovoGasto: (Long) -> Unit,
    onBack: () -> Boolean,
    onNavHome: () -> Unit,
    onNavLogin: () -> Unit,
    onNavCadastroViagens: () -> Unit,
    onNavDadosUsuario: () -> Unit,
    onEditViagem: (Long) -> Unit,
    onEditGasto: (Long) -> Unit
) {
    val scope = rememberCoroutineScope()
    val scaffoldState = rememberScaffoldState()
    val gastosViewModel: GastosViagemViewModel = viewModel()
    val gastosState by gastosViewModel.gastosViagemState.collectAsState()
    val viagemViewModel: ViagensViewModel = viewModel()
    val viagemState by viagemViewModel.viagemState.collectAsState()
    val focusManager = LocalFocusManager.current
    val categorias = listOf("Todas","Lazer", "Hospedagem", "Transporte", "Alimentação", "Outros")
    val categoriaSelecionada = remember { mutableStateOf(TextFieldValue()) }
    val navLogin = remember { mutableStateOf(false) }

    viagemViewModel.getViagensById(id.toLong())

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
                        viagemViewModel.deleteViagem(id.toLong())
                        onBack.invoke()
                    },
                    onCancel = {},
                    message = "Você está tentando deletar sua viagem por completo, todos os gastos tambem serão deletados!!! \n" +
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
                    .clickable(
                        indication = null,
                        interactionSource = remember { MutableInteractionSource() }) {
                        focusManager.clearFocus()
                    },
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(20.dp))
                when(viagemState) {
                    is DataResult.Loading -> CircularProgressIndicator()
                    is DataResult.Error -> ErrorMessage((viagemState as DataResult.Error).error)
                    is DataResult.Success -> ContentDescriptionViagem((viagemState as DataResult.Success<Viagem>), onClickEdit = {onEditViagem.invoke(id.toLong())})
                    else -> {}
                }
                Spacer(modifier = Modifier.height(12.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(0.9f),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    boxSelector(categorias = categorias, title = "Filtrar por categoria", selecionado = categoriaSelecionada)
                    if (categoriaSelecionada.value.text == "Todas" || categoriaSelecionada.value.text.isEmpty()){
                        gastosViewModel.getGastosViagem(id.toLong())
                    }else {
                        gastosViewModel.getGastosCategoria(id.toLong(), categoriaSelecionada.value.text)
                    }
                }
                Spacer(modifier = Modifier.height(12.dp))

                when(gastosState) {
                    is DataResult.Loading -> CircularProgressIndicator()
                    is DataResult.Error -> ErrorMessage((gastosState as DataResult.Error).error)
                    is DataResult.Success -> ContentDescriptionGastos((gastosState as DataResult.Success<List<GastoViagem>>), onClickEdit = onEditGasto)
                    else -> {}
                }

                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    outLinedButtonComponent(
                        onNavigationIconClick = { onNavNovoGasto.invoke(id.toLong()) },
                        title = "ADICIONAR NOVO GASTO"
                    )

                    Spacer(modifier = Modifier.height(25.dp))
                }
            }
        }
    }
}

@Composable
fun ContentDescriptionViagem(
    retorno: DataResult.Success<Viagem>,
    onClickEdit: () -> Unit
){
    val viagem = retorno.data
    val color = when{
        viagem.gastoTotal >= (viagem.orcamento * 50 / 100) && viagem.gastoTotal < (viagem.orcamento * 85 / 100) ->
            Color.Yellow
        viagem.gastoTotal >= (viagem.orcamento * 85 / 100) ->
            Color.Red
        else -> {
            MaterialTheme.colors.secondary
        }
    }

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Row(
            modifier = Modifier.fillMaxWidth(0.9f),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(0.94f),
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = viagem.nome,
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    color = color
                )
            }
            Column(
                horizontalAlignment = Alignment.End
            ) {
                IconButton(onClick = {onClickEdit.invoke()}) {
                    Icon(imageVector = Icons.Filled.Edit, contentDescription = "editar")
                }
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            modifier = Modifier.fillMaxWidth(0.95f),
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(0.55f),
                horizontalAlignment = Alignment.Start
            ){
                Row(modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Start) {
                    Text(text = "Data ida: ", fontWeight = FontWeight.Bold, fontSize = 14.sp)
                    Text(text = viagem.dataIda, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                }
                Spacer(modifier = Modifier.height(5.dp))
                Row(modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Start) {
                    Text(text = "Dias de viagem: ", fontWeight = FontWeight.Bold, fontSize = 14.sp)
                    Text(text = viagem.diasDeViagem.toString(), fontWeight = FontWeight.Bold, fontSize = 14.sp)
                }
                Spacer(modifier = Modifier.height(5.dp))
                Row(modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Start) {
                    Text(text = "Origem: ", fontWeight = FontWeight.Bold, fontSize = 14.sp)
                    Text(text = viagem.origem, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                }
                Spacer(modifier = Modifier.height(5.dp))
                Row(modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Start) {
                    Text(text = "Orçamento total: ", fontWeight = FontWeight.Bold, fontSize = 14.sp)
                    Text(text = DecimalFormat("#.##").format(viagem.orcamento), fontWeight = FontWeight.Bold, fontSize = 14.sp, color = color)
                }
                Spacer(modifier = Modifier.height(5.dp))
                Row(modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Start) {
                    Text(text = "Orçamento Disponivel: ", fontWeight = FontWeight.Bold, fontSize = 14.sp)
                    Text(text = DecimalFormat("#.##").format(viagem.orcamentoRestante), fontWeight = FontWeight.Bold, fontSize = 14.sp, color = color)
                }
            }

            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.Start
            ) {
                Row(modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Start
                ){
                    Text(text = "Data volta: ", fontWeight = FontWeight.Bold, fontSize = 14.sp)
                    Text(text = viagem.dataVolta, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                }
                Spacer(modifier = Modifier.height(5.dp))
                Row(modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Start
                ){
                    Text(text = "Nº de viajantes: ", fontWeight = FontWeight.Bold, fontSize = 14.sp)
                    Text(text = viagem.qtdPessoas.toString(), fontWeight = FontWeight.Bold, fontSize = 14.sp)
                }
                Spacer(modifier = Modifier.height(5.dp))
                Row(modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Start
                ){
                    Text(text = "Destino: ", fontWeight = FontWeight.Bold, fontSize = 14.sp)
                    Text(text = viagem.destino, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                }
                Spacer(modifier = Modifier.height(5.dp))
                Row(modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Start
                ){
                    Text(text = "Orçamento diário: ", fontWeight = FontWeight.Bold, fontSize = 14.sp)
                    Text(text = DecimalFormat("#.##").format(viagem.orcamentoDiario), fontWeight = FontWeight.Bold, fontSize = 14.sp)
                }
                Spacer(modifier = Modifier.height(5.dp))
                Row(modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Start
                ){
                    Text(text = "Gastos totais: ", fontWeight = FontWeight.Bold, fontSize = 14.sp)
                    Text(text = DecimalFormat("#.##").format(viagem.gastoTotal), fontWeight = FontWeight.Bold, fontSize = 14.sp, color = color)
                }
            }
        }
    }
}

@Composable
fun ContentDescriptionGastos(
    retorno: DataResult.Success<List<GastoViagem>>,
    onClickEdit: (Long) -> Unit
){
    val listaGastos = retorno.data

    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.75f)
            .padding(bottom = 10.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        items(listaGastos.size) {
            Column(
                modifier = Modifier.fillMaxWidth(0.85f)
            ) {
                DescViagemComponent(
                    onItemClick = { onClickEdit.invoke(listaGastos[it].id) },
                    dataGasto = listaGastos[it].dataGasto,
                    valor = listaGastos[it].valorGasto,
                    moeda = listaGastos[it].moeda,
                    categoria = listaGastos[it].categoria,
                    descricao = listaGastos[it].descricaoGasto
                )
            }
        }
    }
}