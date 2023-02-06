package br.com.empiricus.statusviajante.model

import br.com.empiricus.statusviajante.model.model.GastoViagem

object MockGastoViagem {

    val gastos: List<GastoViagem> =
        listOf(
            GastoViagem(
                "5/1/2023",
                GastoViagem.Categoria.HOSPEDAGEM,
                250.00,
                "Pagamento do Hotel"
            ),
            GastoViagem(
                "6/1/2023",
                GastoViagem.Categoria.TRANSPORTE,
                50.00,
                "Táxi até o parque"
            ),
            GastoViagem(
                "6/1/2023",
                GastoViagem.Categoria.LAZER,
                550.00,
                "Ingresso para o parque"
            ),
            GastoViagem(
                "6/1/2023",
                GastoViagem.Categoria.ALIMENTACAO,
                150.00,
                "Almoço no parque"
            ),
            GastoViagem(
                "7/1/2023",
                GastoViagem.Categoria.OUTROS,
                92.53,
                "Gasto com lembrancinhas"
            )
        )
}