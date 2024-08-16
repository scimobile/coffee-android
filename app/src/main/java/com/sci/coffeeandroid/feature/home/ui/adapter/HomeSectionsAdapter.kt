package com.sci.coffeeandroid.feature.home.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.sci.coffeeandroid.databinding.BannerContainerBinding
import com.sci.coffeeandroid.databinding.CarouselContainerBinding
import com.sci.coffeeandroid.databinding.GridContainerBinding
import com.sci.coffeeandroid.feature.home.ui.model.CarouselSectionData
import com.sci.coffeeandroid.feature.home.ui.model.GridSectionData
import com.sci.coffeeandroid.feature.home.ui.model.HomeMenuData
import com.sci.coffeeandroid.feature.home.ui.model.PromotionSectionData
import com.sci.coffeeandroid.feature.home.ui.viewholder.BannerContainerVH
import com.sci.coffeeandroid.feature.home.ui.viewholder.CarouselContainerVH
import com.sci.coffeeandroid.feature.home.ui.viewholder.GridContainerVH
import com.sci.coffeeandroid.feature.home.ui.viewholder.HomeSectionVH

const val BANNER_TYPE = 0
const val CAROUSEL_TYPE = 1
const val GRID_TYPE = 2

class HomeSectionsAdapter(private val onCategoryClick: (Long) -> Unit) : Adapter<HomeSectionVH>() {

    private var homeSections: List<HomeMenuData> = emptyList()

    fun updateList(dataList: List<HomeMenuData>) {
        homeSections = dataList
        notifyDataSetChanged()
    }

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

    override fun getItemCount() = homeSections.size

    override fun getItemViewType(position: Int): Int {
        return when (homeSections[position]) {
            is PromotionSectionData -> BANNER_TYPE
            is CarouselSectionData -> CAROUSEL_TYPE
            is GridSectionData -> GRID_TYPE
            else -> BANNER_TYPE
        }
    }

    override fun onBindViewHolder(holder: HomeSectionVH, position: Int) {
        holder.bind(homeSections[position])
    }
}