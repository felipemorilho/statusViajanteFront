package br.com.empiricus.statusviajante.android

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import br.com.empiricus.statusviajante.android.cadastroviagens.cadastroViagens
import br.com.empiricus.statusviajante.android.gastoViagem.GastosViagem
import br.com.empiricus.statusviajante.android.testescreen.testeBonitin

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
            cadastroViagens {
                navHostController.navigate(Route.Test.name)
            }
        }
        composable(Route.Test.name){

            GastosViagem{
                navHostController.popBackStack()
            }
        }
    }
}
