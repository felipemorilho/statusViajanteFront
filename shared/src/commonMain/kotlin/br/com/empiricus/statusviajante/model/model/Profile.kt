package br.com.empiricus.statusviajante.model.model

@kotlinx.serialization.Serializable
class ProfileToken(val token: String)

@kotlinx.serialization.Serializable
class Profile(val name: String, val email: String, val photo: String)