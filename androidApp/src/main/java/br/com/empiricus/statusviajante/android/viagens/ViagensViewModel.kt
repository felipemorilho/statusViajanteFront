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
    private val viagensRepository: ViagensRepository = ViagensRepository.instance
): ViewModel() {

    private val _viagensState = MutableStateFlow<DataResult<List<Viagem>>>(DataResult.Empty)
    val viagensState: StateFlow<DataResult<List<Viagem>>> = _viagensState

    private val _viagemState = MutableStateFlow<DataResult<Viagem>>(DataResult.Empty)
    val viagemState: StateFlow<DataResult<Viagem>> = _viagemState

    init {
        getViagens()
    }

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

    fun postViagem(viagem: Viagem) = viewModelScope.launch {
        val viagemCadastro = Viagem(
            nome = viagem.nome,
            origem = viagem.origem,
            destino = viagem.destino,
            dataInicio = viagem.dataInicio,
            dataFinal = viagem.dataFinal,
            orcamentoTotal = viagem.orcamentoTotal,
            orcamentoDiario = viagem.orcamentoDiario,
            quantidadeViajantes = viagem.quantidadeViajantes,
            descricao = viagem.descricao
        )

        viagensRepository.postViagem(viagemCadastro).collectLatest {
            _viagemState.value = it
        }
    }
      fun putViagem(viagem: Viagem) = viewModelScope.launch {
          val viagemAtualizacao = Viagem(
              nome = viagem.nome,
              origem = viagem.origem,
              destino = viagem.destino,
              dataInicio = viagem.dataInicio,
              dataFinal = viagem.dataFinal,
              orcamentoTotal = viagem.orcamentoTotal,
              orcamentoDiario = viagem.orcamentoDiario,
              quantidadeViajantes = viagem.quantidadeViajantes,
              descricao = viagem.descricao
          )
        viagensRepository.putViagem(viagemAtualizacao).collectLatest {
            _viagemState.value = it
        }
    }
      fun deleteViagem(viagem: Viagem) = viewModelScope.launch {
          val viagemId = viagem.id
        viagensRepository.deleteViagem(viagemId).collectLatest {
        }
    }



}