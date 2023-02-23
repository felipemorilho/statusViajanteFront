package br.com.empiricus.statusviajante.android.gastoViagem


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.digitalhouse.dhwallet.util.DataResult
import br.com.empiricus.statusviajante.integration.model.GastoViagem
import br.com.empiricus.statusviajante.integration.repository.GastosViagemRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class GastosViagemViewModel(
    val gastosViagemRepository: GastosViagemRepository = GastosViagemRepository.instance
) : ViewModel() {

    private val _gastoViagemState: MutableStateFlow<DataResult<GastoViagem>> = MutableStateFlow(DataResult.Empty)
    val gastoViagemState: StateFlow<DataResult<GastoViagem>> = _gastoViagemState

    private val _gastosViagemState: MutableStateFlow<DataResult<List<GastoViagem>>> = MutableStateFlow(DataResult.Empty)
    val gastosViagemState: StateFlow<DataResult<List<GastoViagem>>> = _gastosViagemState



    fun getGastosViagem(id: Long) = viewModelScope.launch {
        gastosViagemRepository.getGastos(id).collectLatest {
            _gastosViagemState.value = it
        }
    }

    fun getGastosById(id: Long) = viewModelScope.launch {
        gastosViagemRepository.getGastosById(id).collectLatest {
            _gastoViagemState.value = it
        }
    }

    fun postGastos(gastoViagem: GastoViagem, id: Long) = viewModelScope.launch {
        gastosViagemRepository.postGastos(gastoViagem, id).collectLatest {
            _gastoViagemState.value = it
        }
    }

    fun putGastos(gastoViagem: GastoViagem) = viewModelScope.launch {
        val gastoViagemoAtualizacao= GastoViagem(
            id = gastoViagem.id,
            dataGasto = gastoViagem.dataGasto,
            categoria = gastoViagem.categoria,
            valorGasto = gastoViagem.valorGasto,
            descricaoGasto = gastoViagem.descricaoGasto,
            moeda = gastoViagem.moeda
        )

        gastosViagemRepository.putGastos(gastoViagemoAtualizacao).collectLatest {
            _gastoViagemState.value = it
        }
    }

    fun deleteGastos(gastoViagem: GastoViagem) = viewModelScope.launch {
        val gastosId = gastoViagem.id
        gastosViagemRepository.deleteGastos(gastosId).collectLatest {
        }
    }


}