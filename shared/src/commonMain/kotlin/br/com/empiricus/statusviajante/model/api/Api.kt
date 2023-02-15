package br.com.empiricus.statusviajante.model.api

import br.com.empiricus.statusviajante.model.model.*
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
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
            header(Authorization,"$token")
        }
    }

    //============================ Login ===================================
    suspend fun login(login: Login): ProfileToken {
        return httpClient.post("$DEFAULT_URL/usuarios/logar") {
            setBody(login)
        }.body()
    }

    //============================ Cadastro =================================
    suspend fun cadastro(cadastroUsuario: CadastroUsuario): CadastroUsuario {
        return httpClient.post("$DEFAULT_URL/usuarios/cadastrar") {
            setBody(cadastroUsuario)
        }.body()
    }

    //============================ Viagens ==================================
    suspend fun getAllViagens(): ViagensResponse {
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
    suspend fun getAllGastos(): GastosResponse {
        return httpClient.get("$DEFAULT_URL/gasto_viagem").body()
    }

    suspend fun getGastosById(id: Long): GastoViagem {
        return httpClient.get("$DEFAULT_URL/gastos/${id}").body()
    }

    suspend fun postGastos(gastoViagem: GastoViagem): GastoViagem {
        return httpClient.post("$DEFAULT_URL/gasto_viagem").body()
    }

    suspend fun putGastos(gastoViagem: GastoViagem): GastoViagem {
        return httpClient.put("$DEFAULT_URL/gasto_viagem").body()
    }

    suspend fun  deleteGastos(id: Long): GastoViagem {
        return httpClient.delete("$DEFAULT_URL/gastos/${id}").body()
    }

    @ThreadLocal
    companion object {
        val instance by lazy { Api() }
        var token: String = ""
        const val DEFAULT_URL =
            "http://statusviajante-env.eba-nvmskkkr.us-east-1.elasticbeanstalk.com"
    }

}