package br.com.empiricus.statusviajante.model

import kotlinx.serialization.Serializable

@Serializable
data class Login (

    val usuario :String,
    val senha : String,

)
