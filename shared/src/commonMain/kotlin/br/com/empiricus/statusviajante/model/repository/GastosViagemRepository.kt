package br.com.empiricus.statusviajante.model.repository

import br.com.digitalhouse.dhwallet.extension.updateState
import br.com.digitalhouse.dhwallet.util.DataResult
import br.com.empiricus.statusviajante.model.api.Api
import br.com.empiricus.statusviajante.model.model.GastoViagem
import kotlinx.coroutines.CloseableCoroutineDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class GastosViagemRepository(
    private val api: Api = Api.instance,
    private val dispatcher: CoroutineDispatcher = Dispatchers.Default
) {

    suspend fun getGastos() = flow{
        val chamada = api.getAllGastos().gastosViagem

        if (chamada.isEmpty()) {
            emit(DataResult.Empty)
        } else {
            emit(DataResult.Success(chamada))
        }
    }.updateState().flowOn(dispatcher)

    suspend fun getGastosById(id: Long) = flow<DataResult<GastoViagem>> {
        val chamada = api.getGastosById(id)
        emit(DataResult.Success(chamada))
    }

    suspend fun postGastos(gastoViagem: GastoViagem) = flow<DataResult<GastoViagem>> {
        val chamda = api.postGastos(gastoViagem)
        emit(DataResult.Success(chamda))
    }

    suspend fun putGastos(gastoViagem: GastoViagem) = flow<DataResult<GastoViagem>> {
        val chamada = api.putGastos(gastoViagem)
        emit(DataResult.Success(chamada))
    }

    suspend fun deleteGastos(id: Long) = flow<DataResult<*>> {
        val chamada = api.deleteGastos(id)
        emit(DataResult.Success(chamada))
    }


    companion object{
        val instance by lazy { GastosViagemRepository() }
    }

}