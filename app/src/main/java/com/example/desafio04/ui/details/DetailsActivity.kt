package com.example.desafio04.ui.details

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.bumptech.glide.Glide
import com.example.desafio04.R
import com.example.desafio04.databinding.ActivityDetailsBinding
import com.example.desafio04.domain.Game
import com.example.desafio04.ui.main.FirestoreViewModel

class DetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailsBinding
    private lateinit var gameId: String
    val firestoreViewModel: FirestoreViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        gameId = getIdFromPutExtra()
    }

    override fun onResume() {
        super.onResume()

        firestoreViewModel.getGameFromFirestore(gameId)

        firestoreViewModel.game.observe(this) { game ->
            updateUI(game, binding)
            binding.btnEdit.setOnClickListener {
                editGame(game)
            }
        }

        binding.tbDetails.setNavigationOnClickListener {
            finish()
        }
    }

    private fun getIdFromPutExtra(): String {
        return intent.getStringExtra("id") ?: ""
    }

    private fun updateUI(game: Game, binding: ActivityDetailsBinding) {
        binding.tvDetailTitle.text = game.name
        binding.tvDetailName.text = game.name
        binding.tvDetailYear.text = getString(R.string.release, game.year.toString())
        binding.tvDetailDescription.text = game.description
        Glide.with(this).asBitmap()
            .load(game.url)
            .into(binding.ivDetailImg)
    }

    private fun editGame(game: Game) {
        val intent = Intent(this, EditGameActivity::class.java)
        intent.putExtra("id", game.id)
        intent.putExtra("name", game.name)
        intent.putExtra("year", game.year.toString())
        intent.putExtra("url", game.url)
        intent.putExtra("description", game.description)
        startActivity(intent)
    }
}