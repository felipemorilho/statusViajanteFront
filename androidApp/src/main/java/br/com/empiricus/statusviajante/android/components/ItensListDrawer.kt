package br.com.empiricus.statusviajante.android.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.rounded.Create
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector

data class MenuItens(
    val id: String,
    val title: String,
    val contentDescription : String,
    val icon: ImageVector
)

@Composable
fun listaItensDrawer(): List<MenuItens>{

    val listaItensPJ = listOf<MenuItens>(
        MenuItens(
            id = "Home",
            title = "Home",
            contentDescription = "Ir para a pagina inicial da conta",
            icon = Icons.Filled.Home
        ),
        MenuItens(
            id = "Indicadores",
            title = "Consultar indicadores",
            contentDescription = "Ir para tela de indicadores",
            icon = Icons.Filled.Insights
        ),
        MenuItens(
            id = "Meus ativos",
            title = "Meus ativos",
            contentDescription = "Ir para tela de ativos",
            icon = Icons.Filled.Money
        ),
        MenuItens(
            id = "Cadastrar ativos",
            title = "Cadastrar ativo",
            contentDescription = "Ir para tela de cadastro de ativos",
            icon = Icons.Rounded.Create
        ),
        MenuItens(
            id = "Meus dados",
            title = "Meus dados",
            contentDescription = "Ir para tela de dados da conta",
            icon = Icons.Filled.EditAttributes
        ),
        MenuItens(
            id = "Logout",
            title = "Logout",
            contentDescription = "Deslogar da conta",
            icon = Icons.Filled.Logout
        )
    )
    return listaItensPJ
}
