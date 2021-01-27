package com.example.desafio04.ui.add

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.desafio04.R
import com.example.desafio04.databinding.ActivityAddGameBinding

class AddGameActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddGameBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddGameBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}