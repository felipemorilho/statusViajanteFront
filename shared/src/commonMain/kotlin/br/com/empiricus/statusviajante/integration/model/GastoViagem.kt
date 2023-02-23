package br.com.empiricus.statusviajante.integration.model

import br.com.empiricus.statusviajante.integration.util.DateSerialize
import kotlinx.serialization.Serializable

@Serializable
data class GastoViagem (
    val id: Long = 0,
    @Serializable(with = DateSerialize::class)
    val dataGasto: String,
    val categoria: String,
    val valorGasto: Double,
    val moeda: String,
    val descricaoGasto: String
)

