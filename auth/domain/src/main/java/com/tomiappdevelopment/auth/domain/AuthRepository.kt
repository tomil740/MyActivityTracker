package com.tomiappdevelopment.auth.domain

import com.tomiappdevelopment.core.domain.util.DataError
import com.tomiappdevelopment.core.domain.util.EmptyResult


interface AuthRepository {
    suspend fun register(email: String, password: String): EmptyResult<DataError.Network>
    suspend fun login(email: String, password: String): EmptyResult<DataError.Network>

}