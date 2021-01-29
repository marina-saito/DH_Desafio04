package com.example.desafio04.ui.details

import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import com.bumptech.glide.Glide
import com.example.desafio04.R
import com.example.desafio04.databinding.ActivityDetailsBinding
import com.example.desafio04.databinding.ActivityEditGameBinding
import com.example.desafio04.domain.Game
import com.example.desafio04.ui.main.FirestoreViewModel
import dmax.dialog.SpotsDialog

class EditGameActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEditGameBinding
    val firestoreViewModel: FirestoreViewModel by viewModels()

    lateinit var alertDialog: AlertDialog
    private val CODE_IMG = 2000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditGameBinding.inflate(layoutInflater)
        setContentView(binding.root)

        alertDialog = SpotsDialog.Builder().setContext(this).build()

        // Fill the data from the game being edited
        val game = getDataFromPutExtra()
        updateUI(game, binding)

        // Button for loading new image
        binding.editHolder.cvAddImage.setOnClickListener {
            setIntent()
        }

        // Save button
        binding.editHolder.btnAddGame.setOnClickListener {
            if (binding.editHolder.etGameName.text.toString() != "" && binding.editHolder.etYear.text.toString() != "") {
                val updatedGame = getData()
                firestoreViewModel.editGameOnFirestore(game.id, updatedGame)
                finish()
            } else {
                Toast.makeText(this, "Por favor preencha o nome e o ano de lançamento do game!", Toast.LENGTH_LONG).show()
            }
        }

        // Update ImageView if new image is loaded
        firestoreViewModel.imageUrl.observe(this) {
            Glide.with(this).asBitmap()
                .load(it)
                .into(binding.editHolder.ivGame)
        }
    }

    private fun getDataFromPutExtra(): Game {
        val id = intent.getStringExtra("id") ?: ""
        val name = intent.getStringExtra("name") ?: ""
        val year = intent.getStringExtra("year") ?: ""
        val url = intent.getStringExtra("url") ?: ""
        val description = intent.getStringExtra("description") ?: ""

        firestoreViewModel.imageUrl.value = url
        return Game(id, name, year.toLong(), description, url)
    }

    private fun updateUI(game: Game, binding: ActivityEditGameBinding) {
        binding.editHolder.etGameName.setText(game.name)
        binding.editHolder.etYear.setText(game.year.toString())
        binding.editHolder.etDescription.setText(game.description)
        Glide.with(this).asBitmap()
            .load(game.url)
            .into(binding.editHolder.ivGame)
    }

    private fun getData(): MutableMap<String, Any> {
        val updatedGame: MutableMap<String, Any> = HashMap()

        updatedGame["name"] = binding.editHolder.etGameName.text.toString()
        updatedGame["year"] = binding.editHolder.etYear.text.toString().toInt()
        updatedGame["description"] = binding.editHolder.etDescription.text.toString()
        updatedGame["url"] = firestoreViewModel.imageUrl.value.toString()

        return updatedGame
    }

    private fun setIntent() {
        val intent = Intent()
        intent.type = "image/"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent, "Selecione a imagem do game"), CODE_IMG)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == CODE_IMG) {
            alertDialog.show()
            if (data != null) {
                firestoreViewModel.uploadImage(data, this)
                alertDialog.dismiss()
            }
        }
    }
}