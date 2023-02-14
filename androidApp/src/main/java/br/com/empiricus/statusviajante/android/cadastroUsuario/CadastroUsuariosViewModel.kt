package br.com.empiricus.statusviajante.android.cadastroUsuario

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.digitalhouse.dhwallet.util.DataResult
import br.com.empiricus.statusviajante.model.model.CadastroUsuario
import br.com.empiricus.statusviajante.model.repository.CadastroUsuarioRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class CadastroUsuariosViewModel(
    private val cadastroUsuarioRepository: CadastroUsuarioRepository = CadastroUsuarioRepository.instance
): ViewModel() {

    private val _cadastro: MutableStateFlow<DataResult<CadastroUsuario>> = MutableStateFlow(DataResult.Empty)
    val cadastro : StateFlow<DataResult<CadastroUsuario>> = _cadastro

    fun cadastrar(
        nome: String,
        nomeUsuario: String,
        email: String,
        senha: String,
        dataNascimento: String,
        celular: String
    ) = viewModelScope.launch {
        val cadastrar = CadastroUsuario(
            nome = nome,
            nomeUsuario = nomeUsuario,
            email = email,
            senha = senha,
            dataNascimento = dataNascimento,
            celular = celular
        )

        cadastroUsuarioRepository.cadastrar(cadastrar).collectLatest {
            _cadastro.value = it
        }
    }

}