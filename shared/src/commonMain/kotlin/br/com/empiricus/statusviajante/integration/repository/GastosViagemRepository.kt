package br.com.empiricus.statusviajante.integration.repository

import br.com.digitalhouse.dhwallet.extension.updateState
import br.com.digitalhouse.dhwallet.util.DataResult
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

    suspend fun getGastosCategoria(categoria: String) = flow {
        val chamada = api.getGastosCategoria(categoria)
        emit(DataResult.Success(chamada))
    }.updateState().flowOn(dispatcher)

    suspend fun getGastosById(id: Long) = flow {
        val chamada = api.getGastosById(id)
        emit(DataResult.Success(chamada))
    }.updateState().flowOn(dispatcher)

    suspend fun postGastos(gastoViagem: GastoViagem, id: Long) = flow {
        val chamda = api.postGastos(gastoViagem, id)
        emit(DataResult.Success(chamda))
    }.updateState().flowOn(dispatcher)

    suspend fun putGastos(gastoViagem: GastoViagem) = flow<DataResult<GastoViagem>> {
        val chamada = api.putGastos(gastoViagem)
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