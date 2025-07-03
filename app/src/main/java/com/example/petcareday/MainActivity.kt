package com.example.petcareday

import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import android.text.Spannable
import android.text.SpannableString
import android.text.style.AbsoluteSizeSpan
import android.text.style.ForegroundColorSpan

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var editTextEmail: EditText
    private lateinit var editTextContraseña: EditText
    private lateinit var buttonAcceder: Button
    private lateinit var buttonRegistrar: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        ArchivoUtils.inicializarArchivoMascotasSiEsNecesario(this)

        supportActionBar?.let { actionBar ->
            val titleColor = ContextCompat.getColor(this, android.R.color.white)
            val title = SpannableString("Login")
            title.setSpan(ForegroundColorSpan(titleColor), 0, title.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
            title.setSpan(AbsoluteSizeSpan(24, true), 0, title.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
            val color = ContextCompat.getColor(this, R.color.rojoBotones)
            actionBar.title = title
            actionBar.setBackgroundDrawable(ColorDrawable(color))
        }

        editTextEmail = findViewById(R.id.editTextEmail)
        editTextContraseña = findViewById(R.id.editTextContraseña)
        buttonAcceder = findViewById(R.id.buttonAcceder)
        buttonRegistrar = findViewById(R.id.buttonRegistrar)

        buttonAcceder.setOnClickListener(this)
        buttonRegistrar.setOnClickListener(this)
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.buttonAcceder -> handleLogin()
            R.id.buttonRegistrar -> handleRegister()
        }
    }

    private fun handleLogin() {
        val email = editTextEmail.text.toString().trim()
        val password = editTextContraseña.text.toString().trim()

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Debes rellenar todos los campos", Toast.LENGTH_SHORT).show()
            return
        }

        if (isValidEmail() && isValidPassword()) {
            BDController.loginUser(email, password) { isSuccess, message ->
                if (isSuccess) {
                    Toast.makeText(this, "Bienvenido: $email", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, SolicitarPet::class.java)
                    intent.putExtra("titulo", "Mascotas")
                    startActivity(intent)

                } else {
                    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun handleRegister() {
        val email = editTextEmail.text.toString().trim()
        val password = editTextContraseña.text.toString().trim()

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Debes rellenar todos los campos", Toast.LENGTH_SHORT).show()
            return
        }

        if (isValidEmail() && isValidPassword()) {
            BDController.isEmailRegistered(email) { isRegistered ->
                if (isRegistered) {
                    Toast.makeText(this, "Correo ya registrado, puedes loguearte", Toast.LENGTH_SHORT).show()
                } else {
                    BDController.registerUser(email, password) { isSuccess, message ->
                        if (isSuccess) {
                            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
                        } else {
                            Toast.makeText(this, "Error al registrar el usuario", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }
    }

    private fun isValidEmail(): Boolean {
        val email = editTextEmail.text.toString().trim()
        return when {
            email.isEmpty() -> {
                editTextEmail.error = "Por favor, ingresa tu correo"
                false
            }
            !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches() -> {
                editTextEmail.error = "Formato de correo inválido"
                false
            }
            else -> true
        }
    }

    private fun isValidPassword(): Boolean {
        val password = editTextContraseña.text.toString().trim()
        return when {
            password.isEmpty() -> {
                editTextContraseña.error = "Por favor, ingresa tu contraseña"
                false
            }
            password.length < 6 -> {
                editTextContraseña.error = "Debe tener al menos 6 caracteres"
                false
            }
            password.contains(" ") -> {
                editTextContraseña.error = "No debe contener espacios"
                false
            }
            else -> true
        }
    }
    override fun onResume() {
        super.onResume()

        editTextEmail.setText("")
        editTextContraseña.setText("")
    }
}















