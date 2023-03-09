package br.com.empiricus.statusviajante.integration.model

import br.com.empiricus.statusviajante.integration.util.DateSerialize
import kotlinx.datetime.LocalDate
import kotlinx.serialization.Serializable

@Serializable
data class GastoViagem (
    val id: Long = 0,
    val dataGasto: String,
    val categoria: String,
    val valorGasto: Double,
    val moeda: String,
    val descricaoGasto: String
)

