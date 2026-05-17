package com.beautyhub.app

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

data class LoginRequest(val email: String, val password: String)

data class RegisterRequest(
    val fullName: String,
    val email: String,
    val phone: String,
    val password: String
)

data class LoginResponse(
    val token: String,
    val fullName: String? = null
)

data class SalaoApi(
    val id: Int,
    val nome: String,
    val descricao: String?,
    val endereco: String?,
    val telefone: String?,
    val mediaAvaliacao: Double?
)

data class ServicoApi(
    val id: Int,
    val nome: String,
    val descricao: String?,
    val duracaoMinutos: Int,
    val preco: Double,
    val salao: SalaoApi?
)

data class ServicoApiSimples(
    val id: Int? = null,
    val nome: String? = null,
    val preco: Double? = null,
    val duracaoMinutos: Int? = null,
    val salao: SalaoApi? = null,
    val descricao: String? = null
)

data class ClienteApi(
    val id: Int,
    val fullName: String,
    val email: String
)

data class AppointmentRequest(
    val serviceId: Int,
    val dataHoraInicio: String
)

data class AppointmentResponse(
    val id: Int,
    val status: String,
    val dataHoraInicio: String,
    val servicoNome: String? = null,
    val servicoPreco: Double? = null,
    val service: ServicoApiSimples? = null,
    val client: ClienteApi? = null
)

interface ApiService {
    @POST("auth/login")
    suspend fun login(@Body request: LoginRequest): Response<LoginResponse>

    @POST("auth/register")
    suspend fun register(@Body request: RegisterRequest): Response<Void>

    @GET("services")
    suspend fun getServices(): Response<List<ServicoApi>>

    @POST("appointments")
    suspend fun createAppointment(
        @Header("Authorization") token: String,
        @Body request: AppointmentRequest
    ): Response<AppointmentResponse>

    @GET("appointments/my")
    suspend fun getMyAppointments(
        @Header("Authorization") token: String
    ): Response<List<AppointmentResponse>>

    @POST("appointments/{id}/cancel")
    suspend fun cancelAppointment(
        @Header("Authorization") token: String,
        @Path("id") id: Long
    ): Response<Void>
}

object Api {
    val service: ApiService = ApiConfig.retrofit.create(ApiService::class.java)
}