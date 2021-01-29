package com.example.desafio04.ui.main

import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.desafio04.domain.Game
import com.example.desafio04.repository.FirebaseStorageRepository
import com.example.desafio04.repository.FirestoreRepository
import com.google.firebase.firestore.ktx.toObject
import kotlinx.coroutines.launch

class FirestoreViewModel: ViewModel() {

    val listGames = MutableLiveData<List<Game>>()
    val imageUrl = MutableLiveData<String>()
    val game = MutableLiveData<Game>()
    val message = MutableLiveData<String>()

    val TAG = "===FIRESTORE_VIEW_MODEL"
    var firestoreRepository = FirestoreRepository()
    val firebaseStorageRepository = FirebaseStorageRepository()

    fun addGameToFirestore(game: MutableMap<String, Any>) {
        viewModelScope.launch{
            firestoreRepository.addGame(game).addOnSuccessListener {
                message.value = "Game cadastrado com sucesso!"
            }.addOnFailureListener {
                message.value = "Desculpe, não foi possível cadastrar o game. Tente novamente."
                Log.i(TAG, it.toString())
            }
            imageUrl.value = ""
        }
    }

    fun editGameOnFirestore(id: String, game: MutableMap<String, Any>) {
        viewModelScope.launch{
            firestoreRepository.editGame(id, game).addOnSuccessListener {
                message.value = "Game atualizado com sucesso!"
            }.addOnFailureListener {
                message.value = "Desculpe, não foi possível atualizar o game. Tente novamente."
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
                        listTemp.add(document.toObject<Game>().apply { id = document.id })
                    }
                    listGames.value = listTemp
                } else {
                    Log.w(TAG, "Error getting documents.", task.exception)
                }
            }
        }
    }

    fun getGameFromFirestore(id: String) {
        viewModelScope.launch {
            firestoreRepository.readGame(id)
                .addOnSuccessListener { document ->
                    if (document != null) {
                        game.value = document.toObject<Game>()?.apply { this.id = document.id }
                    } else {
                        Log.d(TAG, "No such document")
                    }
                }
                .addOnFailureListener { exception ->
                        Log.d(TAG, "get failed with ", exception)
                }
            }
        }

    fun uploadImage(data: Intent, context: Context) {
        firebaseStorageRepository.uploadImg(data, context)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val downloadUri = task.result
                    imageUrl.value = downloadUri!!.toString()
                        .substring(0, downloadUri.toString().indexOf("&token"))
                } else {
                    message.value = "Desculpe, não foi possível carregar a imagem. Tente novamente."
                    Log.i(TAG, "DEU RUIM !!!! ${task.exception}")
                }
            }
    }
}