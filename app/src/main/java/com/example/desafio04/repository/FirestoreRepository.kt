package com.example.desafio04.repository

import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.*
import java.util.*

class FirestoreRepository {

    var firestoreDB = FirebaseFirestore.getInstance()
    val cr = firestoreDB.collection("games")

    fun addGame(game: MutableMap<String, Any>): Task<Void> {
        val gameId = Calendar.getInstance().timeInMillis.toString()
        return cr.document(gameId).set(game)
    }

    fun editGame(id: String, game: MutableMap<String, Any>): Task<Void> {
        return cr.document(id).update(game)
    }

    fun readGames(): Task<QuerySnapshot> {
        return cr.get()
    }

    fun readGame(id: String): Task<DocumentSnapshot> {
        return cr.document(id).get()
    }
}