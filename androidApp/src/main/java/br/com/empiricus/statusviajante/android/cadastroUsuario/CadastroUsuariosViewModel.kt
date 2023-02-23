package br.com.empiricus.statusviajante.android.cadastroUsuario

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.digitalhouse.dhwallet.util.DataResult
import br.com.empiricus.statusviajante.integration.model.Usuario
import br.com.empiricus.statusviajante.integration.repository.CadastroUsuarioRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class CadastroUsuariosViewModel(
    private val cadastroUsuarioRepository: CadastroUsuarioRepository = CadastroUsuarioRepository.instance
): ViewModel() {

    private val _cadastro: MutableStateFlow<DataResult<Usuario>> = MutableStateFlow(DataResult.Empty)
    val cadastro : StateFlow<DataResult<Usuario>> = _cadastro

    fun cadastrar(usuario: Usuario) = viewModelScope.launch {
        cadastroUsuarioRepository.cadastrar(usuario).collectLatest {
            _cadastro.value = it
        }
    }

}