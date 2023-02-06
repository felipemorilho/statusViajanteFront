package br.com.empiricus.statusviajante.model

import kotlinx.serialization.Serializable
import kotlinx.datetime.LocalDate

@Serializable

data class Viagem (
    val idViagem : Long,
    val nomeViagem : String,
    val origem : String,
    val destino : String,
    val moeda : String,
    val dataIda : LocalDate,
    val dataVolta : LocalDate,
    val orcamento : Double,
    val qtdPessoas : Int,
    val descricaoViagem : String

        )