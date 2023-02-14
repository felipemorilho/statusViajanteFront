package br.com.empiricus.statusviajante.model.model

import kotlinx.serialization.Serializable

@Serializable
data class Login(
    val usuario: String,
    val senha: String
)