package com.sci.coffeeandroid.feature.menudetails.ui.screen

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.sci.coffeeandroid.R
import com.sci.coffeeandroid.databinding.ActivityMenuDetailsBinding
import com.sci.coffeeandroid.feature.menudetails.ui.viewmodel.CoffeeDetailViewModel
import kotlinx.coroutines.delay
import org.koin.androidx.viewmodel.ext.android.viewModel

class MenuDetailsActivity() : AppCompatActivity() {

    private val coffeeDetailViewModel: CoffeeDetailViewModel by viewModel()
    private lateinit var binding: ActivityMenuDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMenuDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        coffeeDetailViewModel.fetchCoffeeDetail(id = 1)

        coffeeDetailViewModel.coffeeDetailLiveData.observe(this){
            when (it) {
                is CoffeeDetailViewModel.CoffeeDetailUiState.Error -> TODO()
                CoffeeDetailViewModel.CoffeeDetailUiState.Loading -> {

                }
                is CoffeeDetailViewModel.CoffeeDetailUiState.Success -> {
                    binding.tvPrice.text = ("$${it.coffee.price}").toString()
                    binding.collapsingToolBarLayout.title = it.coffee.name
                    binding.tvDetailDescription.text = it.coffee.description

                    Glide.with(this)
                        .load(it.coffee.image)
                        .into(binding.ivCoffeeDetail)

                    ArrayAdapter(
                        this,
                        R.layout.spinner_item,
                        it.coffee.milk
                    ).also { adapter ->
                        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item)
                        binding.spinnerMilk.adapter = adapter
                    }
                    ArrayAdapter(
                        this,
                        R.layout.spinner_item,
                        it.coffee.toppings
                    ).also { adapter ->
                        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item)
                        binding.spinnerToppings.adapter = adapter
                    }
                }
            }
        }

        binding.btnAddToCart.setOnClickListener {
            binding.chipGroupSize.checkedChipId
        }

        ArrayAdapter.createFromResource(
            this,
            R.array.milk_array,
            R.layout.spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(R.layout.spinner_dropdown_item)
            binding.spinnerMilk.adapter = adapter
        }
        ArrayAdapter.createFromResource(
            this,
            R.array.coffee_toppings,
            R.layout.spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(R.layout.spinner_dropdown_item)
            binding.spinnerToppings.adapter = adapter
        }
    }
}