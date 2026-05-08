package com.beautyhub.app

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

// Modelos de requisição
data class LoginRequest(
    val email: String,
    val password: String
)

data class RegisterRequest(
    val fullName: String,
    val email: String,
    val phone: String,
    val password: String
)

// Modelos de resposta
data class LoginResponse(
    val token: String
)

// Modelo do salao dentro do serviço
data class SalaoApi(
    val id: Int,
    val nome: String,
    val descricao: String?,
    val endereco: String?,
    val telefone: String?,
    val mediaAvaliacao: Double?
)

// Endpoint de Serviços
data class ServicoApi(
    val id: Int,
    val nome: String,
    val descricao: String?,
    val duracaoMinutos: Int,
    val preco: Double,
    val salao: SalaoApi?
)

// Endpoint de agendamento
data class AppointmentRequest(
    val serviceId: Int,
    val dataHoraInicio: String
)

data class AppointmentResponse(
    val id: Int,
    val status: String,
    val dataHoraInicio: String
)

// Interface da API
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
}

// Instância global do serviço
object Api {
    val service: ApiService = ApiConfig.retrofit.create(ApiService::class.java)
}