package br.com.empiricus.statusviajante.integration.model

import kotlinx.serialization.Serializable

@Serializable
data class Viagem(
    val id: Long = 0,
    val nome: String,
    val origem: String,
    val destino: String,
    val dataIda: String,
    val dataVolta: String,
    val diasDeViagem: Int,
    val gastoTotal: Double,
    val orcamento: Double,
    val orcamentoDiario: Double = 0.0,
    val orcamentoRestante: Double = 0.0,
    val qtdPessoas: Int,
    val descricaoViagem: String
)