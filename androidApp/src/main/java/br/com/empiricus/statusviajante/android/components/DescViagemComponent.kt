package br.com.empiricus.statusviajante.android.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit

import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@JvmOverloads
@Composable
fun DescViagemComponent(
    onItemClick: () -> Unit,
    id: Long,
    title: String,
    valor: Double,
    modifier: Modifier = Modifier
) {
    Row() {
        Card(
            modifier = Modifier.padding(bottom = 0.5.dp)

        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .padding(20.dp)
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth(0.7f)
                ) {
                    Text(
                        text = title,
                        color = MaterialTheme.colors.primary,
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(0.9f)
                    ) {
                        Column(
                            modifier = Modifier.fillMaxWidth(0.5f),
                            horizontalAlignment = Alignment.Start
                        ) {
                            Text(
                                text = "R$ $valor",
                                color = MaterialTheme.colors.primary,
                                fontWeight = FontWeight.Bold,
                                fontSize = 12.sp
                            )

                        }
                    }
                }
            }
            Row() {
                Column(
                    modifier = Modifier.fillMaxWidth(0.95f),
                    horizontalAlignment = Alignment.End
                ) {
                   IconButton(onClick = {}){
                       Icon(
                           imageVector = Icons.Filled.Edit,
                           contentDescription = "Editar"
                       )
                    }
                    Column() {
                        IconButton(onClick = {}){
                            Icon(
                                imageVector = Icons.Filled.Delete,
                                contentDescription = "Deletar"
                            )
                        }
                    }
                }
            }
        }
    }
}



