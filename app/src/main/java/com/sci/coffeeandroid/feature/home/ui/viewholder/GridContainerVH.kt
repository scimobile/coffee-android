package com.sci.coffeeandroid.feature.home.ui.viewholder

import com.sci.coffeeandroid.databinding.GridContainerBinding
import com.sci.coffeeandroid.feature.home.ui.adapter.GridMenuItemsAdapter
import com.sci.coffeeandroid.feature.home.ui.adapter.MenuCategoriesAdapter
import com.sci.coffeeandroid.feature.home.ui.model.GridSectionData
import com.sci.coffeeandroid.feature.home.ui.model.HomeMenuData

class GridContainerVH(
    private val binding: GridContainerBinding,
    onClick: (Long) -> Unit
) : HomeSectionVH(binding.root) {

    private val categoriesAdapter = MenuCategoriesAdapter(onClick)
    private val menuItemsAdapter = GridMenuItemsAdapter()

    override fun bind(data: HomeMenuData) {
        if (data is GridSectionData) {
            binding.rvMenuCategories.adapter = categoriesAdapter
            categoriesAdapter.submitList(data.categories)

            binding.rvMenuItems.adapter = menuItemsAdapter
            menuItemsAdapter.submitList(data.menuItems)
        }

    }

}