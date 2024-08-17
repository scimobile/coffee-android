package com.sci.coffeeandroid.feature.home.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sci.coffeeandroid.feature.home.data.repository.HomeMenuRepository
import com.sci.coffeeandroid.feature.home.ui.model.GridSectionData
import com.sci.coffeeandroid.feature.home.ui.model.HomeMenuData

sealed class HomeMenuUIState {
    data object Loading : HomeMenuUIState()
    data class Success(val data: List<HomeMenuData>) : HomeMenuUIState()
    data class Error(val errorMessage: String) : HomeMenuUIState()
}

class HomeViewModel(private val repository: HomeMenuRepository) : ViewModel() {

    private val _homeMenuSectionsLive: MutableLiveData<HomeMenuUIState> = MutableLiveData()
    val homeMenuSectionsLive: LiveData<HomeMenuUIState> = _homeMenuSectionsLive

    private fun getHomeMenuSections() {
        _homeMenuSectionsLive.value = HomeMenuUIState.Loading
        repository.getHomeMenuData().fold(
            {
                _homeMenuSectionsLive.value = HomeMenuUIState.Success(it)
            },
            {
                _homeMenuSectionsLive.value =
                    HomeMenuUIState.Error(it.message ?: "Something went wrong!")
            }
        )
    }

    private val _cartQtyLive: MutableLiveData<Int> = MutableLiveData(0)
    val cartQtyLive: LiveData<Int> = _cartQtyLive

    fun onCategorySelected(id: Long) {
        if (_homeMenuSectionsLive.value is HomeMenuUIState.Success) {
            val homeSections = (_homeMenuSectionsLive.value as HomeMenuUIState.Success).data
            _homeMenuSectionsLive.value = HomeMenuUIState.Success(
                homeSections.map { section ->
                    if (section is GridSectionData) {
                        section.copy(
                            categories = section.categories.map { category ->
                                category.copy(isSelected = category.id == id)
                            }
                        )
                    } else {
                        section
                    }
                }
            )
        }
    }

    init {
        getHomeMenuSections()
    }

}