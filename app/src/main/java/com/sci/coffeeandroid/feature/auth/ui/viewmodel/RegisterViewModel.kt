package com.sci.coffeeandroid.feature.auth.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sci.coffeeandroid.feature.auth.data.repository.RegisterRepository
import com.sci.coffeeandroid.feature.auth.domain.model.UserModel

class RegisterViewModel(
    private val registerRepository: RegisterRepository
) : ViewModel() {

    private val _registerLiveData : MutableLiveData<UserModel> = MutableLiveData()
    val registerLiveData : MutableLiveData<UserModel> = _registerLiveData

//    private val _uiState : MutableLiveData<UiState> = MutableLiveData()

    suspend fun register(
        username: String,
        email: String,
        phone: String,
        password: String
    ) {
        registerRepository.register(username, email, phone, password)

    }

}