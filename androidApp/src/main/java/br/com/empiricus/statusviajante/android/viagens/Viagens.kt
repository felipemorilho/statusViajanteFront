package br.com.empiricus.statusviajante.android.viagens

import br.com.empiricus.statusviajante.android.components.listaViagemComponent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.empiricus.statusviajante.android.MyApplicationTheme
import br.com.empiricus.statusviajante.android.components.outLinedButtonTransparent


@Composable
fun Viagens(onNavTest: () -> Unit) {
    MyApplicationTheme {
        Scaffold(
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
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .padding(55.dp)
                    ) {
                        Text(text = "SUAS VIAGENS", fontWeight = FontWeight.Bold, fontSize = 22.sp)
                    }
                }
                item {

                    Column(
                    ) {
                        listaViagemComponent(
                            id = 1,
                            title = "Ferias",
                            dataIda = "06/02/2023",
                            dataVolta = "15/02/2022"
                        )
                        listaViagemComponent(
                            id = 2,
                            title = "Reunião Da Empresa",
                            dataIda = "25/03/2023",
                            dataVolta = "28/03/2022"
                        )
                        listaViagemComponent(
                            id = 3,
                            title = "Dublin",
                            dataIda = "19/09/2023",
                            dataVolta = "01/10/2023"
                        )
                        listaViagemComponent(
                            id = 3,
                            title = "São Paulo ",
                            dataIda = "03/05/2023",
                            dataVolta = "10/05/2023"
                        )
                    }
                }
                item {
                    outLinedButtonTransparent(
                        onNavigationIconClick = { onNavTest.invoke() },
                        title = "ADICIONAR NOVA VIAGEM"
                    )
                    Spacer(modifier = Modifier.height(25.dp))
                }
            }
        }
    }
}


@Preview
@Composable
fun previewViagens() {
    Viagens(onNavTest = {})
}