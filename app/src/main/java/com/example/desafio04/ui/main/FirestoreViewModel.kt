package com.example.desafio04.ui.main

import android.content.Intent
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.desafio04.domain.Game
import com.example.desafio04.repository.FirestoreRepository
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.launch
import java.util.*

class FirestoreViewModel: ViewModel() {

    val listGames = MutableLiveData<List<Game>>()
    val imageUrl = MutableLiveData<String>()

    val TAG = "===FIRESTORE_VIEW_MODEL"
    var firestoreRepository = FirestoreRepository()
    var storageReference = FirebaseStorage.getInstance().reference

    fun addGameToFirestore(game: MutableMap<String, Any>) {
        viewModelScope.launch{
            firestoreRepository.addGame(game).addOnSuccessListener {
                Log.i(TAG, "Game cadastrado com sucesso")
            }.addOnFailureListener {
                Log.i(TAG, it.toString())
            }
            imageUrl.value = ""
        }
    }

    fun getGamesListFromFirestore() {
        viewModelScope.launch {
            val listTemp = mutableListOf<Game>()
            firestoreRepository.readGames().addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    for (document in task.result!!) {
                        listTemp.add(document.toObject<Game>()
                        )
                    }
                    listGames.value = listTemp
                } else {
                    Log.w(TAG, "Error getting documents.", task.exception)
                }
            }
        }
    }

    fun uploadImage(data: Intent) {
        val imgId = Calendar.getInstance().timeInMillis.toString() //data.data.toString().substring(data.data.toString().lastIndexOf("/")+1)
        val uploadTask = storageReference.child("game_imgs/$imgId").putFile(data.data!!)
        val task = uploadTask.continueWithTask { task ->
            if (task.isSuccessful) {
            }
            storageReference.child("game_imgs/$imgId").downloadUrl
        }.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val downloadUri = task.result
                imageUrl.value = downloadUri!!.toString()
                    .substring(0, downloadUri.toString().indexOf("&token"))
            }
            else {
                Log.i(TAG, "DEU RUIM !!!! ${task.exception}")
            }
        }
    }
}