package com.sci.coffeeandroid.feature.menudetails.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sci.coffeeandroid.feature.menudetails.data.repository.CoffeeDetailRepository
import com.sci.coffeeandroid.feature.menudetails.domain.model.CoffeeModel
import com.sci.coffeeandroid.feature.menudetails.domain.model.CustomOrderModel
import com.sci.coffeeandroid.feature.menudetails.domain.model.Size
import com.sci.coffeeandroid.feature.menudetails.domain.model.Sugar
import com.sci.coffeeandroid.feature.menudetails.domain.model.Variation
import kotlinx.coroutines.launch

class CoffeeDetailViewModel(
    private val coffeeDetailRepository: CoffeeDetailRepository,
    coffeeId: Int
) : ViewModel() {

    private var _coffeeDetailLiveData: MutableLiveData<CoffeeDetailUiState> = MutableLiveData()
    val coffeeDetailLiveData: LiveData<CoffeeDetailUiState> = _coffeeDetailLiveData

    private var _quantityLiveData: MutableLiveData<Int> = MutableLiveData(0)
    val quantityLiveData: LiveData<Int> = _quantityLiveData

    private var _customOrderLiveData: MutableLiveData<CustomOrderModel> =
        MutableLiveData(
            CustomOrderModel(
                size = Size.SMALL,
                variation = Variation.HOT,
                sugar = Sugar.NONE,
                milk = "",
                topping = "",
                specialInstructions = "",
                quantity = 0
            )
        )

    init {
        fetchCoffeeDetail(id = coffeeId)
    }

    fun updateQuantity(quantity: Int) {
        _customOrderLiveData.value = customOrderLiveData.value?.copy(
            quantity = quantity
        )
    }

    val customOrderLiveData: LiveData<CustomOrderModel> = _customOrderLiveData

    fun onSizeSelected(size: Size) {
        _customOrderLiveData.value = customOrderLiveData.value?.copy(
            size = size
        )
    }

    fun onVariationSelected(variation: Variation) {
        _customOrderLiveData.value = customOrderLiveData.value?.copy(
            variation = variation
        )
    }

    fun onSugarSelected(sugar: Sugar) {
        _customOrderLiveData.value = customOrderLiveData.value?.copy(
            sugar = sugar
        )
    }

    var selectedMilkPosition = 0

    fun onMilkSelected(milk: Int) {
        selectedMilkPosition = milk
        _customOrderLiveData.value = customOrderLiveData.value?.copy(
            milk = (coffeeDetailLiveData.value as CoffeeDetailUiState.Success).coffee.milk[milk]
        )
    }

    var selectedToppingPosition = 0

    fun onToppingSelected(topping: Int) {
        selectedToppingPosition = topping
        _customOrderLiveData.value = customOrderLiveData.value?.copy(
            topping = (coffeeDetailLiveData.value as CoffeeDetailUiState.Success).coffee.toppings[topping]
        )
    }

    var isFavourite = false

    fun onFavouriteClicked() {
        isFavourite = !isFavourite
        _coffeeDetailLiveData.value = CoffeeDetailUiState.Success(
            (coffeeDetailLiveData.value as CoffeeDetailUiState.Success).coffee.copy(
                isFavourite = !(coffeeDetailLiveData.value as CoffeeDetailUiState.Success).coffee.isFavourite
            )
        )
    }

    fun onSpecialInstructionsAdded(instruction: String) {
        _customOrderLiveData.value = customOrderLiveData.value?.copy(
            specialInstructions = instruction
        )
    }

    fun decreaseQuantity() {
        _quantityLiveData.value = maxOf(0, (customOrderLiveData.value?.quantity!! - 1).toInt())
    }

    fun increaseQuantity() {
        _quantityLiveData.value = minOf(1000, (customOrderLiveData.value?.quantity!! + 1).toInt())
    }

    private fun fetchCoffeeDetail(id: Int) {
        if (id == 0) {
            _coffeeDetailLiveData.value = CoffeeDetailUiState.Error("Something went wrong")
            return
        }
        _coffeeDetailLiveData.value = CoffeeDetailUiState.Loading
        viewModelScope.launch {
            val coffeeDetailModels: Result<CoffeeModel> =
                coffeeDetailRepository.getCoffeeDetail(id = id)
            coffeeDetailModels.fold(
                {
                    _coffeeDetailLiveData.value = CoffeeDetailUiState.Success(it)
                    isFavourite = it.isFavourite
                },
                {
                    _coffeeDetailLiveData.value =
                        CoffeeDetailUiState.Error(it.message ?: "something went wrong")
                }
            )
        }
    }

    fun addToCart() {
        viewModelScope.launch {
            coffeeDetailRepository.addToCart(customOrderModel = customOrderLiveData.value!!)
        }
    }

    sealed class CoffeeDetailUiState {
        data object Loading : CoffeeDetailUiState()
        data class Success(val coffee: CoffeeModel) : CoffeeDetailUiState()
        data class Error(val message: String) : CoffeeDetailUiState()
    }
}