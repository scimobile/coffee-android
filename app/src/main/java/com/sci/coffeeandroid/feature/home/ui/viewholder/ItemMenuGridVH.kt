package com.sci.coffeeandroid.feature.home.ui.viewholder

import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.sci.coffeeandroid.databinding.ItemMenuGridBinding
import com.sci.coffeeandroid.feature.home.ui.model.MenuDrink

class ItemMenuGridVH(private val binding: ItemMenuGridBinding): ViewHolder(binding.root) {

    fun bind(data: MenuDrink) {
        binding.tvMenuName.text = data.name
        binding.tvMenuPrice.text = data.price
        Glide.with(binding.root).load(data.imageUrl).into(binding.ivMenuImage)
    }

}