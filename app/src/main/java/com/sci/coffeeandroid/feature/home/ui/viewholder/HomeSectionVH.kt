package com.sci.coffeeandroid.feature.home.ui.viewholder

import android.view.View
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.sci.coffeeandroid.feature.home.ui.model.HomeMenuData

abstract class HomeSectionVH(item: View) : ViewHolder(item) {
    abstract fun bind(data: HomeMenuData)
}