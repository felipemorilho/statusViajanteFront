package br.com.empiricus.statusviajante.integration.repository

import br.com.digitalhouse.dhwallet.extension.updateState
import br.com.digitalhouse.dhwallet.util.DataResult
import br.com.empiricus.statusviajante.integration.api.Api
import br.com.empiricus.statusviajante.integration.model.Usuario
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class CadastroUsuarioRepository(
    private val api: Api = Api.instance,
    private val dispatcher: CoroutineDispatcher = Dispatchers.Default
) {
    suspend fun cadastrar(cadastroUsuario: Usuario) = flow<DataResult<Usuario>>{
        val data = DataResult.Success(api.cadastro(cadastroUsuario))
        emit(data)
    }.updateState().flowOn(dispatcher)

    companion object{
        val instance by lazy { CadastroUsuarioRepository() }
    }

}