package com.sci.coffeeandroid.feature.home.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.sci.coffeeandroid.databinding.BannerContainerBinding
import com.sci.coffeeandroid.databinding.CarouselContainerBinding
import com.sci.coffeeandroid.databinding.CategoryFiltersContainerBinding
import com.sci.coffeeandroid.databinding.GridContainerBinding
import com.sci.coffeeandroid.feature.home.ui.model.CarouselSectionData
import com.sci.coffeeandroid.feature.home.ui.model.CategoryFiltersSectionData
import com.sci.coffeeandroid.feature.home.ui.model.GridSectionData
import com.sci.coffeeandroid.feature.home.ui.model.HomeMenuData
import com.sci.coffeeandroid.feature.home.ui.model.PromotionSectionData
import com.sci.coffeeandroid.feature.home.ui.viewholder.BannerContainerVH
import com.sci.coffeeandroid.feature.home.ui.viewholder.CarouselContainerVH
import com.sci.coffeeandroid.feature.home.ui.viewholder.CategoryFiltersContainerVH
import com.sci.coffeeandroid.feature.home.ui.viewholder.GridContainerVH
import com.sci.coffeeandroid.feature.home.ui.viewholder.HomeSectionVH

const val BANNER_TYPE = 0
const val CAROUSEL_TYPE = 1
const val GRID_TYPE = 2
const val CATEGORY_FILTERS_TYPE = 3

class HomeSectionsAdapter(private val onCategoryClick: (Long) -> Unit) :
    ListAdapter<HomeMenuData, HomeSectionVH>(HomeSectionsDiffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeSectionVH {
        return when (viewType) {
            BANNER_TYPE -> BannerContainerVH(
                BannerContainerBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
            )

            CAROUSEL_TYPE -> CarouselContainerVH(
                CarouselContainerBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
            )

            GRID_TYPE -> GridContainerVH(
                GridContainerBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
            )

            CATEGORY_FILTERS_TYPE -> CategoryFiltersContainerVH(
                CategoryFiltersContainerBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                ),
                onCategoryClick
            )

            else -> BannerContainerVH(
                BannerContainerBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
            )
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is PromotionSectionData -> BANNER_TYPE
            is CarouselSectionData -> CAROUSEL_TYPE
            is GridSectionData -> GRID_TYPE
            is CategoryFiltersSectionData -> CATEGORY_FILTERS_TYPE
            else -> BANNER_TYPE
        }
    }

    override fun onBindViewHolder(holder: HomeSectionVH, position: Int) {
        holder.bind(getItem(position))
    }
}

object HomeSectionsDiffUtil : DiffUtil.ItemCallback<HomeMenuData>() {

    override fun areItemsTheSame(oldItem: HomeMenuData, newItem: HomeMenuData): Boolean {
        return if (oldItem is PromotionSectionData && newItem is PromotionSectionData) {
            oldItem.title == newItem.title
        } else if (oldItem is CarouselSectionData && newItem is CarouselSectionData) {
            oldItem.title == newItem.title
        } else if (oldItem is GridSectionData && newItem is GridSectionData) {
            oldItem.toJson() == newItem.toJson()
        } else if (oldItem is CategoryFiltersSectionData && newItem is CategoryFiltersSectionData) {
            oldItem.toJson() == newItem.toJson()
        } else {
            false
        }
    }

    override fun areContentsTheSame(oldItem: HomeMenuData, newItem: HomeMenuData): Boolean {
        return if (oldItem is PromotionSectionData && newItem is PromotionSectionData) {
            oldItem == newItem
        } else if (oldItem is CarouselSectionData && newItem is CarouselSectionData) {
            oldItem == newItem
        } else if (oldItem is GridSectionData && newItem is GridSectionData) {
            oldItem.toJson() == newItem.toJson()
        } else if (oldItem is CategoryFiltersSectionData && newItem is CategoryFiltersSectionData) {
            oldItem.toJson() == newItem.toJson()
        } else {
            false
        }
    }

}