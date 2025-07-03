package com.example.petcareday

import android.content.ContentValues
import android.content.Context
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


object BDController {

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val database = FirebaseDatabase.getInstance()
    private val usersRef = database.getReference("users")


    fun isEmailRegistered(email: String, callback: (Boolean) -> Unit) {
        Firebase.auth.fetchSignInMethodsForEmail(email)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val isEmailRegistered = task.result?.signInMethods?.isNotEmpty() == true
                    callback(isEmailRegistered)
                } else {
                    callback(false)
                }
            }
    }

    fun registerUser(email: String, password: String, callback: (Boolean, String) -> Unit) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val user: FirebaseUser? = auth.currentUser
                    val userId = user?.uid
                    val userMap = hashMapOf(
                        "email" to email,
                        "password" to password
                    )

                    userId?.let {
                        usersRef.child(it).setValue(userMap)
                            .addOnCompleteListener { dbTask ->
                                if (dbTask.isSuccessful) {
                                    callback(true, "Usuario registrado exitosamente.")
                                } else {
                                    callback(false, "Error al guardar los datos del usuario.")
                                }
                            }
                    }
                } else {
                    callback(false, "Error al registrar el usuario: ${task.exception?.message}")
                }
            }
    }


    fun loginUser(email: String, password: String, callback: (Boolean, String) -> Unit) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    callback(true, "Inicio de sesión exitoso.")
                } else {
                    callback(false, "Error al iniciar sesión: ${task.exception?.message}")
                }
            }
    }
    fun guardarEnFirebase(
        nombre: String,
        raza: String,
        genero: String,
        peso: String,
        onSuccess: () -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        val db = FirebaseFirestore.getInstance()
        val mascota = hashMapOf(
            "nombre" to nombre,
            "raza" to raza,
            "genero" to genero,
            "peso" to peso
        )

        db.collection("mascotas")
            .add(mascota)
            .addOnSuccessListener {
                onSuccess()
            }
            .addOnFailureListener { e ->
                e.printStackTrace()
                onFailure(e)
            }
    }

}


