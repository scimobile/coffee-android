package com.sci.coffeeandroid.feature.onboarding.ui.screen

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import androidx.core.content.edit
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.sci.coffeeandroid.R
import com.sci.coffeeandroid.databinding.FragmentOnboardingBinding
import com.sci.coffeeandroid.feature.onboarding.ui.adapter.OnboardingPagerAdapter
import org.koin.android.ext.android.inject


class OnboardingFragment : Fragment(R.layout.fragment_onboarding) {

    private var _binding: FragmentOnboardingBinding? = null
    private val binding get() = _binding!!

    private val sharePref: SharedPreferences by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentOnboardingBinding.bind(view)

        binding.root
            .setOnApplyWindowInsetsListener { v, insets ->
                val windowInsetsCompat = WindowInsetsCompat.toWindowInsetsCompat(insets)
                v.updatePadding(
                    top = windowInsetsCompat.getInsets(WindowInsetsCompat.Type.statusBars()).top,
                    bottom = windowInsetsCompat.getInsets(WindowInsetsCompat.Type.navigationBars()).bottom,
                )
                insets
            }

        val adapter = OnboardingPagerAdapter(
            requireActivity()
        )
        var index = 0

        binding.viewPager.adapter = adapter
        binding.dotIndicator.attachTo(viewPager2 = binding.viewPager)

        binding.viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                updateButtonText(position = position)
                index = position
            }
        })
        updateButtonText(binding.viewPager.currentItem)

        binding.viewPager.setPageTransformer(ZoomOutPageTransformer())

        binding.btnNext.setOnClickListener {
            val position = binding.viewPager.currentItem
            if (position == 0 || position == 1) {
                binding.viewPager.currentItem = index + 1
            } else {
                sharePref.edit {
                    putBoolean("isOnboardingShown",true)
                }
                requireActivity().finish()
            }
        }
    }

    private fun updateButtonText(position: Int) {
        binding.btnNext.text = if (position == 0 || position == 1) {
            getString(R.string.onboarding_first_slide_button_text)
        } else {
            getString(R.string.onboarding_third_slide_button_text)
        }
    }
}