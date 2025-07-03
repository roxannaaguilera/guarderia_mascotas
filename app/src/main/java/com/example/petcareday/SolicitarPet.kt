package com.example.petcareday

import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.AbsoluteSizeSpan
import android.text.style.ForegroundColorSpan
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat

class SolicitarPet : AppCompatActivity(), OnClickListener {

    private lateinit var editTextNombre: EditText
    private lateinit var editTextRaza: EditText
    private lateinit var editTextGenero: EditText
    private lateinit var editTextConstitucion: EditText
    private lateinit var buttonGuardarMascota: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_introducir_mascota)


        supportActionBar?.let { actionBar ->
            val titleColor = ContextCompat.getColor(this, android.R.color.white)
            val title = SpannableString("Listado de Mascotas")
            title.setSpan(
                ForegroundColorSpan(titleColor),
                0,
                title.length,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
            title.setSpan(
                AbsoluteSizeSpan(24, true),
                0,
                title.length,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
            actionBar.title = title

            val color = ContextCompat.getColor(this, R.color.rojoBotones)
            actionBar.setBackgroundDrawable(ColorDrawable(color))

        }


        configurarBotonHuella()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressedDispatcher
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun configurarBotonHuella() {
        val buttonHuella = findViewById<ImageButton>(R.id.boton_huella)
        buttonHuella.setOnClickListener {
            onClick(it)
        }
    }

    override fun onClick(view: View) {
        when (view.id) { R.id.boton_huella -> {

                val intent = Intent(this, CrearPet::class.java)
                startActivity(intent)
            }
            else -> {

                Toast.makeText(this, "Vista no manejada", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
