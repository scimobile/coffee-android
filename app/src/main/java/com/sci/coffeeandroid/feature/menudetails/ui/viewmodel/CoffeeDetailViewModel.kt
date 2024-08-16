package com.sci.coffeeandroid.feature.menudetails.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sci.coffeeandroid.feature.menudetails.data.repository.CoffeeDetailRepository
import com.sci.coffeeandroid.feature.menudetails.domain.model.CoffeeModel
import com.sci.coffeeandroid.feature.menudetails.domain.model.CustomOrderModel
import kotlinx.coroutines.launch

class CoffeeDetailViewModel constructor(
    private val coffeeDetailRepository: CoffeeDetailRepository
) : ViewModel() {
    private val _coffeeDetailLiveData: MutableLiveData<CoffeeDetailUiState> = MutableLiveData()
    val coffeeDetailLiveData: LiveData<CoffeeDetailUiState> = _coffeeDetailLiveData

    private var _quantityLiveData: MutableLiveData<Int> = MutableLiveData(0)
    val quantityLiveData: LiveData<Int> = _quantityLiveData

    private var _customOrderLiveData: MutableLiveData<CustomOrderModel> =
        MutableLiveData(
            CustomOrderModel(
                size = "",
                variation = "",
                sugar = "",
                milk = "",
                topping = "",
                specialInstructions = ""
            )
        )
    private val customOrderLiveData: LiveData<CustomOrderModel> = _customOrderLiveData

    fun onSizeSelected(size: String) {
        _customOrderLiveData.value = customOrderLiveData.value?.copy(
            size = size
        )
    }

    fun onVariationSelected(variation: String) {
        _customOrderLiveData.value = customOrderLiveData.value?.copy(
            variation = variation
        )
    }

    fun onSugarSelected(sugar: String) {
        _customOrderLiveData.value = customOrderLiveData.value?.copy(
            sugar = sugar
        )
    }

    fun onMilkSelected(milk: String) {
        _customOrderLiveData.value = customOrderLiveData.value?.copy(
            milk = milk
        )
    }

    fun onToppingSelected(topping: String) {
        _customOrderLiveData.value = customOrderLiveData.value?.copy(
            topping = topping
        )
    }

    fun onSpecialInstructionsAdded(instruction: String) {
        _customOrderLiveData.value = customOrderLiveData.value?.copy(
            specialInstructions = instruction
        )
    }

    fun decreaseQuantity() {
        _quantityLiveData.value = maxOf(0, (quantityLiveData.value!! - 1).toInt())
    }

    fun increaseQuantity() {
        _quantityLiveData.value = minOf(1000, (quantityLiveData.value!! + 1).toInt())
    }

    fun fetchCoffeeDetail(id: Int) {
        _coffeeDetailLiveData.value = CoffeeDetailUiState.Loading
        viewModelScope.launch {
            val coffeeDetailModels: Result<CoffeeModel> =
                coffeeDetailRepository.getCoffeeDetail(id = id)
            coffeeDetailModels.fold(
                {
                    _coffeeDetailLiveData.value = CoffeeDetailUiState.Success(it)
                },
                {
                    _coffeeDetailLiveData.value =
                        CoffeeDetailUiState.Error(it.message ?: "something went wrong")
                }
            )
        }
    }


    sealed class CoffeeDetailUiState {
        data object Loading : CoffeeDetailUiState()
        data class Success(val coffee: CoffeeModel) : CoffeeDetailUiState()
        data class Error(val message: String) : CoffeeDetailUiState()
    }
}