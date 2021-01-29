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

    lateinit var adapterGames : GamesAdapter
    private lateinit var binding: ActivityMainBinding

    private val TAG = "=== MainActivity ==="

    val firestoreViewModel: FirestoreViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.fabAddGame.setOnClickListener {
            val intent = Intent(this, AddGameActivity::class.java)
            startActivity(intent)
        }

        adapterGames = GamesAdapter(this)

        binding.rvGames.adapter = adapterGames
        binding.rvGames.hasFixedSize()

        firestoreViewModel.getGamesListFromFirestore()

        Log.i(TAG, adapterGames.listGames.toString())

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
//        intent.putExtra("name", adapterGames.listGames[position].name)
//        intent.putExtra("year", adapterGames.listGames[position].year.toString())
//        intent.putExtra("url", adapterGames.listGames[position].url)
//        intent.putExtra("description", adapterGames.listGames[position].description)
        startActivity(intent)
    }


}