package br.com.empiricus.statusviajante.android.viagens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.empiricus.statusviajante.integration.util.DataResult
import br.com.empiricus.statusviajante.integration.model.Viagem
import br.com.empiricus.statusviajante.integration.repository.ViagensRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class ViagensViewModel(
    private val viagensRepository: ViagensRepository = ViagensRepository.instance
): ViewModel() {

    private val _viagensState = MutableStateFlow<DataResult<List<Viagem>>>(DataResult.Empty)
    val viagensState: StateFlow<DataResult<List<Viagem>>> = _viagensState

    private val _viagemState = MutableStateFlow<DataResult<Viagem>>(DataResult.Empty)
    val viagemState: StateFlow<DataResult<Viagem>> = _viagemState


    fun getViagens() = viewModelScope.launch {
        viagensRepository.getViagens().collectLatest {
            _viagensState.value = it
        }
    }

    fun getViagensById(id: Long) = viewModelScope.launch {
        viagensRepository.getViagemById(id).collectLatest {
            _viagemState.value = it
        }
    }

    fun getViagensByNome(nome: String) = viewModelScope.launch {
        viagensRepository.getViagemByNome(nome).collectLatest {
            _viagensState.value = it
        }
    }

    fun postViagem(viagem: Viagem) = viewModelScope.launch {
        viagensRepository.postViagem(viagem).collectLatest {
            _viagemState.value = it
        }
    }
      fun putViagem(id: Long, viagem: Viagem) = viewModelScope.launch {
        viagensRepository.putViagem(id, viagem).collectLatest {
            _viagemState.value = it
        }
    }
      fun deleteViagem(id: Long) = viewModelScope.launch {
        viagensRepository.deleteViagem(id).collectLatest {
        }
    }



}