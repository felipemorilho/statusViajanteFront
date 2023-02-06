package br.com.empiricus.statusviajante.model.model

import br.com.empiricus.statusviajante.model.util.DateSerialize
import kotlinx.serialization.Serializable

@Serializable
data class CadastroUsuario(
    val nome: String,
    val email: String,
    val senha: String,
    @Serializable(with = DateSerialize::class)
    val dataNascimento: String,
    val celular: String
        )