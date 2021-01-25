package com.example.desafio04.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.desafio04.R

class MainActivity : AppCompatActivity(), GamesAdapter.OnClickGameListener {

    lateinit var adapterComic : GamesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onClickGame(position: Int) {
        TODO("Not yet implemented")
    }
}