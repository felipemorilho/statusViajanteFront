package br.com.empiricus.statusviajante.integration.repository

import br.com.digitalhouse.dhwallet.extension.updateState
import br.com.empiricus.statusviajante.integration.util.DataResult
import br.com.empiricus.statusviajante.integration.api.Api
import br.com.empiricus.statusviajante.integration.model.GastoViagem
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class GastosViagemRepository(
    private val api: Api = Api.instance,
    private val dispatcher: CoroutineDispatcher = Dispatchers.Default
) {

    suspend fun getGastos(id: Long) = flow {
        val chamada = api.getAllGastos(id)
        emit(DataResult.Success(chamada))
    }.updateState().flowOn(dispatcher)

    suspend fun getGastosCategoria(id: Long, categoria: String) = flow {
        val chamada = api.getGastosCategoria(id, categoria)
        emit(DataResult.Success(chamada))
    }.updateState().flowOn(dispatcher)

    suspend fun getGastosById(id: Long) = flow {
        val chamada = api.getGastosById(id)
        emit(DataResult.Success(chamada))
    }.updateState().flowOn(dispatcher)

    suspend fun postGastos(id: Long, gastoViagem: GastoViagem) = flow {
        val chamda = api.postGastos(id, gastoViagem)
        emit(DataResult.Success(chamda))
    }.updateState().flowOn(dispatcher)

    suspend fun putGastos(id: Long, gastoViagem: GastoViagem) = flow<DataResult<GastoViagem>> {
        val chamada = api.putGastos(id, gastoViagem)
        emit(DataResult.Success(chamada))
    }.updateState().flowOn(dispatcher)

    suspend fun deleteGastos(id: Long) = flow {
        val chamada = api.deleteGastos(id)
        emit(DataResult.Success(chamada))
    }.updateState().flowOn(dispatcher)


    companion object{
        val instance by lazy { GastosViagemRepository() }
    }

}