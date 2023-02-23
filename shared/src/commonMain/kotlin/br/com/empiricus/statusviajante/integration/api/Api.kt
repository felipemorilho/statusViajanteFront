package br.com.empiricus.statusviajante.integration.api

import br.com.empiricus.statusviajante.integration.UserLogin
import br.com.empiricus.statusviajante.integration.model.*
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.auth.*
import io.ktor.client.plugins.auth.providers.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.http.HttpHeaders.AuthenticationInfo
import io.ktor.http.HttpHeaders.Authorization
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json
import kotlin.native.concurrent.ThreadLocal

class Api {

    private val httpClient = HttpClient {
        install(ContentNegotiation) {
            json(
                Json {
                    ignoreUnknownKeys = true
                    useAlternativeNames = false
                }
            )
        }
        defaultRequest {
            contentType(ContentType.Application.Json)
            accept(ContentType.Application.Json)
            header(Authorization, token)
        }
    }

    //============================ Login ===================================
    suspend fun login(login: UserLogin): ProfileToken {
        return httpClient.post("$DEFAULT_URL/login") {
            setBody(login)
        }.body()
    }

    //============================ Cadastro =================================
    suspend fun cadastro(cadastroUsuario: Usuario): Usuario {
        return httpClient.post("$DEFAULT_URL/cadastrar") {
            setBody(cadastroUsuario)
        }.body()
    }

    //============================ Viagens ==================================
    suspend fun getAllViagens(): List<Viagem> {
        return httpClient.get("$DEFAULT_URL/viagens").body()
    }

    suspend fun getViagemById(id: Long): Viagem {
        return httpClient.get("$DEFAULT_URL/viagens/${id}").body()
    }

    suspend fun postViagem(viagem: Viagem): Viagem {
        return httpClient.post("$DEFAULT_URL/viagens") {
            setBody(viagem)
        }.body()
    }

    suspend fun putViagem(viagem: Viagem): Viagem {
        return httpClient.put("$DEFAULT_URL/viagens") {
            setBody(viagem)
        }.body()
    }

    suspend fun deleteViagem(id: Long) {
        return httpClient.delete("$DEFAULT_URL/viagens/${id}").body()
    }

    //============================ Gastos ==================================
    suspend fun getAllGastos(id: Long): List<GastoViagem> {
        return httpClient.get("$DEFAULT_URL/gastos/${id}").body()
    }

    suspend fun getGastosById(id: Long): GastoViagem {
        return httpClient.get("$DEFAULT_URL/gastos/${id}").body()
    }

    suspend fun postGastos(gastoViagem: GastoViagem, id: Long): GastoViagem {
        return httpClient.post("$DEFAULT_URL/gastos/${id}"){
            setBody(gastoViagem)
        }.body()
    }

    suspend fun putGastos(gastoViagem: GastoViagem): GastoViagem {
        return httpClient.put("$DEFAULT_URL/gastos"){
            setBody(gastoViagem)
        }.body()
    }

    suspend fun  deleteGastos(id: Long): GastoViagem {
        return httpClient.delete("$DEFAULT_URL/gastos/${id}").body()
    }

    @ThreadLocal
    companion object {
        val instance by lazy { Api() }
        var token = ""
        const val DEFAULT_URL = "http://192.168.0.109:8080"
    }

}