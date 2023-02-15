package br.com.empiricus.statusviajante.android.gastoViagem

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.digitalhouse.dhwallet.util.DataResult
import br.com.empiricus.statusviajante.model.model.GastoViagem
import br.com.empiricus.statusviajante.model.repository.GastosViagemRepository
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class GastosViagemViewModel(
    val gastosViagemRepository: GastosViagemRepository.instance
) : ViewModel() {

    private val _gastosViagemState = mutableStateOf<DataResult<List<GastoViagem>>>(DataResult.Empty)
    val gastosViagemState: StateFlow<DataResult<List<GastoViagem>>> = _gastosViagemState

    init {
        getGastosViagem()
    }

    fun getGastosViagem() = viewModelScope.launch {
        gastosViagemRepository.getGastosViagem().collectLastest {
            _gastosViagemState.value = it
        }
    }

    fun getGastosById(id: Long) = viewModelScope.launch {
        gastosViagemRepository.getGastosById(id).collectLastest {
            _gastosViagemState.value = it
        }
    }

    fun postGastos(gastoViagem: GastoViagem) = viewModelScope.launch {
        val gastoViagemCadastro: GastoViagem(
        dataGasto = GastoViagem.dataGasto,
        categoria = GastoViagem.categoria,
        valor = GastoViagem.valor,
        descricao = GastoViagem.descricao
        )

        gastosViagemRepository.postGastos(gastosViagemCadastro).collectLateste {
            _gastosViagemState.value = it
        }
    }

    fun putGastos(gastoViagem: GastoViagem) = viewModelScope.launch {
        val gastoViagemoAtualizacao: GastoViagem(
        dataGasto = GastoViagem.dataGasto,
        categoria = GastoViagem.categoria,
        valor = GastoViagem.valor,
        descricao = GastoViagem.descricao
        )

        gastosViagemRepository.putGastos(gastosViagemCadastro).collectLateste {
            _gastosViagemState.value = it
        }
    }

    fun deleteGastos(gastoViagem: GastoViagem) = viewModelScope.launch {
        val gastosId = GastoViagem.id
        gastosViagemRepository.deleteGastos(gastosId).collectLastest {
        }
    }


}