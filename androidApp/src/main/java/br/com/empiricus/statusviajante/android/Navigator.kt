package br.com.empiricus.statusviajante.android

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import br.com.empiricus.statusviajante.android.cadastroviagens.cadastroViagens
import br.com.empiricus.statusviajante.android.gastoViagem.GastosViagem

enum class  Route {
    Login,
    CadastroViagens,
    Test
}

@Composable
fun navigator(
    modifier: Modifier = Modifier,
    navHostController: NavHostController = rememberNavController(),
    initial: Route = Route.CadastroViagens
) {
    NavHost(
        navController = navHostController,
        startDestination = initial.name
    ) {
        composable(Route.CadastroViagens.name) {
            cadastroViagens{
                navHostController.popBackStack()
            }
        }
        composable(Route.Test.name){

            GastosViagem{
                navHostController.popBackStack()
            }
        }
    }
}
