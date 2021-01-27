package com.example.desafio04.ui.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.desafio04.R
import com.example.desafio04.databinding.ActivityMainBinding
import com.example.desafio04.ui.add.AddGameActivity

class MainActivity : AppCompatActivity(), GamesAdapter.OnClickGameListener {

    lateinit var adapterComic : GamesAdapter
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.fabAddGame.setOnClickListener {
            val intent = Intent(this, AddGameActivity::class.java)
            startActivity(intent)
            finish()
        }

    }

    override fun onClickGame(position: Int) {
        TODO("Not yet implemented")
    }
}