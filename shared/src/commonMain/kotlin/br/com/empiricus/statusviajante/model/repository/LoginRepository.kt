package br.com.empiricus.statusviajante.model.repository

import br.com.digitalhouse.dhwallet.extension.updateState
import br.com.digitalhouse.dhwallet.util.DataResult
import br.com.empiricus.statusviajante.model.api.Api
import br.com.empiricus.statusviajante.model.model.Login
import br.com.empiricus.statusviajante.model.model.ProfileToken
import br.com.empiricus.statusviajante.model.model.Viagem
import kotlinx.coroutines.CloseableCoroutineDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class LoginRepository(
    private val api: Api = Api.instance,
    private val dispatcher: CoroutineDispatcher = Dispatchers.Default
) {

    suspend fun login(login: Login) = flow<DataResult<ProfileToken>> {
        val data = DataResult.Success(api.login(login))
        Api.token = data.data.token
        emit(data)
    }.updateState().flowOn(dispatcher)

    companion object{
        val instance by lazy { LoginRepository() }
    }
}