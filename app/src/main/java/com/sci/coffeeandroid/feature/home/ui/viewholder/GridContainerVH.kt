package com.sci.coffeeandroid.feature.home.ui.viewholder

import com.sci.coffeeandroid.databinding.GridContainerBinding
import com.sci.coffeeandroid.feature.home.ui.adapter.GridMenuItemsAdapter
import com.sci.coffeeandroid.feature.home.ui.model.GridSectionData
import com.sci.coffeeandroid.feature.home.ui.model.HomeMenuData
import com.sci.coffeeandroid.feature.home.ui.util.ItemOffsetDecoration

class GridContainerVH(private val binding: GridContainerBinding) : HomeSectionVH(binding.root) {

    private val menuItemsAdapter = GridMenuItemsAdapter()

    override fun bind(data: HomeMenuData) {
        if (data is GridSectionData) {
            binding.rvMenuItems.adapter = menuItemsAdapter
            menuItemsAdapter.submitList(data.menuItems)
            val itemDecoration = ItemOffsetDecoration(16)
            binding.rvMenuItems.addItemDecoration(itemDecoration)
        }

    }

}