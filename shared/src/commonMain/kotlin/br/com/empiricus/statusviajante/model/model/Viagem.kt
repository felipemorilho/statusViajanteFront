package br.com.empiricus.statusviajante.model.model

import br.com.empiricus.statusviajante.model.util.DateSerialize
import kotlinx.serialization.Serializable

@Serializable
data class Viagem(
    val id: Long = 0,
    val nome: String,
    val origem: String,
    val destino: String,
    @Serializable(with = DateSerialize::class)
    val dataInicio: String,
    @Serializable(with = DateSerialize::class)
    val dataFinal: String,
    val orcamentoTotal: Double,
    val orcamentoDiario: Double,
    val quantidadeViajantes: Int,
    val descricao: String
)

data class ViagensResponse(
    val viagens: List<Viagem>
)