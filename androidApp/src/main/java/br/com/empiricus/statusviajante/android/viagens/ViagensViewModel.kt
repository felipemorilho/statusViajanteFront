package br.com.empiricus.statusviajante.android.viagens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.digitalhouse.dhwallet.util.DataResult
import br.com.empiricus.statusviajante.model.model.Viagem
import br.com.empiricus.statusviajante.model.repository.ViagensRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class ViagensViewModel(
    val viagensRepository: ViagensRepository = ViagensRepository.instance
): ViewModel() {

    private val _viagens = MutableStateFlow<DataResult<List<Viagem>>>(DataResult.Empty)
    val viagens: StateFlow<DataResult<List<Viagem>>> = _viagens

    private val _viagem = MutableStateFlow<DataResult<Viagem>>(DataResult.Empty)
    val viagem: StateFlow<DataResult<Viagem>> = _viagem

    init {
        getViagens()
    }

    fun getViagens() = viewModelScope.launch {
        viagensRepository.getViagens().collectLatest {
            _viagens.value = it
        }
    }

    fun getViagensById(id: Long) = viewModelScope.launch {
        viagensRepository.getViagemById(id).collectLatest {
            _viagem.value = it
        }
    }


}