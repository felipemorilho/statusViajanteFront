package br.com.empiricus.statusviajante.android

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
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
import br.com.empiricus.statusviajante.android.usuario.DadosUsuario
import br.com.empiricus.statusviajante.android.viagens.EditarViagens
import br.com.empiricus.statusviajante.android.viagens.Viagens

enum class  Route {
    Login,
    Cadastro,
    CadastroSucesso,
    CadastroViagens,
    HomeViagens,
    DetalheViagem,
    DadosUsuario,
    EditarViagem,
    NovoGastoViagem,
    EditarGasto
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Navigator(
    navHostController: NavHostController = rememberNavController(),
    initial: Route = Route.Login
) {
    NavHost(
        navController = navHostController,
        startDestination = initial.name
    ) {
        composable(Route.CadastroViagens.name) {
            cadastroViagens(
                onNavDadosUsuario = {navHostController.navigate(Route.DadosUsuario.name)},
                onNavHome = {navHostController.navigate(Route.HomeViagens.name)},
                onNavLogin = {navHostController.navigate(Route.Login.name)},
                onBack = {navHostController.popBackStack()}

            )
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
                onItemDetail = { navHostController.navigate("${Route.DetalheViagem}/${it}") },
                onNavLogin = { navHostController.navigate(Route.Login.name) },
                onNavDadosUsuario = { navHostController.navigate(Route.DadosUsuario.name) },
                onNavBack = {navHostController.popBackStack()}
            )
        }
        composable("${Route.DetalheViagem}/{id}"){ it ->
            val id = it.arguments?.getString("id")
            DescViagens(
                id = id ?: "0",
                onNavNovoGasto = { navHostController.navigate("${Route.NovoGastoViagem}/${it}") },
                onEditViagem = { navHostController.navigate("${Route.EditarViagem}/${it}") },
                onEditGasto = { navHostController.navigate("${Route.EditarGasto}/${it}") },
                onNavDadosUsuario = {navHostController.navigate(Route.DadosUsuario.name)},
                onNavHome = {navHostController.navigate(Route.HomeViagens.name)},
                onNavLogin = {navHostController.navigate(Route.Login.name)},
                onNavCadastroViagens = {navHostController.navigate(Route.CadastroViagens.name)},
                onBack = { navHostController.popBackStack() }
            )
        }
        composable("${Route.EditarViagem}/{id}"){
            val id = it.arguments?.getString("id")
            EditarViagens(
                id = id ?: "0",
                onBack = { navHostController.popBackStack() },
                onNavDadosUsuario = {navHostController.navigate(Route.DadosUsuario.name)},
                onNavHome = {navHostController.navigate(Route.HomeViagens.name)},
                onNavLogin = {navHostController.navigate(Route.Login.name)},
                onNavCadastroViagens = {navHostController.navigate(Route.CadastroViagens.name)},
            )
        }
        composable("${Route.NovoGastoViagem}/{id}"){
            val id = it.arguments?.getString("id")
            GastosViagem(
                id = id ?: "0",
                onBack = { navHostController.popBackStack() },
                onNavDadosUsuario = {navHostController.navigate(Route.DadosUsuario.name)},
                onNavHome = {navHostController.navigate(Route.HomeViagens.name)},
                onNavLogin = {navHostController.navigate(Route.Login.name)},
                onNavCadastroViagens = {navHostController.navigate(Route.CadastroViagens.name)},
            )
        }
        composable("${Route.EditarGasto}/{id}"){
            val id = it.arguments?.getString("id")
            EditarGastosViagem(

                id = id ?: "0",
                onBack = { navHostController.popBackStack() },
                onNavDadosUsuario = {navHostController.navigate(Route.DadosUsuario.name)},
                onNavHome = {navHostController.navigate(Route.HomeViagens.name)},
                onNavLogin = {navHostController.navigate(Route.Login.name)},
                onNavCadastroViagens = {navHostController.navigate(Route.CadastroViagens.name)},
            )
        }
        composable(Route.CadastroSucesso.name){
            cadastroSucesso(
                onNavHome = { navHostController.navigate(Route.Login.name) }
            )
        }
        composable(Route.DadosUsuario.name){
            DadosUsuario(
                onBack = {navHostController.popBackStack()},
                onNavHome = {navHostController.navigate(Route.HomeViagens.name)},
                onNavLogin = {navHostController.navigate(Route.Login.name)},
                onNavCadastroViagens = {navHostController.navigate(Route.CadastroViagens.name)},
            )
        }
    }
}
