package com.sci.coffeeandroid.feature.home.ui.viewholder

import androidx.recyclerview.widget.PagerSnapHelper
import com.sci.coffeeandroid.databinding.BannerContainerBinding
import com.sci.coffeeandroid.feature.home.ui.adapter.PromotionsAdapter
import com.sci.coffeeandroid.feature.home.ui.model.HomeMenuData
import com.sci.coffeeandroid.feature.home.ui.model.PromotionSectionData

class BannerContainerVH(private val binding: BannerContainerBinding) : HomeSectionVH(binding.root) {

    private val adapter = PromotionsAdapter()
    private val snapHelper = PagerSnapHelper()

    override fun bind(data: HomeMenuData) {
        if (data is PromotionSectionData) {
            binding.tvPromotionTitle.text = data.title
            binding.rvBanner.adapter = adapter
            snapHelper.attachToRecyclerView(binding.rvBanner)
            adapter.submitList(data.promotions)
        }
    }

}