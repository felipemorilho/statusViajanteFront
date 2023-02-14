package br.com.empiricus.statusviajante.model.repository

import br.com.digitalhouse.dhwallet.extension.updateState
import br.com.digitalhouse.dhwallet.util.DataResult
import br.com.empiricus.statusviajante.model.api.Api
import br.com.empiricus.statusviajante.model.model.CadastroUsuario
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class CadastroUsuarioRepository(
    private val api: Api = Api.instance,
    private val dispatcher: CoroutineDispatcher = Dispatchers.Default
) {
    suspend fun cadastrar(cadastroUsuario: CadastroUsuario) = flow<DataResult<CadastroUsuario>>{
        val data = DataResult.Success(api.cadastro(cadastroUsuario))
        emit(data)
    }.updateState().flowOn(dispatcher)

    companion object{
        val instance by lazy { CadastroUsuarioRepository() }
    }

}