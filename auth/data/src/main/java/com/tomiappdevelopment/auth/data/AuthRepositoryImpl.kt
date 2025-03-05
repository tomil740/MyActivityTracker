package com.tomiappdevelopment.auth.data


import android.util.Log
import com.tomiappdevelopment.auth.domain.AuthRepository
import com.tomiappdevelopment.core.data.networking.post
import com.tomiappdevelopment.core.domain.AuthInfo
import com.tomiappdevelopment.core.domain.SessionStorage
import com.tomiappdevelopment.core.domain.util.DataError
import com.tomiappdevelopment.core.domain.util.EmptyResult
import com.tomiappdevelopment.core.domain.util.Result
import com.tomiappdevelopment.core.domain.util.asEmptyDataResult
import io.ktor.client.HttpClient

class AuthRepositoryImpl(
    private val httpClient: HttpClient,
    private val sessionStorage: SessionStorage
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

    override suspend fun login(email: String, password: String): EmptyResult<DataError.Network> {
        val result = httpClient.post<LoginRequestDto, LoginResponseDto>(
            route = "/login",
            body = LoginRequestDto(
                email = email,
                password = password
            )
        )
        Log.i("the result mai,",result.toString())
        if(result is Result.Success) {
            sessionStorage.set(
                AuthInfo(
                    accessToken = result.data.accessToken,
                    refreshToken = result.data.refreshToken,
                    userId = result.data.userId
                )
            )
        }
        return result.asEmptyDataResult()
    }
}