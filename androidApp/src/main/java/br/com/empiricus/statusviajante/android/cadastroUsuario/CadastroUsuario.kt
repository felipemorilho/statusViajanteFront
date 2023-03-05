package br.com.empiricus.statusviajante.android.cadastroUsuario

import android.text.TextUtils.isEmpty
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import br.com.empiricus.statusviajante.integration.util.DataResult
import br.com.empiricus.statusviajante.android.MyApplicationTheme
import br.com.empiricus.statusviajante.android.components.*
import br.com.empiricus.statusviajante.integration.model.Usuario
import java.util.regex.Pattern


@Composable
fun cadastroUsuario(onBack: () -> Boolean, onNavCadastroSucesso: () -> Unit) {

    val viewModel: CadastroUsuariosViewModel = viewModel()
    val cadastroState by viewModel.cadastro.collectAsState()

    val nome = remember { mutableStateOf(TextFieldValue()) }
    val nomeUsuario = remember { mutableStateOf(TextFieldValue()) }
    val email = remember { mutableStateOf(TextFieldValue()) }
    val senha = remember { mutableStateOf(TextFieldValue()) }
    val celular = remember { mutableStateOf(TextFieldValue()) }
    val onNavHomeOk = remember { mutableStateOf(false)
    }

    val errorNome = remember { mutableStateOf(false) }
    val errorNomeUsuario = remember { mutableStateOf(false) }
    val errorEmail = remember { mutableStateOf(false) }
    val errorSenha = remember { mutableStateOf(false) }
    val errorCelular = remember { mutableStateOf(false) }
    val PASSWORD_PATTERN =
        "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,20}$"
    val validadorSenha = Pattern.compile(PASSWORD_PATTERN)


    MyApplicationTheme {
        Scaffold(
            topBar = { topBarComponent() },
            bottomBar = { bottonBarComponent(
                onBack = {onBack.invoke()},
                colorMenuButton = Color.Transparent,
                onNavDrawer = {})
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
                            color = Color.Red
                        )
                    }
                }

                item {
                    outLinedTextFildComponent(valor = nomeUsuario, title = "NOME DO USUARIO")
                    if (errorNomeUsuario.value){
                        Text(
                            modifier = Modifier.fillMaxWidth(0.8f),
                            textAlign = TextAlign.Left,
                            text = "* Nome do Usuario deve ser preenchido",
                            color = Color.Red
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
                            color = Color.Red
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
                            color = Color.Red
                        )
                    }
                }

                item {
                    outLinedTextFildPassword(valor = senha, title = "SENHA")
                    if (errorSenha.value){
                        Text(
                            modifier = Modifier.fillMaxWidth(0.8f),
                            textAlign = TextAlign.Left,
                            text = "* Senha deve conter entre 8 e 20 caracteres \n" +
                                    "* Senha deve conter no minimo um numero [0-9] \n" +
                                    "* Senha deve conter no minimo uma letra minuscula [a-z] \n" +
                                    "* Senha deve conter no minimo uma letra maiuscula [A-Z] \n" +
                                    "* Senha deve conter no minimo um caracter espacial",
                            color = Color.Red
                        )
                    }
                }

                item {
                    when{
                        cadastroState is DataResult.Success && !onNavHomeOk.value -> {
                            onNavCadastroSucesso.invoke()
                            onNavHomeOk.value = true
                        }
                        cadastroState is DataResult.Loading -> CircularProgressIndicator()
                        cadastroState is DataResult.Error -> ErrorMessage((cadastroState as DataResult.Error).error)
                    }

                    outLinedButtonComponent(
                        onNavigationIconClick = {
                            when{ nome.value.text.isEmpty() -> errorNome.value = true else -> errorNome.value = false}
                            when{ nomeUsuario.value.text.isEmpty() -> errorNomeUsuario.value = true else -> errorNomeUsuario.value = false}
                            when{ email.value.text.isEmpty() -> errorEmail.value = true else -> errorEmail.value = false}
                            when{ !validadorSenha.matcher(senha.value.text).matches() -> errorSenha.value = true else -> errorSenha.value = false}
                            when{ celular.value.text.length != 11 -> errorCelular.value = true else -> errorCelular.value = false}

                            when{
                                nome.value.text.isNotEmpty() && nomeUsuario.value.text.isNotEmpty() && email.value.text.isNotEmpty() && validadorSenha.matcher(senha.value.text).matches() && celular.value.text.isNotEmpty() -> {
                                    viewModel.cadastrar(
                                        usuario = Usuario(
                                            nome = nome.value.text,
                                            usuario = nomeUsuario.value.text,
                                            senha = senha.value.text,
                                            email = email.value.text,
                                            celular = celular.value.text
                                        )
                                    )
                                }
                            }
                        },
                        title = "Cadastrar usuário"
                    )
                    Spacer(modifier = Modifier.height(25.dp))
                }
            }
        }
    }
}


@Preview
@Composable
fun loginPreview() {
    cadastroUsuario(onBack = { true }, {})
}