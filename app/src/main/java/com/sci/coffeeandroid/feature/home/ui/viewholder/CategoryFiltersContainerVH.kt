package com.sci.coffeeandroid.feature.home.ui.viewholder

import com.sci.coffeeandroid.databinding.CategoryFiltersContainerBinding
import com.sci.coffeeandroid.feature.home.ui.adapter.MenuCategoriesAdapter
import com.sci.coffeeandroid.feature.home.ui.model.CategoryFiltersSectionData
import com.sci.coffeeandroid.feature.home.ui.model.HomeMenuData

class CategoryFiltersContainerVH(
    private val binding: CategoryFiltersContainerBinding,
    onClick: (Long) -> Unit
) : HomeSectionVH(binding.root) {

    private val categoriesAdapter = MenuCategoriesAdapter(onClick)

    override fun bind(data: HomeMenuData) {
        if (data is CategoryFiltersSectionData) {
            binding.rvMenuCategories.adapter = categoriesAdapter
            categoriesAdapter.submitList(data.categories)
        }
    }

}