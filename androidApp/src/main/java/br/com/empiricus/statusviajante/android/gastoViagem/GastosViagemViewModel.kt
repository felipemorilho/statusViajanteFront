package br.com.empiricus.statusviajante.android.gastoViagem


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.digitalhouse.dhwallet.util.DataResult
import br.com.empiricus.statusviajante.model.model.GastoViagem
import br.com.empiricus.statusviajante.model.repository.GastosViagemRepository
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

    init {
        getGastosViagem()
    }

    fun getGastosViagem() = viewModelScope.launch {
        gastosViagemRepository.getGastos().collectLatest {
            _gastosViagemState.value = it
        }
    }

    fun getGastosById(id: Long) = viewModelScope.launch {
        gastosViagemRepository.getGastosById(id).collectLatest {
            _gastoViagemState.value = it
        }
    }

    fun postGastos(gastoViagem: GastoViagem) = viewModelScope.launch {
        val gastoViagemCadastro = GastoViagem(
            dataGasto = gastoViagem.dataGasto,
            categoria = gastoViagem.categoria,
            valor = gastoViagem.valor,
            descricao = gastoViagem.descricao
        )

        gastosViagemRepository.postGastos(gastoViagemCadastro).collectLatest {
            _gastoViagemState.value = it
        }
    }

    fun putGastos(gastoViagem: GastoViagem) = viewModelScope.launch {
        val gastoViagemoAtualizacao= GastoViagem(
            dataGasto = gastoViagem.dataGasto,
            categoria = gastoViagem.categoria,
            valor = gastoViagem.valor,
            descricao = gastoViagem.descricao
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