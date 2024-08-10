package com.sci.coffeeandroid.feature.onboarding.ui.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.sci.coffeeandroid.feature.onboarding.ui.screen.FirstSlideFragment
import com.sci.coffeeandroid.feature.onboarding.ui.screen.SecondSlideFragment
import com.sci.coffeeandroid.feature.onboarding.ui.screen.ThirdSlideFragment

class OnboardingPagerAdapter(fa: FragmentActivity) : FragmentStateAdapter(fa) {
    override fun getItemCount(): Int {
        return 3
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> FirstSlideFragment()
            1 -> SecondSlideFragment()
            2 -> ThirdSlideFragment()
            else -> FirstSlideFragment()
        }
    }
}