package br.com.empiricus.statusviajante.android

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import br.com.empiricus.statusviajante.android.cadastroUsuario.cadastroUsuario
import br.com.empiricus.statusviajante.android.cadastroviagens.cadastroViagens
import br.com.empiricus.statusviajante.android.login.Login
import br.com.empiricus.statusviajante.android.viagens.Viagens

enum class  Route {
    Login,
    Cadastro,
    CadastroViagens,
    HomeViagens,
    DetalheViagem,
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
           Login(
               onNavCadastro = { navHostController.navigate(Route.Cadastro.name) },
               onNavHomeViagens = { navHostController.navigate(Route.HomeViagens.name) }
           )
        }
        composable(Route.Cadastro.name){
            cadastroUsuario{
                navHostController.popBackStack()
            }
        }
        composable(Route.HomeViagens.name){
            Viagens(
                onNavCadastroViagens = {navHostController.navigate(Route.CadastroViagens.name)},
                onNavViagem = {navHostController.navigate(Route.DetalheViagem.name)}
            )
        }

    }
}
