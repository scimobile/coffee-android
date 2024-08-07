package com.sci.coffeeandroid.feature.onboarding.ui.screen

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.sci.coffeeandroid.R

class OnboardingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_onboarding)

        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, OnboardingFragment())
            .commit()

    }
}