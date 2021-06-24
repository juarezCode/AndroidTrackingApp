package com.juarez.trackingmapapp.ui

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.juarez.trackingmapapp.databinding.ActivityMainBinding
import com.juarez.trackingmapapp.viewmodel.MainViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.greeting.observe(this, { greeting ->
            binding.txtGreeting.text = greeting
        })

        startActivity(Intent(this, MapsActivity::class.java))
    }
}