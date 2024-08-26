package com.sci.coffeeandroid.feature.menudetails.ui.screen

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.widget.addTextChangedListener
import com.bumptech.glide.Glide
import com.sci.coffeeandroid.MainActivity
import com.sci.coffeeandroid.R
import com.sci.coffeeandroid.databinding.ActivityMenuDetailsBinding
import com.sci.coffeeandroid.feature.home.HomeActivity
import com.sci.coffeeandroid.feature.menudetails.domain.model.Size
import com.sci.coffeeandroid.feature.menudetails.domain.model.Sugar
import com.sci.coffeeandroid.feature.menudetails.domain.model.Variation
import com.sci.coffeeandroid.feature.menudetails.ui.viewmodel.MenuDetailsViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class MenuDetailsActivity : AppCompatActivity() {

    private val menuDetailsViewModel: MenuDetailsViewModel by viewModel() {
        parametersOf(
            coffeeId
        )
    }
    private lateinit var binding: ActivityMenuDetailsBinding
    private val coffeeId: Int by lazy {
        intent.getIntExtra("coffeeId", 0)
    }

    @SuppressLint("DefaultLocale", "SetTextI18n")
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

        menuDetailsViewModel.coffeeDetailsLiveData.observe(this) {
            when (it) {
                is MenuDetailsViewModel.CoffeeDetailUiState.Error -> {
                    binding.lottieLoading.visibility = View.GONE
                    binding.loadingBackground.visibility = View.GONE

                    onBackPressedDispatcher.onBackPressed()

                    Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show()
                }

                MenuDetailsViewModel.CoffeeDetailUiState.Loading -> {
                    binding.lottieLoading.visibility = View.VISIBLE
                    binding.loadingBackground.visibility = View.VISIBLE
                }

                is MenuDetailsViewModel.CoffeeDetailUiState.Success -> {
                    binding.lottieLoading.visibility = View.GONE
                    binding.loadingBackground.visibility = View.GONE
                    val currency = "$"
                    val price = String.format("%.2f", it.coffee.price)
                    binding.tvPrice.text = currency + price
                    binding.collapsingToolBarLayout.title = it.coffee.name
                    binding.tvDetailDescription.text = it.coffee.description
                    binding.btnFav.isChecked = it.coffee.isFavourite

                    binding.btnFav.icon = AppCompatResources.getDrawable(
                        binding.root.context,
                        if (it.coffee.isFavourite) R.drawable.ic_heart_filled else R.drawable.ic_heart_outlined
                    )

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
                        menuDetailsViewModel.customOrderLiveData.value?.milk.let {
                            if (it?.isNotEmpty() == true) {
                                binding.spinnerMilk.setSelection(menuDetailsViewModel.selectedMilkPosition)
                            }
                        }
                    }
                    ArrayAdapter(
                        this,
                        R.layout.spinner_item,
                        it.coffee.toppings
                    ).also { adapter ->
                        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item)
                        binding.spinnerToppings.adapter = adapter
                        menuDetailsViewModel.customOrderLiveData.value?.topping.let {
                            if (it?.isNotEmpty() == true) {
                                binding.spinnerToppings.setSelection(menuDetailsViewModel.selectedToppingPosition)
                            }
                        }
                    }
                }
            }
        }

        binding.chipGroupSize.setOnCheckedStateChangeListener { group, checkedIds ->
            if (checkedIds.isNotEmpty()) {
                when (checkedIds.first()) {
                    R.id.btn_chip_small -> {
                        menuDetailsViewModel.onSizeSelected(Size.SMALL)
                    }

                    R.id.btn_chip_medium -> {
                        menuDetailsViewModel.onSizeSelected(Size.MEDIUM)
                    }

                    R.id.btn_radio_large -> {
                        menuDetailsViewModel.onSizeSelected(Size.LARGE)
                    }
                }
            }

        }

        binding.chipGroupVariation.setOnCheckedStateChangeListener { group, checkedIds ->
            if (checkedIds.isNotEmpty()) {
                when (checkedIds.first()) {
                    R.id.btn_chip_hot -> {
                        menuDetailsViewModel.onVariationSelected(Variation.HOT)
                    }

                    R.id.btn_chip_cold -> {
                        menuDetailsViewModel.onVariationSelected(Variation.COLD)
                    }
                }
            }
        }

        binding.chipGroupSugar.setOnCheckedStateChangeListener { group, checkedIds ->
            if (checkedIds.isNotEmpty()) {
                when (checkedIds.first()) {
                    R.id.btn_chip_sugar_none -> {
                        menuDetailsViewModel.onSugarSelected(Sugar.NONE)
                    }

                    R.id.btn_chip_sugar_30percent -> {
                        menuDetailsViewModel.onSugarSelected(Sugar.THIRTY_PERCENT)
                    }

                    R.id.btn_chip_sugar_50percent -> {
                        menuDetailsViewModel.onSugarSelected(Sugar.FIFTY_PERCENT)
                    }
                }
            }
        }

        binding.btnFav.setOnClickListener {
            if (menuDetailsViewModel.isFavourite) {
                Toast.makeText(this, "Removed from favourites", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Added to favourites", Toast.LENGTH_SHORT).show()
            }
            menuDetailsViewModel.onFavouriteClicked()
        }

        menuDetailsViewModel.quantityLiveData.observe(this) {
            binding.tvQuantity.text = menuDetailsViewModel.quantityLiveData.value.toString()
        }

        binding.btnBack.setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            }
            startActivity(intent)
        }

        binding.spinnerMilk.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                menuDetailsViewModel.onMilkSelected(
                    milk = position
                )
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

        }
        binding.spinnerToppings.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    menuDetailsViewModel.onToppingSelected(
                        topping = position
                    )
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {

                }
            }

        binding.btnMinus.setOnClickListener {
            menuDetailsViewModel.decreaseQuantity()
            menuDetailsViewModel.updateQuantity(quantity = menuDetailsViewModel.quantityLiveData.value!!)
            Log.d("QuantityD", menuDetailsViewModel.customOrderLiveData.value.toString())
        }

        binding.btnPlus.setOnClickListener {
            menuDetailsViewModel.increaseQuantity()
            menuDetailsViewModel.updateQuantity(quantity = menuDetailsViewModel.quantityLiveData.value!!)
            Log.d("QuantityD", menuDetailsViewModel.customOrderLiveData.value.toString())
        }

        binding.edtSpecialInstructions.addTextChangedListener {
            menuDetailsViewModel.onSpecialInstructionsAdded(
                instruction = it.toString()
            )
        }

        binding.btnAddToCart.setOnClickListener {
            menuDetailsViewModel.addToCart()
            Toast.makeText(this, "Successfully added to cart", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        binding.bottomAppBar.setOnClickListener {

        }
    }

    companion object {
        fun newIntent(context: Context, coffeeId: Int) =
            Intent(context, MenuDetailsActivity::class.java).apply {
                putExtra("coffeeId", coffeeId)
            }
    }
}