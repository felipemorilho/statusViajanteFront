package br.com.empiricus.statusviajante.integration

import kotlinx.serialization.Serializable

@Serializable
data class UserLogin(val usuario: String, val senha: String)