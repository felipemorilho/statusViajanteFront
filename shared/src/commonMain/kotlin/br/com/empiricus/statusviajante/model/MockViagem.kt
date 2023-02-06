package br.com.empiricus.statusviajante.model

import kotlinx.datetime.LocalDate

object MockViagem {

    val viagens: List<Viagem> =
        listOf(
            Viagem(
                1,
                "Férias de android",
                "Brasil",
                "Empiricus",
                "Real",
                LocalDate(2023,2,2),
                LocalDate(2024,5,5),
                2121.0,
                12,
                "Ferias"
            ),
            Viagem(
                2,
                "Férias de Kotlin",
                "Brasil",
                "Santa Catarina",
                "Real",
                LocalDate(2023,4,3),
                LocalDate(2024,5,6),
                1231.0,
                10,
                "Ferias novamente"
            ),
            Viagem(
                3,
                "Férias Rio de Janeiro",
                "Brasil",
                "Rio de Janeiro",
                "Real",
                LocalDate(2023,7,8),
                LocalDate(2024,6,7),
                1231.0,
                5,
                "Ferias novamente"
            ),
        )

}