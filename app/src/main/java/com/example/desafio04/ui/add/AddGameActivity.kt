package com.example.desafio04.ui.add

import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import com.example.desafio04.databinding.ActivityAddGameBinding
import com.example.desafio04.ui.main.FirestoreViewModel
import com.google.firebase.storage.StorageReference
import dmax.dialog.SpotsDialog

class AddGameActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddGameBinding

    private val TAG = "=== AddGameActivity ==="

    lateinit var alertDialog: AlertDialog
    private val CODE_IMG = 1000

    val firestoreViewModel: FirestoreViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddGameBinding.inflate(layoutInflater)
        setContentView(binding.root)

        alertDialog = SpotsDialog.Builder().setContext(this).build()

        binding.infoHolder.cvAddImage.setOnClickListener {
            setIntent()
        }

        binding.infoHolder.btnAddGame.setOnClickListener {
            val game = getData()
            firestoreViewModel.addGameToFirestore(game)
            finish()
        }

    }

    // Pega os valores dos EditText e retorna um game
    private fun getData(): MutableMap<String, Any> {
        val game: MutableMap<String, Any> = HashMap()

        game["name"] = binding.infoHolder.etGameName.text.toString()
        game["year"] = binding.infoHolder.etYear.text.toString().toInt()
        game["description"] = binding.infoHolder.etDescription.text.toString()
        game["url"] = firestoreViewModel.imageUrl.value.toString()

        return game
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
                firestoreViewModel.uploadImage(data)
                alertDialog.dismiss()
            }
        }
    }
}