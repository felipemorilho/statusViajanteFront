package br.com.empiricus.statusviajante

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform