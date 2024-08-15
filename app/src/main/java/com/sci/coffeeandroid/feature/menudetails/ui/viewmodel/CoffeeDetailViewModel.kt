package com.sci.coffeeandroid.feature.menudetails.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sci.coffeeandroid.feature.menudetails.data.repository.CoffeeDetailRepository
import com.sci.coffeeandroid.feature.menudetails.domain.model.CoffeeModel
import kotlinx.coroutines.launch

class CoffeeDetailViewModel constructor(
    private val coffeeDetailRepository: CoffeeDetailRepository
) : ViewModel() {
    private val _coffeeDetailLiveData: MutableLiveData<CoffeeDetailUiState> = MutableLiveData()
    val coffeeDetailLiveData: LiveData<CoffeeDetailUiState> = _coffeeDetailLiveData

    fun fetchCoffeeDetail(id: Int) {
        _coffeeDetailLiveData.value = CoffeeDetailUiState.Loading
        viewModelScope.launch {
            val coffeeDetailModels: Result<CoffeeModel> = coffeeDetailRepository.getCoffeeDetail(id = id)
            coffeeDetailModels.fold(
                {
                    _coffeeDetailLiveData.value = CoffeeDetailUiState.Success(it)
                },
                {
                    _coffeeDetailLiveData.value = CoffeeDetailUiState.Error(it.message ?: "something went wrong")
                }
            )
        }
    }

    sealed class CoffeeDetailUiState{
        data object Loading : CoffeeDetailUiState()
        data class Success(val coffee: CoffeeModel) :CoffeeDetailUiState()
        data class Error(val message: String): CoffeeDetailUiState()
    }
}