package com.beautyhub.app

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit // Importar TimeUnit

object ApiConfig {
    private const val BASE_URL = "https://beautyhub-production.up.railway.app/"

    // 1. Criar um interceptor para adicionar o token de autenticação
    private val authInterceptor = Interceptor { chain ->
        val originalRequest = chain.request()
        val token = SessionManager.getToken() // Pega o token do SessionManager

        val requestBuilder = originalRequest.newBuilder()

        token?.let {
            // Se houver token, adiciona o cabeçalho Authorization
            requestBuilder.header("Authorization", "Bearer $it")
        }

        val request = requestBuilder.build()
        chain.proceed(request)
    }

    // 2. Criar um interceptor para logar as requisições (útil para debug)
    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY // Loga cabeçalhos e corpo da requisição/resposta
    }

    // 3. Configurar o OkHttpClient com os interceptors
    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(authInterceptor) // Adiciona o interceptor de autenticação
        .addInterceptor(loggingInterceptor) // Adiciona o interceptor de logging
        .connectTimeout(30, TimeUnit.SECONDS) // Tempo limite de conexão
        .readTimeout(30, TimeUnit.SECONDS)    // Tempo limite de leitura
        .writeTimeout(30, TimeUnit.SECONDS)   // Tempo limite de escrita
        .build()

    // 4. Configurar o Retrofit para usar o OkHttpClient personalizado
    val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(okHttpClient) // Define o OkHttpClient personalizado
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}