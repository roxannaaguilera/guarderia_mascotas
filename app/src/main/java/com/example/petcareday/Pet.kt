package com.example.petcareday
import java.io.Serializable

data class Pet(
    val nombre: String,
    val raza: String,
    val genero: String,
    val peso: String

): Serializable