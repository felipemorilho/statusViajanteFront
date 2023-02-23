package br.com.empiricus.statusviajante.integration.model

import kotlinx.serialization.Serializable

@Serializable
data class Usuario(
    val nome: String,
    val usuario: String,
    val email: String,
    val senha: String,
    val celular: String
)