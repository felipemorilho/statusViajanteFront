package br.com.empiricus.statusviajante.android

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import br.com.empiricus.statusviajante.android.cadastroUsuario.cadastroSucesso
import br.com.empiricus.statusviajante.android.cadastroUsuario.cadastroUsuario
import br.com.empiricus.statusviajante.android.viagens.cadastroViagens
import br.com.empiricus.statusviajante.android.gastoViagem.GastosViagem
import br.com.empiricus.statusviajante.android.login.Login
import br.com.empiricus.statusviajante.android.viagens.DescViagens
import br.com.empiricus.statusviajante.android.gastoViagem.EditarGastosViagem
import br.com.empiricus.statusviajante.android.viagens.EditarViagens
import br.com.empiricus.statusviajante.android.viagens.Viagens

enum class  Route {
    Login,
    Cadastro,
    CadastroSucesso,
    CadastroViagens,
    HomeViagens,
    DetalheViagem,
    EditarViagem,
    NovoGastoViagem,
    EditarGasto
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun navigator(
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
            cadastroUsuario(
                onBack = { navHostController.popBackStack() },
                onNavCadastroSucesso =  { navHostController.navigate(Route.CadastroSucesso.name) }
            )
        }
        composable(Route.HomeViagens.name){
            Viagens(
                onNavCadastroViagens = {navHostController.navigate(Route.CadastroViagens.name)},
                onNavViagem = {navHostController.navigate(Route.DetalheViagem.name)},
                onItemDetail = { navHostController.navigate("${Route.DetalheViagem}/${it}") }
            )
        }
        composable("${Route.DetalheViagem}/{id}"){
            val id = it.arguments?.getString("id")
            DescViagens(
                id = id ?: "0",
                onNavNovoGasto = { navHostController.navigate("${Route.NovoGastoViagem}/${it}") },
                onEditViagem = { navHostController.navigate("${Route.EditarViagem}/${it}") },
                onEditGasto = { navHostController.navigate("${Route.EditarGasto}/${it}") },
                onBack = { navHostController.popBackStack() }
            )
        }
        composable("${Route.EditarViagem}/{id}"){
            val id = it.arguments?.getString("id")
            EditarViagens(
                id = id ?: "0",
                onBack = { navHostController.popBackStack() },
            )
        }
        composable("${Route.NovoGastoViagem}/{id}"){
            val id = it.arguments?.getString("id")
            GastosViagem(
                id = id ?: "0",
                onBack = { navHostController.popBackStack() },
                onNavDetalheViagem = { navHostController.navigate("${Route.DetalheViagem}/${it}") }
            )
        }
        composable("${Route.EditarGasto}/{id}"){
            val id = it.arguments?.getString("id")
            EditarGastosViagem(
                id = id ?: "0",
                onBack = { navHostController.popBackStack() },
            )
        }
        composable(Route.CadastroSucesso.name){
            cadastroSucesso(
                onNavHome = { navHostController.navigate(Route.Login.name) }
            )
        }
    }
}
