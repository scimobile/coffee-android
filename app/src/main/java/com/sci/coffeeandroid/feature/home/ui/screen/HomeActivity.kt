package com.sci.coffeeandroid.feature.home.ui.screen

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.sci.coffeeandroid.R
import com.sci.coffeeandroid.databinding.ActivityHomeBinding
import com.sci.coffeeandroid.feature.home.ui.adapter.HomeSectionsAdapter
import com.sci.coffeeandroid.feature.home.ui.viewmodel.HomeMenuUIState
import com.sci.coffeeandroid.feature.home.ui.viewmodel.HomeViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding
    private val viewModel: HomeViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        Glide.with(this)
            .load("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQkAJEkJQ1WumU0hXNpXdgBt9NUKc0QDVIiaw&s")
            .into(binding.ivProfile)

        val adapter = HomeSectionsAdapter(
            onCategoryClick = { categoryId ->
                viewModel.onCategorySelected(categoryId)
            }
        )
        binding.rvMenuSections.adapter = adapter

        viewModel.homeMenuSectionsLive.observe(this) {
            when (it) {
                HomeMenuUIState.Loading -> {}
                is HomeMenuUIState.Success -> {
                    adapter.updateList(it.data)
                }

                is HomeMenuUIState.Error -> {}
            }
        }

    }

}