package com.sci.coffeeandroid.feature.home.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.sci.coffeeandroid.databinding.ItemMenuCategoryBinding
import com.sci.coffeeandroid.feature.home.ui.model.MenuCategory
import com.sci.coffeeandroid.feature.home.ui.viewholder.ItemMenuCategoryVH

class MenuCategoriesAdapter(private val onClick: (Long) -> Unit) :
    ListAdapter<MenuCategory, ItemMenuCategoryVH>(MenuCategoryDiffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemMenuCategoryVH {
        return ItemMenuCategoryVH(
            ItemMenuCategoryBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            ),
            onClick
        )
    }

    override fun onBindViewHolder(holder: ItemMenuCategoryVH, position: Int) {
        holder.bind(getItem(position))
    }
}

object MenuCategoryDiffUtil : DiffUtil.ItemCallback<MenuCategory>() {
    override fun areItemsTheSame(oldItem: MenuCategory, newItem: MenuCategory): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: MenuCategory, newItem: MenuCategory): Boolean {
        return oldItem == newItem
    }

}