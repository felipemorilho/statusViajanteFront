package br.com.empiricus.statusviajante.android

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import br.com.empiricus.statusviajante.android.cadastroUsuario.cadastrarUsuario
import br.com.empiricus.statusviajante.android.cadastroUsuario.cadastroUsuario
import br.com.empiricus.statusviajante.android.cadastroviagens.cadastroViagens
import br.com.empiricus.statusviajante.android.login.Login

enum class  Route {
    Login,
    Cadastro,
    CadastroViagens,
}

@Composable
fun navigator(
    modifier: Modifier = Modifier,
    navHostController: NavHostController = rememberNavController(),
    initial: Route = Route.Login
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
        composable(Route.Login.name){
           Login {
               navHostController.navigate(Route.Cadastro.name)
           }
        }
        composable(Route.Cadastro.name){
            cadastroUsuario{
                navHostController.popBackStack()
            }
        }

    }
}
