package com.example.desafio04.ui.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import com.example.desafio04.databinding.ActivityMainBinding
import com.example.desafio04.ui.add.AddGameActivity
import com.example.desafio04.ui.details.DetailsActivity

class MainActivity : AppCompatActivity(), GamesAdapter.OnClickGameListener {

    private val TAG = "=== MainActivity ==="
    private lateinit var binding: ActivityMainBinding

    lateinit var adapterGames : GamesAdapter

    val firestoreViewModel: FirestoreViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Adapter configuration
        adapterGames = GamesAdapter(this)
        binding.rvGames.adapter = adapterGames
        binding.rvGames.hasFixedSize()

        // Add game button
        binding.fabAddGame.setOnClickListener {
            val intent = Intent(this, AddGameActivity::class.java)
            startActivity(intent)
        }

        firestoreViewModel.getGamesListFromFirestore()

        firestoreViewModel.listGames.observe(this) {
            adapterGames.listGames = it
            adapterGames.notifyDataSetChanged()
            Log.i(TAG, "$it")
        }
    }

    override fun onResume() {
        super.onResume()
        firestoreViewModel.getGamesListFromFirestore()
    }

    override fun onClickGame(position: Int) {
        val intent = Intent(this, DetailsActivity::class.java)
        intent.putExtra("id", adapterGames.listGames[position].id)
        startActivity(intent)
    }


}