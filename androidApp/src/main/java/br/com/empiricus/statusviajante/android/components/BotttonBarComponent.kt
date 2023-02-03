package br.com.empiricus.statusviajante.android.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.BottomAppBar
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun bottonBarComponent(
    onBack: () -> Unit,
    onNavDrawer: () -> Unit,
    modifier: Modifier = Modifier
) {
    BottomAppBar(
        modifier = modifier,
        backgroundColor = MaterialTheme.colors.primary,
        elevation = 10.dp
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            IconButton(onClick = onBack) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = "botão de voltar",
                    tint = MaterialTheme.colors.secondaryVariant
                )
            }
            IconButton(onClick = onNavDrawer){
                Icon(
                    imageVector = Icons.Filled.Menu,
                    contentDescription = "botão menu",
                    tint = MaterialTheme.colors.secondaryVariant
                )
            }
        }
    }

}