package com.sci.coffeeandroid.feature.home

import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.sci.coffeeandroid.R
import com.sci.coffeeandroid.feature.menudetails.ui.screen.MenuDetailsActivity

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_home)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val btnMenu = findViewById<Button>(R.id.btn_go_to_menu_detail)

        btnMenu.setOnClickListener {
            val intent = MenuDetailsActivity.newIntent(this, 1)
            startActivity(intent)
        }


    }
}