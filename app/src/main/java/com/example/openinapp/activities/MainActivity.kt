package com.example.openinapp.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.example.openinapp.R
import com.example.openinapp.Utils.TokenManager
import com.example.openinapp.api.ApiClient
import com.example.openinapp.api.ApiService
import com.example.openinapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setStatusBarAndNavigationBarColors()

        setUpApi()

        NavigationUI.setupWithNavController(binding.bottomNavBar, Navigation.findNavController(this,
            R.id.fragmentContainerView
        ))
    }

    private fun setUpApi() {
        val tokenManager = TokenManager(this)
        tokenManager.saveToken("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6MjU5MjcsImlhdCI6MTY3NDU1MDQ1MH0.dCkW0ox8tbjJA2GgUx2UEwNlbTZ7Rr38PVFJevYcXFI")
    }

    private fun setStatusBarAndNavigationBarColors() {
        window?.statusBarColor = resources.getColor(R.color.blue)
        window?.navigationBarColor = resources.getColor(R.color.white)
        val windowInsetsController = ViewCompat.getWindowInsetsController(window?.decorView!!)
        windowInsetsController?.isAppearanceLightStatusBars = true
        windowInsetsController?.isAppearanceLightNavigationBars = true
    }
}