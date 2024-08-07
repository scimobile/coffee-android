package com.sci.coffeeandroid

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding
import com.sci.coffeeandroid.feature.onboarding.ui.screen.FirstSlideFragment
import com.sci.coffeeandroid.feature.onboarding.ui.screen.OnboardingActivity
import com.sci.coffeeandroid.feature.onboarding.ui.screen.OnboardingFragment
import org.koin.android.ext.android.inject

class MainActivity : AppCompatActivity() {

    private val sharePref: SharedPreferences by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        enableEdgeToEdge()

        if (!sharePref.getBoolean("isOnboardingShown", false)) {
            val intent = Intent(this, OnboardingActivity::class.java)
            startActivity(intent)
        }
    }
}