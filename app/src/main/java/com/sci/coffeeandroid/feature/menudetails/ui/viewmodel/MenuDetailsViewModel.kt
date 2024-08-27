package com.sci.coffeeandroid.feature.menudetails.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sci.coffeeandroid.feature.menudetails.data.repository.CoffeeDetailsRepository
import com.sci.coffeeandroid.feature.menudetails.domain.model.CoffeeModel
import com.sci.coffeeandroid.feature.menudetails.domain.model.CustomOrderModel
import com.sci.coffeeandroid.feature.menudetails.domain.model.Size
import com.sci.coffeeandroid.feature.menudetails.domain.model.Sugar
import com.sci.coffeeandroid.feature.menudetails.domain.model.Variation
import kotlinx.coroutines.launch

class MenuDetailsViewModel(
    private val coffeeDetailsRepository: CoffeeDetailsRepository,
    coffeeId: Int
) : ViewModel() {

    private var _menuDetailsUiState: MutableLiveData<CoffeeDetailsUiState> = MutableLiveData()
    val menuDetailsUiState: LiveData<CoffeeDetailsUiState> = _menuDetailsUiState



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

    val customOrderLiveData: LiveData<CustomOrderModel> = _customOrderLiveData

    var placeHolderQuantity = 0
    var selectedMilkPosition = 0
    var selectedToppingPosition = 0
    var isFavourite = false

    init {
        viewModelScope.launch {
            fetchCoffeeDetail(id = coffeeId)
        }
    }

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

    fun onMilkSelected(milk: Int) {
        selectedMilkPosition = milk
        _customOrderLiveData.value = customOrderLiveData.value?.copy(
            milk = (menuDetailsUiState.value as CoffeeDetailsUiState.Success).data.milk[milk]
        )
    }


    fun onToppingSelected(topping: Int) {
        selectedToppingPosition = topping
        _customOrderLiveData.value = customOrderLiveData.value?.copy(
            topping = (menuDetailsUiState.value as CoffeeDetailsUiState.Success).data.toppings[topping]
        )
    }

    fun onFavouriteClicked() {
        isFavourite = !isFavourite
        _menuDetailsUiState.value = CoffeeDetailsUiState.Success(
            (menuDetailsUiState.value as CoffeeDetailsUiState.Success).data.copy(
                isFavourite = !(menuDetailsUiState.value as CoffeeDetailsUiState.Success).data.isFavourite,
            )
        )
    }

    fun onSpecialInstructionsAdded(instruction: String) {
        _customOrderLiveData.value = customOrderLiveData.value?.copy(
            specialInstructions = instruction
        )
    }

    fun decreaseQuantity() {
        placeHolderQuantity = maxOf(0, placeHolderQuantity - 1)
        _customOrderLiveData.value = customOrderLiveData.value?.copy(
            quantity = placeHolderQuantity
        )
    }

    fun increaseQuantity() {
        placeHolderQuantity = minOf(
            (menuDetailsUiState.value as CoffeeDetailsUiState.Success)
                .data.availableQuantity, placeHolderQuantity + 1
        )
        _customOrderLiveData.value = customOrderLiveData.value?.copy(
            quantity = placeHolderQuantity
        )
    }

    private fun fetchCoffeeDetail(id: Int) {
        if (id == 0) {
            _menuDetailsUiState.value = CoffeeDetailsUiState.Error("Something went wrong")
            return
        }
        _menuDetailsUiState.value = CoffeeDetailsUiState.Loading
        viewModelScope.launch {
            val coffeeDetailsModel: Result<CoffeeModel> =
                coffeeDetailsRepository.getCoffeeDetail(id = id)
            coffeeDetailsModel.fold(
                {
                    _menuDetailsUiState.value = CoffeeDetailsUiState.Success(it)
                    isFavourite = it.isFavourite
                },
                {
                    _menuDetailsUiState.value =
                        CoffeeDetailsUiState.Error(it.message ?: "something went wrong")
                }
            )
        }
    }

    fun addToCart() {
        viewModelScope.launch {
            coffeeDetailsRepository.addToCart(customOrderModel = customOrderLiveData.value!!)
        }
    }

    sealed class CoffeeDetailsUiState {
        data object Loading : CoffeeDetailsUiState()
        data class Success(val data: CoffeeModel) : CoffeeDetailsUiState()
        data class Error(val message: String) : CoffeeDetailsUiState()
    }
}