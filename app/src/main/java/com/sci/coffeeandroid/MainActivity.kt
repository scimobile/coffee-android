package com.sci.coffeeandroid

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.sci.coffeeandroid.feature.onboarding.ui.screen.OnboardingActivity
import org.koin.android.ext.android.inject

class MainActivity : AppCompatActivity() {

    private val sharePref: SharedPreferences by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        enableEdgeToEdge()

        if (!sharePref.getBoolean(getString(R.string.is_onboarding_shown), false)) {
            val intent = Intent(this, OnboardingActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}