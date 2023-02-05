package br.com.empiricus.statusviajante.android.cadastroUsuario

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.com.empiricus.statusviajante.android.MyApplicationTheme
import br.com.empiricus.statusviajante.android.components.*
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.launch


fun cadastrarUsuario(nome : String) {
    Log.i("Cadastrar Usuario", nome)
}
@Composable
fun cadastroUsuario(onBack: () -> Boolean) {
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
                    val nomeUsuario = remember { mutableStateOf(TextFieldValue()) }
                    outLinedTextFildComponent(valor = nomeUsuario, title = "NOME")

                }

                item {
                    val email = remember {
                    mutableStateOf(TextFieldValue()) }
                    outLinedTextFildComponent(valor = email, title = "EMAIL")
                }

                item {
                    val senha = remember {
                    mutableStateOf(TextFieldValue()) }
                    outLinedTextFildComponent(valor = senha, title = "SENHA")
                }
                item {
                    val dataNascimento = remember {
                    mutableStateOf("")
                    }
                    boxSelectorCalendar(selecionado = dataNascimento, title = "DATA NASCIMENTO")
                }
                item {
                    val celular = remember {
                    mutableStateOf(TextFieldValue()) }
                    outLinedTextFildComponent(valor = celular, title = "CELULAR")
                }
                item {
                    Spacer(modifier = Modifier.height(25.dp))
                    outLinedButtonComponent(onNavigationIconClick = {  cadastrarUsuario("CADASTRO") }, title = "Cadastrar usuário")
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
