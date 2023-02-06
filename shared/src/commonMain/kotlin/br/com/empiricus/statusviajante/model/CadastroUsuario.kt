package br.com.empiricus.statusviajante.model

import kotlinx.datetime.LocalDate

@kotlinx.serialization.Serializable
data class CadastroUsuario(
    val nome: String,
    val email: String,
    val senha: String,
    val dataNascimento: LocalDate,
    val celular: String
        ){

}