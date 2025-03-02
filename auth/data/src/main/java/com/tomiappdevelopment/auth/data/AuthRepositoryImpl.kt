package com.tomiappdevelopment.auth.data


import com.tomiappdevelopment.auth.domain.AuthRepository
import com.tomiappdevelopment.core.data.networking.post
import com.tomiappdevelopment.core.domain.util.DataError
import com.tomiappdevelopment.core.domain.util.EmptyResult
import io.ktor.client.HttpClient

class AuthRepositoryImpl(
    private val httpClient: HttpClient
): AuthRepository {

    override suspend fun register(email: String, password: String): EmptyResult<DataError.Network> {
        return httpClient.post<RegisterRequest, Unit>(
            route = "/register",
            body = RegisterRequest(
                email = email,
                password = password
            )
        )
    }
}