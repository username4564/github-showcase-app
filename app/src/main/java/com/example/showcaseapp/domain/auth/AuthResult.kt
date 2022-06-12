package com.example.showcaseapp.domain.auth

sealed interface AuthResult
class AuthResultError(val message: String) : AuthResult
class AuthResultSuccess(val code: String) : AuthResult
object AuthResultUnexpectedUrl : AuthResult