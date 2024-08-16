package com.sci.coffeeandroid.feature.menudetails.ui.screen

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.sci.coffeeandroid.MainActivity
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

                    val checkedChipId = binding.chipGroupSize.checkedChipId

                    binding.chipGroupSize.setOnCheckedStateChangeListener { group, checkedIds ->

                    }

                    coffeeDetailViewModel.onSizeSelected((binding.chipGroupSize.checkedChipId).toString())

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

        binding.btnFav.setOnCheckedChangeListener { buttonView, isChecked ->

            if (isChecked){
                Toast.makeText(this,"Added to favourite",Toast.LENGTH_SHORT).show()
            }else
            {
                Toast.makeText(this,"Removed from favourite",Toast.LENGTH_SHORT).show()
            }
        }

        coffeeDetailViewModel.quantityLiveData.observe(this){
            binding.tvQuantity.text = coffeeDetailViewModel.quantityLiveData.value.toString()
        }

        binding.btnBack.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        binding.bottomAppBar.setOnClickListener {

        }

        binding.btnAddToCart.setOnClickListener {

        }

        binding.btnMinus.setOnClickListener {
            coffeeDetailViewModel.decreaseQuantity()
        }

        binding.btnPlus.setOnClickListener {
            coffeeDetailViewModel.increaseQuantity()
        }

    }
}