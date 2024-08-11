package com.sci.coffeeandroid.feature.auth.data.datasource

class AuthLocalDataSourceImpl(
) : AuthLocalDataSource {

    private val tokenService: TokenService = TokenService
    override fun isUserLoggedIn() = !tokenService.token.isNullOrBlank()
    override fun saveAccessToken(token: String) {
        tokenService.token = token
    }

    override fun getAccessToken(): String? = tokenService.token

    override fun removeAccessToken() {
        tokenService.token = null
    }
}