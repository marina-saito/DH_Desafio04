package com.example.desafio04.domain

data class Game(
    var id: String = "",
    val name: String = "",
    val year: Long = 0,
    val description: String = "",
    val url: String = ""
) {
}