package br.com.empiricus.statusviajante.android.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import br.com.empiricus.statusviajante.android.usuario.ContentEditUsuario
import br.com.empiricus.statusviajante.android.usuario.UsuarioViewModel
import br.com.empiricus.statusviajante.integration.model.Usuario
import br.com.empiricus.statusviajante.integration.util.DataResult

@Composable
fun drawerHeader(
) {
    val viewModel: UsuarioViewModel = viewModel()
    val usuarioState by viewModel.usuarioState.collectAsState()
    val usuario: DataResult<Usuario>
    var text = ""

    when (usuarioState) {
        is DataResult.Error -> {}
        is DataResult.Loading -> CircularProgressIndicator()
        is DataResult.Success -> {
            usuario = usuarioState as DataResult.Success<Usuario>
            text = usuario.data.nome
        }
        else -> {}
    }
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 64.dp),
        contentAlignment = Alignment.Center,
    ){
        Text(text = "Olá ${text.substringBefore(" ")}", fontSize = 35.sp)
    }
}

@Composable
fun drawerBody(
    itens: List<MenuItens>,
    onItemClick: (MenuItens) -> Unit
) {
    LazyColumn() {
        items(itens) {item ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        onItemClick(item)
                    }
                    .padding(16.dp)
            ) {
                Icon(
                    imageVector = item.icon,
                    contentDescription = item.contentDescription
                )
                Spacer(modifier = Modifier.width(16.dp))
                Text(
                    text = item.title,
                    color = MaterialTheme.colors.primary,
                    fontSize = 23.sp,
                    modifier = Modifier.weight(1f)
                )

            }
        }
    }
}