package com.tomiappdevelopment.core.domain


data class AuthInfo(
    val accessToken: String,
    val refreshToken: String,
    val userId: String
)