package br.com.empiricus.statusviajante.android.gastoViagem

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import br.com.empiricus.statusviajante.android.MyApplicationTheme
import br.com.empiricus.statusviajante.android.components.*
import br.com.empiricus.statusviajante.android.viagens.ViagensViewModel
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
    onEditViagem: (Long) -> Unit
) {


    val scope = rememberCoroutineScope()
    val scaffoldState = rememberScaffoldState()
    val gastosViewModel: GastosViagemViewModel = viewModel()
    val gastosState by gastosViewModel.gastosViagemState.collectAsState()
    val viagemViewModel: ViagensViewModel = viewModel()
    val viagemState by viagemViewModel.viagemState.collectAsState()
    val categorias = listOf("Todas","Lazer", "Hospedagem", "Transporte", "Alimentação", "Outros")
    val categoriaSelecionada = remember { mutableStateOf(TextFieldValue()) }

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
            floatingActionButton = {
                MyFloatingActionButtonDelete(
                    onConfirm = {
                        viagemViewModel.deleteViagem(id.toLong())
                        onBack.invoke()
                    },
                    onCancel = {},
                    message = "Você está tentando deletar sua viagem por completo, todos os gatos tambem serão deletados!!! \n" +
                            "Deseja prosseguir com a exclusão?",
                    title = "ATENÇÃO"
                )
            },
            floatingActionButtonPosition = FabPosition.Center,
            isFloatingActionButtonDocked = true,
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
                    ),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(20.dp))
                IconButton(onClick = {onEditViagem.invoke(id.toLong())}) {
                    Icon(imageVector = Icons.Filled.Edit, contentDescription = "editar")
                }
                when(viagemState) {
                    is DataResult.Loading -> CircularProgressIndicator()
                    is DataResult.Error -> ErrorMessage((viagemState as DataResult.Error).error)
                    is DataResult.Success -> ContentDescriptionViagem((viagemState as DataResult.Success<Viagem>))
                    else -> {}
                }
                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(0.9f),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    boxSelector(categorias = categorias, title = "Filtrar por categoria", selecionado = categoriaSelecionada)
                    if (categoriaSelecionada.value.text == "Todas" || categoriaSelecionada.value.text.isEmpty()){
                        gastosViewModel.getGastosViagem(id.toLong())
                    }else {
                        gastosViewModel.getGastosCategoria(categoriaSelecionada.value.text)
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))

                when(gastosState) {
                    is DataResult.Loading -> CircularProgressIndicator()
                    is DataResult.Error -> ErrorMessage((gastosState as DataResult.Error).error)
                    is DataResult.Success -> ContentDescriptionGastos((gastosState as DataResult.Success<List<GastoViagem>>))
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
    retorno: DataResult.Success<Viagem>
){
    val viagem = retorno.data

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Row(
            modifier = Modifier.fillMaxWidth(0.85f),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = viagem.nome, fontWeight = FontWeight.Bold, fontSize = 22.sp)
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
                    Text(text = DecimalFormat("#.##").format(viagem.orcamento), fontWeight = FontWeight.Bold, fontSize = 14.sp)
                }
                Spacer(modifier = Modifier.height(5.dp))
                Row(modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Start) {
                    Text(text = "Orçamento Disponivel: ", fontWeight = FontWeight.Bold, fontSize = 14.sp)
                    Text(text = DecimalFormat("#.##").format(viagem.orcamentoRestante), fontWeight = FontWeight.Bold, fontSize = 14.sp)
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
                    Text(text = DecimalFormat("#.##").format(viagem.gastoTotal), fontWeight = FontWeight.Bold, fontSize = 14.sp)
                }
            }
        }
        Spacer(modifier = Modifier.height(17.dp))
    }
}

@Composable
fun ContentDescriptionGastos(
    retorno: DataResult.Success<List<GastoViagem>>
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
                    onItemClick = {  },
                    id = listaGastos[it].id,
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