package com.tomiappdevelopment.auth.domain

interface PatternValidator {
    fun matches(value: String): Boolean
}