package br.com.empiricus.statusviajante.android.usuario

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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import br.com.empiricus.statusviajante.android.MyApplicationTheme
import br.com.empiricus.statusviajante.android.components.*
import br.com.empiricus.statusviajante.integration.model.Usuario
import br.com.empiricus.statusviajante.integration.util.DataResult
import kotlinx.coroutines.launch


@Composable
fun DadosUsuario(
    onBack: () -> Boolean,
    onNavHome: () -> Unit,
    onNavLogin: () -> Unit,
    onNavCadastroViagens: () -> Unit,
) {

    val viewModel: UsuarioViewModel = viewModel()
    val usuarioState by viewModel.usuarioState.collectAsState()
    val onNavHomeOk = remember { mutableStateOf(false) }
    val navLogin = remember { mutableStateOf(false) }
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()
    val focusManager = LocalFocusManager.current

    viewModel.getUsuarioByUsername()

    MyApplicationTheme {
        Scaffold(
            scaffoldState = scaffoldState,
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
                            "Home" -> {
                                onBack.invoke()
                                onNavHome.invoke()
                            }
                            "Criar Viagem" -> {
                                onBack.invoke()
                                onNavCadastroViagens.invoke()
                            }
                            "Meus Dados" -> {
                            }
                            "Logout" -> {
                                navLogin.value = true
                            }
                        }
                    }
                )
            },
        ){
            if (navLogin.value){
                ConfirmAlertDialog(
                    title = "Atenção",
                    message = "Você deseja sair da sua conta?",
                    onConfirm = { onNavLogin.invoke() },
                    onCancel = {onBack.invoke()})

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
            ){
                when (usuarioState) {
                    is DataResult.Error -> { MyAlertDialog(title = "Erro", message = "Senha incorreta!!") {
                        onBack.invoke()
                    } }
                    is DataResult.Loading -> CircularProgressIndicator()
                    is DataResult.Success -> {
                        ContentEditUsuario(usuarioState as DataResult.Success<Usuario>,
                            viewModel = viewModel,
                            onClick = {onNavHomeOk.value = true}
                        )
                    }
                    else -> {}
                }
                when{
                    usuarioState is DataResult.Success && onNavHomeOk.value -> {
                       MyAlertDialog(title = "Uhuu", message = "Dados alterados com sucesso!!", onDismiss = {
                           onBack.invoke()
                       })
                    }
                    usuarioState is DataResult.Loading -> CircularProgressIndicator()
                }
            }
        }
    }
}

@Composable
fun ContentEditUsuario(
    retorno: DataResult.Success<Usuario>,
    viewModel: UsuarioViewModel,
    onClick: () -> Unit
){

    val usuario = retorno.data

    val nome = remember { mutableStateOf(TextFieldValue(usuario.nome)) }
    val nomeUsuario = remember { mutableStateOf(TextFieldValue(usuario.usuario)) }
    val email = remember { mutableStateOf(TextFieldValue(usuario.email)) }
    val senha = remember { mutableStateOf(TextFieldValue()) }
    val celular = remember { mutableStateOf(TextFieldValue(usuario.celular)) }
    val color = MaterialTheme.colors.secondary

    val errorNome = remember { mutableStateOf(false) }
    val errorNomeUsuario = remember { mutableStateOf(false) }
    val errorEmail = remember { mutableStateOf(false) }
    val errorSenha = remember { mutableStateOf(false) }
    val errorCelular = remember { mutableStateOf(false) }
    val alterarDados = remember { mutableStateOf(false) }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(25.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        item{
            Spacer(modifier = Modifier.height(25.dp))
            Text(text = "BEM-VINDO")
        }

        item {
            outLinedTextFildComponent(valor = nome, title = "NOME")
            if (errorNome.value){
                Text(
                    modifier = Modifier.fillMaxWidth(0.8f),
                    textAlign = TextAlign.Left,
                    text = "* Nome deve ser preenchido",
                    color = color
                )
            }
        }

        item {
            outLinedTextFildReadOnly(valor = nomeUsuario, title = "NOME DO USUARIO")
            if (errorNomeUsuario.value){
                Text(
                    modifier = Modifier.fillMaxWidth(0.8f),
                    textAlign = TextAlign.Left,
                    text = "* Nome do Usuario deve ser preenchido",
                    color = color
                )
            }
        }

        item {
            outLinedTextFildComponent(valor = email, title = "EMAIL", keyboardType = KeyboardType.Email)
            if (errorEmail.value){
                Text(
                    modifier = Modifier.fillMaxWidth(0.8f),
                    textAlign = TextAlign.Left,
                    text = "* Email deve ser preenchido",
                    color = color
                )
            }
        }

        item {
            outLinedTextFildComponent(valor = celular, title = "CELULAR", keyboardType = KeyboardType.Phone)
            if (errorCelular.value){
                Text(
                    modifier = Modifier.fillMaxWidth(0.8f),
                    textAlign = TextAlign.Left,
                    text = "* Celular deve conter DDD + 9 Digitos",
                    color = color
                )
            }
        }

        item {
            if (alterarDados.value){
                outLinedTextFildPassword(valor = senha, title = "SENHA")
                if (errorSenha.value){
                    Text(
                        modifier = Modifier.fillMaxWidth(0.8f),
                        textAlign = TextAlign.Left,
                        text = "* Digite sua senha por favor!",
                        color = color
                    )
                }
            }
        }

        item {
            if (!alterarDados.value){
                outLinedButtonComponent(
                    onNavigationIconClick = {
                        alterarDados.value = true
                    },
                    title = "Alterar dados"
                )
            } else {
                when{ nome.value.text.isEmpty() -> errorNome.value = true else -> errorNome.value = false}
                when{ nomeUsuario.value.text.isEmpty() -> errorNomeUsuario.value = true else -> errorNomeUsuario.value = false}
                when{ email.value.text.isEmpty() -> errorEmail.value = true else -> errorEmail.value = false}
                when{ senha.value.text.isEmpty() -> errorSenha.value = true else -> errorSenha.value = false}
                when{ celular.value.text.length != 11 -> errorCelular.value = true else -> errorCelular.value = false}

                outLinedButtonComponent(onNavigationIconClick = {
                    when{
                        nome.value.text.isNotEmpty() && senha.value.text.isNotEmpty() && nomeUsuario.value.text.isNotEmpty() && email.value.text.isNotEmpty() && celular.value.text.isNotEmpty() -> {
                            onClick.invoke()
                            viewModel.putUsuario(
                                usuario = Usuario(
                                    nome = nome.value.text,
                                    usuario = usuario.usuario,
                                    senha = senha.value.text,
                                    email = email.value.text,
                                    celular = celular.value.text
                                )
                            )
                        }
                    }
                }, title = "Salvar alterações")
            }

        }
    }

}