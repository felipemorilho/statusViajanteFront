package br.com.empiricus.statusviajante.model.repository

import br.com.digitalhouse.dhwallet.extension.updateState
import br.com.digitalhouse.dhwallet.util.DataResult
import br.com.empiricus.statusviajante.model.api.Api
import br.com.empiricus.statusviajante.model.model.Viagem
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class ViagensRepository(
    private val api: Api = Api.instance,
    private val dispatcher: CoroutineDispatcher = Dispatchers.Default
) {

    suspend fun getViagens() = flow{
        val chamada = api.getAllViagens().viagens

        if (chamada.isEmpty()) {
            emit(DataResult.Empty)
        } else {
            emit(DataResult.Success(chamada))
        }
    }.updateState().flowOn(dispatcher)

    suspend fun getViagemById(id: Long) = flow<DataResult<Viagem>> {
        val chamada = api.getViagemById(id)
        emit(DataResult.Success(chamada))
    }

    suspend fun postViagem(viagem: Viagem) = flow<DataResult<Viagem>> {
        val chamada = api.postViagem(viagem)
        emit(DataResult.Success(chamada))
    }

    suspend fun putViagem(viagem: Viagem) = flow<DataResult<Viagem>> {
        val chamada = api.putViagem(viagem)
        emit(DataResult.Success(chamada))
    }

    suspend fun deleteViagem(id: Long) = flow<DataResult<*>> {
        val chamada = api.deleteViagem(id)
        emit(DataResult.Success(chamada))
    }


    companion object{
        val instance by lazy { ViagensRepository() }
    }

}