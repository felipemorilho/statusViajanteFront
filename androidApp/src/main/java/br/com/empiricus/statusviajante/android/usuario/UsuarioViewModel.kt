package br.com.empiricus.statusviajante.android.usuario

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.empiricus.statusviajante.integration.model.Usuario
import br.com.empiricus.statusviajante.integration.repository.UsuarioRepository
import br.com.empiricus.statusviajante.integration.util.DataResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class UsuarioViewModel(private val usuarioRepository: UsuarioRepository = UsuarioRepository.instance): ViewModel() {

    private val _usuarioState: MutableStateFlow<DataResult<Usuario>> = MutableStateFlow(DataResult.Empty)
    val usuarioState: StateFlow<DataResult<Usuario>> = _usuarioState

    init { getUsuarioByUsername() }

    fun getUsuarioByUsername() = viewModelScope.launch {
        usuarioRepository.getUsuario().collectLatest {
            _usuarioState.value = it
        }
    }

    fun putUsuario(usuario: Usuario) = viewModelScope.launch {
        usuarioRepository.putUsuario(usuario).collectLatest {
            _usuarioState.value = it
        }
    }

    fun putSenhaUsuario(senhaAtual: String, novaSenha: String) = viewModelScope.launch {
        usuarioRepository.putSenhaUsuario(senhaAtual, novaSenha).collectLatest {
            _usuarioState.value = it
        }
    }

    fun deleteUsuario(id: Long) = viewModelScope.launch {
        usuarioRepository.deleteUsuario(id).collectLatest {
        }
    }

}