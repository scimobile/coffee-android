package com.sci.coffeeandroid.feature.home.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.sci.coffeeandroid.databinding.ItemMenuBinding
import com.sci.coffeeandroid.feature.home.ui.model.MenuDrink
import com.sci.coffeeandroid.feature.home.ui.viewholder.ItemMenuVH

class MenuItemsAdapter : ListAdapter<MenuDrink, ItemMenuVH>(ItemMenuDiffUtil) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemMenuVH {
        return ItemMenuVH(
            ItemMenuBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: ItemMenuVH, position: Int) {
        holder.bind(getItem(position))
    }
}

object ItemMenuDiffUtil : DiffUtil.ItemCallback<MenuDrink>() {
    override fun areItemsTheSame(oldItem: MenuDrink, newItem: MenuDrink): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: MenuDrink, newItem: MenuDrink): Boolean {
        return oldItem == newItem
    }

}