package com.sci.coffeeandroid.feature.home.ui.viewholder

import com.sci.coffeeandroid.databinding.CarouselContainerBinding
import com.sci.coffeeandroid.feature.home.ui.adapter.MenuItemsAdapter
import com.sci.coffeeandroid.feature.home.ui.model.CarouselSectionData
import com.sci.coffeeandroid.feature.home.ui.model.HomeMenuData

class CarouselContainerVH(private val binding: CarouselContainerBinding) :
    HomeSectionVH(binding.root) {

    private val adapter = MenuItemsAdapter()

    override fun bind(data: HomeMenuData) {
        if (data is CarouselSectionData) {
            binding.tvPromotionTitle.text = data.title
            binding.rvMenuItems.adapter = adapter
            adapter.submitList(data.menuItems)
        }

    }

}