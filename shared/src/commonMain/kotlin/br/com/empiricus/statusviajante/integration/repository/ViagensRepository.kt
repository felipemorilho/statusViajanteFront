package br.com.empiricus.statusviajante.integration.repository

import br.com.digitalhouse.dhwallet.extension.updateState
import br.com.empiricus.statusviajante.integration.util.DataResult
import br.com.empiricus.statusviajante.integration.api.Api
import br.com.empiricus.statusviajante.integration.model.Viagem
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class ViagensRepository(
    private val api: Api = Api.instance,
    private val dispatcher: CoroutineDispatcher = Dispatchers.Default
) {

    suspend fun getViagens() = flow{
        val chamada = api.getAllViagens()

        if (chamada.isEmpty()) {
            emit(DataResult.Empty)
        } else {
            emit(DataResult.Success(chamada))
        }
    }.updateState().flowOn(dispatcher)

    suspend fun getViagemById(id: Long) = flow<DataResult<Viagem>> {
        val chamada = api.getViagemById(id)
        emit(DataResult.Success(chamada))
    }.updateState().flowOn(dispatcher)

    suspend fun postViagem(viagem: Viagem) = flow<DataResult<Viagem>> {
        val chamada = api.postViagem(viagem)
        emit(DataResult.Success(chamada))
    }.updateState().flowOn(dispatcher)

    suspend fun putViagem(id: Long, viagem: Viagem) = flow<DataResult<Viagem>> {
        val chamada = api.putViagem(id, viagem)
        emit(DataResult.Success(chamada))
    }.updateState().flowOn(dispatcher)

    suspend fun deleteViagem(id: Long) = flow<DataResult<*>> {
        val chamada = api.deleteViagem(id)
        emit(DataResult.Success(chamada))
    }.updateState().flowOn(dispatcher)
    


    companion object{
        val instance by lazy { ViagensRepository() }
    }

}