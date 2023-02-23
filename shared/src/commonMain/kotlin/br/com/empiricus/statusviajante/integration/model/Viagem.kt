package br.com.empiricus.statusviajante.integration.model

import br.com.empiricus.statusviajante.integration.util.DateSerialize
import kotlinx.serialization.Serializable

@Serializable
data class Viagem(
    val id: Long = 0,
    val nome: String,
    val origem: String,
    val destino: String,
    @Serializable(with = DateSerialize::class)
    val dataIda: String,
    @Serializable(with = DateSerialize::class)
    val dataVolta: String,
    val diasDeViagem: Int,
    val orcamento: Double,
    val orcamentoDiario: Double,
    val gastoTotal: Double,
    val qtdPessoas: Int,
    val descricaoViagem: String
)