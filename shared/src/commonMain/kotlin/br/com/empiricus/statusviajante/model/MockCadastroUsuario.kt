package br.com.empiricus.statusviajante.model

import br.com.empiricus.statusviajante.model.model.CadastroUsuario
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

object MockCadastroUsuario {

    val usuariosCadastrados: MutableList<CadastroUsuario> =
           mutableListOf(
               CadastroUsuario(
                   "Felipe Morilho",
                   "felipe@gmail.com",
                   "Qwerty123#",
                   "1989-11-7",
                   "47999990055"
               )
           )
    val data = Json.encodeToString(usuariosCadastrados)
    val deserialize = Json.decodeFromString<CadastroUsuario>(data)
}