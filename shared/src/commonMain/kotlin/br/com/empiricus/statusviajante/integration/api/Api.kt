package br.com.empiricus.statusviajante.integration.api

import br.com.empiricus.statusviajante.integration.UserLogin
import br.com.empiricus.statusviajante.integration.model.GastoViagem
import br.com.empiricus.statusviajante.integration.model.Usuario
import br.com.empiricus.statusviajante.integration.model.Viagem
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.http.*
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
    suspend fun login(login: UserLogin): String {
        val response = httpClient.post("$DEFAULT_URL/login") {
            setBody(login)
        }
        val token = response.headers[Authorization]
        if (token != null) {
            return token
        } else {
            throw RuntimeException("Authentication failed")
        }
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

    suspend fun putViagem(id: Long, viagem: Viagem): Viagem {
        return httpClient.put("$DEFAULT_URL/viagens/${id}") {
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

    suspend fun getGastosCategoria(categoria: String): List<GastoViagem> {
        return httpClient.get("$DEFAULT_URL/gastos/categoria/${categoria}").body()
    }

    suspend fun getGastosById(id: Long): GastoViagem {
        return httpClient.get("$DEFAULT_URL/gastos/${id}").body()
    }

    suspend fun postGastos(id: Long, gastoViagem: GastoViagem): GastoViagem {
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