package com.example.desafio04.repository

import android.util.Log
import com.example.desafio04.domain.Game
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot

class FirestoreRepository {

    val TAG = "FIREBASE_REPOSITORY"
    var firestoreDB = FirebaseFirestore.getInstance()
    val cr = firestoreDB.collection("games")

    fun addGame(game: MutableMap<String, Any>): Task<DocumentReference> {
        return cr.add(game)
    }

    fun readGames(): Task<QuerySnapshot> {
        return cr.get()
    }
}