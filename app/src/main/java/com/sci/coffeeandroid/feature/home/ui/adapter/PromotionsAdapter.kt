package com.sci.coffeeandroid.feature.home.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.sci.coffeeandroid.databinding.ItemPromotionBinding
import com.sci.coffeeandroid.feature.home.ui.model.Promotion
import com.sci.coffeeandroid.feature.home.ui.viewholder.ItemPromotionVH

class PromotionsAdapter : ListAdapter<Promotion, ItemPromotionVH>(PromotionDiffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemPromotionVH {
        return ItemPromotionVH(
            ItemPromotionBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: ItemPromotionVH, position: Int) {
        holder.bind(getItem(position))
    }

}

object PromotionDiffUtil : DiffUtil.ItemCallback<Promotion>() {

    override fun areItemsTheSame(oldItem: Promotion, newItem: Promotion): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Promotion, newItem: Promotion): Boolean {
        return oldItem.id == newItem.id
    }

}