package com.sci.coffeeandroid.feature.home.ui.viewholder

import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.sci.coffeeandroid.databinding.ItemPromotionBinding
import com.sci.coffeeandroid.feature.home.ui.model.Promotion

class ItemPromotionVH(private val binding: ItemPromotionBinding) : ViewHolder(binding.root) {

    fun bind(data: Promotion) {
        binding.tvBannerItemTitle.text = data.title
        binding.tvBannerItemDescription.text = data.description
        Glide.with(binding.root).load(data.imageUrl).into(binding.ivBannerItemImage)
    }

}