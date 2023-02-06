package br.com.empiricus.statusviajante.model

import kotlinx.datetime.LocalDate

object MockCadastroUsuario {

    val cadastroUsuario: CadastroUsuario =
            CadastroUsuario(
                "Felipe Morilho",
                "felipe@gmail.com",
                "Qwerty123#",
                LocalDate(1989,11,7),
                "47999990055"
            )
}