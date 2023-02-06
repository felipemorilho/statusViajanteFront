package br.com.empiricus.statusviajante.model

import kotlinx.datetime.LocalDate

object MockGastoViagem {

    val gastos: List<GastoViagem> =
        listOf(
            GastoViagem(
                LocalDate(2023, 1, 5),
                GastoViagem.Categoria.HOSPEDAGEM,
                250.00,
                "Pagamento do Hotel"
            ),
            GastoViagem(
                LocalDate(2023, 1, 6),
                GastoViagem.Categoria.TRANSPORTE,
                50.00,
                "Táxi até o parque"
            ),
            GastoViagem(
                LocalDate(2023, 1, 6),
                GastoViagem.Categoria.LAZER,
                550.00,
                "Ingresso para o parque"
            ),
            GastoViagem(
                LocalDate(2023, 1, 6),
                GastoViagem.Categoria.ALIMENTACAO,
                150.00,
                "Almoço no parque"
            ),
            GastoViagem(
                LocalDate(2023, 1, 7),
                GastoViagem.Categoria.OUTROS,
                92.53,
                "Gasto com lembrancinhas"
            )
        )
}