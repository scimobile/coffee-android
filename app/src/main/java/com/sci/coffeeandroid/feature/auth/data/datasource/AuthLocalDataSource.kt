package com.sci.coffeeandroid.feature.auth.data.datasource
import androidx.lifecycle.LiveData
import com.sci.coffeeandroid.feature.auth.domain.model.UserModel

interface AuthLocalDataSource {
    fun isUserLoggedIn(): Boolean
    fun saveAccessToken(token: String)
    fun getAccessToken(): String?
    fun removeAccessToken()
}