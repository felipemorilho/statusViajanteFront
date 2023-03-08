package br.com.empiricus.statusviajante.integration.repository

import br.com.digitalhouse.dhwallet.extension.updateState
import br.com.empiricus.statusviajante.integration.api.Api
import br.com.empiricus.statusviajante.integration.model.Usuario
import br.com.empiricus.statusviajante.integration.util.DataResult
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class UsuarioRepository(
    private val api: Api = Api.instance,
    private val dispatcher: CoroutineDispatcher = Dispatchers.Default
) {

    suspend fun getUsuario() = flow {
        val chamada = api.getUsuarioByUsername()
        emit(DataResult.Success(chamada))
    }.updateState().flowOn(dispatcher)

    suspend fun putUsuario(usuario: Usuario) = flow {
        val chamada = api.putUsuario(usuario)
        emit(DataResult.Success(chamada))
    }.updateState().flowOn(dispatcher)

    suspend fun putSenhaUsuario(senhaAtual:String, novaSenha: String) = flow {
        val chamada = api.putSenhaUsuario(senhaAtual, novaSenha)
        emit(DataResult.Success(chamada))
    }.updateState().flowOn(dispatcher)

    suspend fun deleteUsuario(id: Long) = flow {
        val chamada = api.deleteUsuario(id)
        emit(DataResult.Success(chamada))
    }.updateState().flowOn(dispatcher)

    companion object{
        val instance by lazy { UsuarioRepository() }
    }
}