package br.com.empiricus.statusviajante.model.model

import br.com.empiricus.statusviajante.model.util.DateSerialize
import kotlinx.serialization.Serializable

@Serializable
data class GastoViagem (
    val id: Long = 0,
    @Serializable(with = DateSerialize::class)
    val dataGasto: String,
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
data class GastosResponse(
    val gastosViagem: List<GastoViagem>
)

