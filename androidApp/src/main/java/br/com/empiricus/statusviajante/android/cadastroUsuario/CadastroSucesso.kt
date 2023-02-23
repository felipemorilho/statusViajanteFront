package br.com.empiricus.statusviajante.android.cadastroUsuario

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.empiricus.statusviajante.android.MyApplicationTheme
import br.com.empiricus.statusviajante.android.R
import br.com.empiricus.statusviajante.android.Route
import br.com.empiricus.statusviajante.android.components.bottonBarComponent
import br.com.empiricus.statusviajante.android.components.outLinedButtonComponent
import br.com.empiricus.statusviajante.android.components.topBarComponent

@Composable
fun cadastroSucesso(onNavHome: () -> Unit) {
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
                    .padding(it),
                verticalArrangement = Arrangement.spacedBy(25.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                item {
                    Spacer(modifier = Modifier.height(25.dp))
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
                    Text(
                        text = "Cadastro realizado com sucesso!!",
                        style = TextStyle(
                            textAlign = TextAlign.Center,
                            fontSize = 30.sp,
                            fontWeight = FontWeight.Bold
                        )
                    )
                }
                item {
                    outLinedButtonComponent(onNavigationIconClick = {onNavHome.invoke()}, title = "Login" )
                }
            }
        }
    }
}

@Composable
@Preview
fun preview_cadastroSuc(){
    cadastroSucesso({})
}