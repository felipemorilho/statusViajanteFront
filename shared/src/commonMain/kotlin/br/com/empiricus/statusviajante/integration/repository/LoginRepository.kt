package br.com.empiricus.statusviajante.integration.repository

import br.com.digitalhouse.dhwallet.extension.updateState
import br.com.empiricus.statusviajante.integration.util.DataResult
import br.com.empiricus.statusviajante.integration.UserLogin
import br.com.empiricus.statusviajante.integration.api.Api
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class LoginRepository(
    private val api: Api = Api.instance,
    private val dispatcher: CoroutineDispatcher = Dispatchers.Default
) {

    suspend fun login(login: UserLogin) = flow<DataResult<*>> {
        val data = api.login(login)
        Api.token = data
        emit(DataResult.Success(data))
    }.updateState().flowOn(dispatcher)

    companion object{
        val instance by lazy { LoginRepository() }
    }
}