package br.com.empiricus.statusviajante.model.model

import br.com.empiricus.statusviajante.model.util.DateSerialize
import kotlinx.datetime.LocalDate
import kotlinx.serialization.Serializable

@Serializable
data class Viagem(
    val id: Long,
    val nome: String,
    val origem: String,
    val destino: String,
    @Serializable(with = DateSerialize::class)
    val dataInicio: String,
    @Serializable(with = DateSerialize::class)
    val dataFinal: String,
    val orcamentoTotak: Double,
    val orcamentoDiario: Double,
    val quantidadeViajantes: Int,
    val descricao: String
){

}
