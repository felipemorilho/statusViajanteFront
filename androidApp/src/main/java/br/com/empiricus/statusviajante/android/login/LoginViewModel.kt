package br.com.empiricus.statusviajante.android.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.digitalhouse.dhwallet.util.DataResult
import br.com.empiricus.statusviajante.integration.UserLogin
import br.com.empiricus.statusviajante.integration.model.ProfileToken
import br.com.empiricus.statusviajante.integration.repository.LoginRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class LoginViewModel(private val loginRepository: LoginRepository = LoginRepository.instance): ViewModel() {

    private val _loginState: MutableStateFlow<DataResult<ProfileToken>> = MutableStateFlow(
        DataResult.Empty)
    val loginState: StateFlow<DataResult<ProfileToken>> = _loginState

    fun login(usuario: String, senha: String) = viewModelScope.launch {
        val user = UserLogin(usuario = usuario, senha = senha)

        loginRepository.login(user).collectLatest {
            _loginState.value = it
        }
    }

}