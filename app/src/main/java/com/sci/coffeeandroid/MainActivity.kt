package com.sci.coffeeandroid

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.sci.coffeeandroid.feature.onboarding.ui.screen.FirstSlideFragment
import com.sci.coffeeandroid.feature.onboarding.ui.screen.OnboardingFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, OnboardingFragment())
            .commit()
    }
}