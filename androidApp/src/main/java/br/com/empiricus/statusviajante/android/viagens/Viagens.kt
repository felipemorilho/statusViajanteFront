package br.com.empiricus.statusviajante.android.viagens

import ConfirmAlertDialog
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import br.com.empiricus.statusviajante.android.MyApplicationTheme
import br.com.empiricus.statusviajante.android.components.*
import br.com.empiricus.statusviajante.integration.model.Viagem
import br.com.empiricus.statusviajante.integration.util.DataResult
import kotlinx.coroutines.launch


@Composable
fun Viagens(
    onNavCadastroViagens: () -> Unit,
    onItemDetail: (Long) -> Unit,
    onNavLogin: () -> Unit,
    onNavDadosUsuario: () -> Unit,
    onNavBack: () -> Unit
) {

    val scope = rememberCoroutineScope()
    val viewModel: ViagensViewModel = viewModel()
    val viagensState by viewModel.viagensState.collectAsState()
    val filterViagem = remember { mutableStateOf(TextFieldValue()) }
    val focusManager = LocalFocusManager.current
    val navLogin = remember { mutableStateOf(false) }

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
                    onItemClick = {
                        when(it.id) {
                            "Home" -> {
                                onNavBack.invoke()
                            }
                            "Criar Viagem" -> {
                                onNavBack.invoke()
                                onNavCadastroViagens.invoke()
                            }
                            "Meus Dados" -> {
                                onNavBack.invoke()
                                onNavDadosUsuario.invoke()
                            }
                            "Logout" -> {
                                navLogin.value = true
                            }
                        }
                    }
                )
            },
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
                    .clickable(
                        indication = null,
                        interactionSource = remember { MutableInteractionSource() }) {
                        focusManager.clearFocus()
                    }
            ) {
                Spacer(modifier = Modifier.height(25.dp))
                Text(text = "SUAS VIAGENS", fontWeight = FontWeight.Bold, fontSize = 22.sp)
                Spacer(modifier = Modifier.height(15.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(0.70f),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    OutlinedTextField(
                        modifier = Modifier
                            .fillMaxWidth(0.70f)
                            .height(55.dp),
                        value = filterViagem.value,
                        onValueChange = { filterViagem.value = it },
                        label = {
                            Text(
                                text = "Buscar viagem",
                                color = MaterialTheme.colors.secondary,
                                style = TextStyle(shadow = Shadow(color = MaterialTheme.colors.secondary))
                            )
                        },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                        trailingIcon = {
                            IconButton(
                                onClick = {  if (filterViagem.value.text.isBlank()){ viewModel.getViagens() } else {
                                    viewModel.getViagensByNome(filterViagem.value.text.trim())
                                } }) {
                                Icon(imageVector = Icons.Filled.Search, contentDescription = "Pesquisar")
                            }
                        },
                        shape = MaterialTheme.shapes.medium,
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            focusedBorderColor = MaterialTheme.colors.secondary,
                            unfocusedBorderColor = MaterialTheme.colors.secondary,
                            focusedLabelColor = MaterialTheme.colors.secondary,
                            cursorColor = MaterialTheme.colors.secondary,
                        )
                    )
                }
                Spacer(modifier = Modifier.height(15.dp))
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

                if (navLogin.value){
                    ConfirmAlertDialog(
                        title = "Atenção",
                        message = "Você deseja sair da sua conta?",
                        onConfirm = { onNavLogin.invoke() },
                        onCancel = {onNavBack.invoke()})

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