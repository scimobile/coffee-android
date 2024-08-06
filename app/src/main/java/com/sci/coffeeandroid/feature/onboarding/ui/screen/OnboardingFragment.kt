package com.sci.coffeeandroid.feature.onboarding.ui.screen

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager2.widget.ViewPager2
import com.sci.coffeeandroid.R
import com.sci.coffeeandroid.databinding.FragmentOnboardingBinding
import com.sci.coffeeandroid.feature.onboarding.ui.adapter.OnboardingPagerAdapter


class OnboardingFragment : Fragment(R.layout.fragment_onboarding) {

    private var _binding: FragmentOnboardingBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentOnboardingBinding.bind(view)

        val adapter = OnboardingPagerAdapter(
            requireActivity()
        )
        var index = 0

        binding.viewPager.adapter = adapter
        binding.dotIndicator.attachTo(viewPager2 = binding.viewPager)

        binding.viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {

            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                toggleBtnVisibility(position=position)
                index = position
            }
        })

        binding.btnNext.setOnClickListener {
            binding.viewPager.setCurrentItem(index + 1)
        }
    }

    private fun toggleBtnVisibility(position: Int){
        when(position){
            0,1 -> {
                binding.btnNext.visibility = View.VISIBLE
                binding.btnGetStarted.visibility = View.GONE
            }
            2 -> {
                binding.btnNext.visibility = View.GONE
                binding.btnGetStarted.visibility = View.VISIBLE
            }
        }
    }
}