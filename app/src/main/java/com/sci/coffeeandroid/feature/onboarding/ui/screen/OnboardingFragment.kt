package com.sci.coffeeandroid.feature.onboarding.ui.screen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sci.coffeeandroid.R
import com.sci.coffeeandroid.databinding.FragmentOnboardingBinding
import com.sci.coffeeandroid.feature.onboarding.ui.adapter.OnboardingPagerAdapter


class OnboardingFragment : Fragment() {

    private var binding: FragmentOnboardingBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return FragmentOnboardingBinding.inflate(inflater, container, false).also {
            binding = it
        }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = OnboardingPagerAdapter(
            requireActivity()
        )
        binding?.viewPager?.adapter = adapter
    }


}