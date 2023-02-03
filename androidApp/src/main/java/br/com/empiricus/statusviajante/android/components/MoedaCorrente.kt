package br.com.empiricus.statusviajante.android.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class MoedaCorrente(
    val id: String,
    val title: String,
)

@Composable
fun moedasSelect(): List<MoedaCorrente> {

    return listOf(
        MoedaCorrente(
            id = "Real",
            title = "Real",
        ),
        MoedaCorrente(
            id = "DolarAmericano",
            title = "Dolar Americano",
        ),
        MoedaCorrente(
            id = "Euro",
            title = "Euro",
        ),
        MoedaCorrente(
            id = "Peso",
            title = "Peso",
        ),
        MoedaCorrente(
            id = "Libra",
            title = "Libra",
        )
    )
}

@Composable
fun moedaCorrenteList(
    itens: List<MoedaCorrente>,
    onItemClick: () -> Unit
) {
    LazyColumn() {
        items(itens) {item ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        onItemClick
                    }
                    .padding(16.dp)
            ) {
                Text(
                    text = item.title,
                    color = MaterialTheme.colors.secondaryVariant,
                    fontSize = 18.sp,
                    modifier = Modifier.weight(1f)
                )

            }
        }
    }
}

