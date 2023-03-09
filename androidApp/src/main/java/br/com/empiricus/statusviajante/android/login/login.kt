package br.com.empiricus.statusviajante.android.login

import MyAlertDialog
import br.com.empiricus.statusviajante.android.R

import androidx.compose.foundation.background
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.com.empiricus.statusviajante.android.MyApplicationTheme

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.lifecycle.viewmodel.compose.viewModel
import br.com.empiricus.statusviajante.integration.util.DataResult
import br.com.empiricus.statusviajante.android.components.*


@Composable
fun Login(onNavCadastro: () -> Unit, onNavHomeViagens: () -> Unit) {

    val viewModel: LoginViewModel = viewModel()
    val loginState by viewModel.loginState.collectAsState()
    val navigateToHome = remember { mutableStateOf(false) }
    val focusManager = LocalFocusManager.current


    val usuario = remember { mutableStateOf(TextFieldValue()) }
    val senha = remember { mutableStateOf(TextFieldValue()) }

    MyApplicationTheme {
        Scaffold(
            topBar = { topBarComponent() },
            bottomBar = {
                bottonBarComponent(
                    colorBackButton = Color.Transparent,
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
                    .padding(it)
                    .clickable(
                        indication = null,
                        interactionSource = remember { MutableInteractionSource() }) {
                        focusManager.clearFocus()
                    },
                verticalArrangement = Arrangement.spacedBy(25.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                item {
                    Image(
                        imageVector = ImageVector.vectorResource(id = R.drawable.logocircular),
                        contentDescription = "Logo circular do projeto",
                        modifier = Modifier
                            .width(250.dp)
                            .height(250.dp),
                        colorFilter = ColorFilter.tint(MaterialTheme.colors.primary)
                    )
                }
                item {
                    outLinedTextFildComponent(
                        valor = usuario,
                        title = "USUARIO",
                        keyboardType = KeyboardType.Email
                    )
                }
                item {
                    outLinedTextFildPassword(valor = senha, title = "SENHA")
                }
                item {
                    when {
                        loginState is DataResult.Loading -> CircularProgressIndicator()
                        loginState is DataResult.Success && !navigateToHome.value -> {
                            onNavHomeViagens.invoke()
                            navigateToHome.value = true
                        }
                        loginState is DataResult.Error -> {
                            MyAlertDialog(
                                title = "Atenção!!!",
                                message = "Usuario e/ou Senha invalidos!!"
                            ) {

                            }
                        }
                    }
                    outLinedButtonComponent(
                        title = "ENTRAR",
                        onNavigationIconClick = {
                            viewModel.login(usuario.value.text, senha.value.text)
                        })
                }
                item {
                    outLinedButtonComponent(
                        title = "CADASTRAR",
                        onNavigationIconClick = { onNavCadastro.invoke() })
                }
            }
        }
    }
}


@Preview
@Composable
fun LoginPreview(){
    Login (onNavCadastro = {}, onNavHomeViagens = {})
}
