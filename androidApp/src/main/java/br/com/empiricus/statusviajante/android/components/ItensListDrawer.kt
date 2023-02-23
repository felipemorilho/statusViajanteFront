package br.com.empiricus.statusviajante.android.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector

data class MenuItens(
    val id: String,
    val title: String,
    val contentDescription : String,
    val icon: ImageVector
)

@Composable
fun listaItensDrawer(): List<MenuItens> {

    return listOf(
        MenuItens(
            id = "Home",
            title = "Home",
            contentDescription = "Ir para a pagina inicial da conta",
            icon = Icons.Filled.Home
        ),
        MenuItens(
            id = "Viagens",
            title = "Minhas Viagens",
            contentDescription = "Ir para tela de viagem",
            icon = Icons.Filled.Insights
        ),
        MenuItens(
            id = "Criar Viagem",
            title = "Nova Viagem",
            contentDescription = "Ir para tela de ativos",
            icon = Icons.Filled.Money
        ),
        MenuItens(
            id = "Meus Dados",
            title = "Meus Dados",
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
}
