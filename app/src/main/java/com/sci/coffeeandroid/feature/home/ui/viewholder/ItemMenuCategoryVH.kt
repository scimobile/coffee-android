package com.sci.coffeeandroid.feature.home.ui.viewholder

import android.graphics.Typeface
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.sci.coffeeandroid.R
import com.sci.coffeeandroid.databinding.ItemMenuCategoryBinding
import com.sci.coffeeandroid.feature.home.ui.model.MenuCategory

class ItemMenuCategoryVH(
    private val binding: ItemMenuCategoryBinding,
    private val onClick: (Long) -> Unit
) : ViewHolder(binding.root) {

    fun bind(data: MenuCategory) {
        Glide.with(binding.root.context).load(data.imageUrl).into(binding.ivCategoryImage)
        binding.tvCategoryName.text = data.name

        binding.mcvCategoryContainer.setCardBackgroundColor(
            if (data.isSelected) {
                Typeface.defaultFromStyle(Typeface.BOLD).also {
                    binding.tvCategoryName.setTypeface(it)
                }
                binding.root.context.getColor(R.color.color_onboarding_text)
            } else {
                Typeface.defaultFromStyle(Typeface.NORMAL).also {
                    binding.tvCategoryName.setTypeface(it)
                }
                binding.root.context.getColor(R.color.color_divider)
            }
        )

        binding.root.setOnClickListener {
            onClick(data.id)
        }
    }

}