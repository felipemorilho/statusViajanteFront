package br.com.empiricus.statusviajante.android.cadastroUsuario

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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import br.com.digitalhouse.dhwallet.util.DataResult
import br.com.empiricus.statusviajante.android.MyApplicationTheme
import br.com.empiricus.statusviajante.android.components.*


@Composable
fun cadastroUsuario(onBack: () -> Boolean) {

    val viewModel: CadastroUsuariosViewModel = viewModel()
    val cadastroState by viewModel.cadastro.collectAsState()

    val nome = remember { mutableStateOf(TextFieldValue()) }
    val nomeUsuario = remember { mutableStateOf(TextFieldValue()) }
    val email = remember { mutableStateOf(TextFieldValue()) }
    val senha = remember { mutableStateOf(TextFieldValue()) }
    val dataNascimento = remember { mutableStateOf("") }
    val celular = remember { mutableStateOf(TextFieldValue()) }


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
                    Spacer(modifier = Modifier.height(35.dp))
                    Text(text = "BEM-VINDO")
                }

                item {
                    outLinedTextFildComponent(valor = nome, title = "NOME")
                }

                item {
                    outLinedTextFildComponent(valor = nomeUsuario, title = "NOME DO USUARIO")
                }

                item {
                    outLinedTextFildComponent(valor = email, title = "EMAIL", keyboardType = KeyboardType.Email)
                }

                item {
                    outLinedTextFildPassword(valor = senha, title = "SENHA")
                }

                item {
                    boxSelectorCalendar(selecionado = dataNascimento, title = "DATA NASCIMENTO")
                }

                item {
                    outLinedTextFildComponent(valor = celular, title = "CELULAR", keyboardType = KeyboardType.Phone)
                }

                item {
                    Spacer(modifier = Modifier.height(25.dp))

                    if (cadastroState is DataResult.Loading) {
                        CircularProgressIndicator()
                    } else {
                        if (cadastroState is DataResult.Success) {
                            onBack.invoke()
                        }

                        if (cadastroState is DataResult.Error) {

                        }
                        outLinedButtonComponent(
                            onNavigationIconClick = {
                                viewModel.cadastrar(
                                    nome = nome.value.text,
                                    nomeUsuario = nomeUsuario.value.text,
                                    email = email.value.text,
                                    senha = senha.value.text,
                                    dataNascimento = dataNascimento.value,
                                    celular = celular.value.text
                                )},
                            title = "Cadastrar usuário"
                        )
                    }


                }
            }
        }
    }
}

@Preview
@Composable
fun loginPreview() {
    cadastroUsuario(onBack = { true })
}
