package com.sci.coffeeandroid.feature.home.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.sci.coffeeandroid.databinding.ItemMenuGridBinding
import com.sci.coffeeandroid.feature.home.ui.model.MenuDrink
import com.sci.coffeeandroid.feature.home.ui.viewholder.ItemMenuGridVH

class GridMenuItemsAdapter : ListAdapter<MenuDrink, ItemMenuGridVH>(ItemMenuDiffUtil) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemMenuGridVH {
        return ItemMenuGridVH(
            ItemMenuGridBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: ItemMenuGridVH, position: Int) {
        holder.bind(getItem(position))
    }
}