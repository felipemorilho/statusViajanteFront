package br.com.empiricus.statusviajante.model

import kotlinx.datetime.LocalDate
import kotlinx.serialization.Serializable

@Serializable
data class GastoViagem (
    val dataGasto: LocalDate,
    val categoria: Categoria,
    val valor: Double,
    val descricao: String
        ) {

    enum class Categoria(val category: String){
        LAZER("Lazer"),
        HOSPEDAGEM("Hospedagem"),
        TRANSPORTE("Transporte"),
        ALIMENTACAO("Alimentação"),
        OUTROS("Outros")
    }
}