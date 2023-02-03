package br.com.empiricus.statusviajante.android.login

import br.com.empiricus.statusviajante.android.R

import androidx.compose.foundation.background
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.com.empiricus.statusviajante.android.MyApplicationTheme
import br.com.empiricus.statusviajante.android.components.outLinedButtonComponent
import br.com.empiricus.statusviajante.android.components.outLinedTextFildComponent
import br.com.empiricus.statusviajante.android.components.topBarComponent

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.ui.res.painterResource



@Composable
fun Login(onBack: () -> Boolean) {

    fun entrar()  {
        println("user quer entrar")
    }

    fun cadastrar() {
        println("user quer cadastrar")
    }

    MyApplicationTheme {
        Scaffold(
            topBar = { topBarComponent(onClickNav = {onBack.invoke()}) }
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
            ){
                item {
                    Image(
                        painter = painterResource(id = R.drawable.logo_azul),
                        contentDescription = "",
                        modifier = Modifier.width(250.dp)
                            .height(250.dp)
                    )
                }
                item {
                    val test = remember { mutableStateOf(TextFieldValue()) }
                    outLinedTextFildComponent(valor = test, title = "EMAIL")
                }
                item {
                    val test = remember { mutableStateOf(TextFieldValue()) }
                    outLinedTextFildComponent(valor = test, title = "SENHA")
                }
                item {
                    outLinedButtonComponent(title = "ENTRAR", onNavigationIconClick= {entrar()})
                }
                item {
                    outLinedButtonComponent(title = "CADASTRAR", onNavigationIconClick= {cadastrar()})
                }
            }
            }
        }
    }


@Preview
@Composable
fun LoginPreview(){
    Login (onBack = {true})
}