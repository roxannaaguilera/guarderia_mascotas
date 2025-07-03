package com.example.petcareday

import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.AbsoluteSizeSpan
import android.text.style.ForegroundColorSpan
import android.view.View
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat

 class CrearPet : AppCompatActivity(), OnClickListener {

     private lateinit var etNombre: EditText
     private lateinit var etRaza: EditText
     private lateinit var autoCompleteGenero: AutoCompleteTextView
     private lateinit var etPeso: EditText
     private lateinit var btnNueva: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_crear_mascota)

        etNombre = findViewById(R.id.etNombre)
        etRaza = findViewById(R.id.etRaza)
        etPeso = findViewById(R.id.etPeso)
        btnNueva = findViewById(R.id.btnNueva)
        autoCompleteGenero = findViewById(R.id.autoCompleteGenero)





        val autoCompleteGenero = findViewById<AutoCompleteTextView>(R.id.autoCompleteGenero)
        autoCompleteGenero.contentDescription = "Campo de género. Selecciona género."
        val items = listOf("Macho", "Hembra", "Binario")
        val adapter = ArrayAdapter(this, R.layout.list_item, items)
        autoCompleteGenero.setAdapter(adapter)


        supportActionBar?.let { actionBar ->
            val titleColor = ContextCompat.getColor(this, android.R.color.white)
            val title = SpannableString("NuevaMascota")
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
            actionBar.setDisplayHomeAsUpEnabled(true)


            val upArrow = ContextCompat.getDrawable(this, R.drawable.ic_arrow_back)
            upArrow?.setTint(ContextCompat.getColor(this, android.R.color.white))
            actionBar.setHomeAsUpIndicator(upArrow)
        }

        btnNueva.setOnClickListener { onClick(it) }
    }
     override fun onClick(view: View) {
         val nombre = etNombre.text.toString().trim()
         val raza = etRaza.text.toString().trim()
         val peso = etPeso.text.toString().trim()
         val genero = autoCompleteGenero.text.toString().trim()

         val camposVacios = nombre.isBlank() || raza.isBlank() || peso.isBlank() || genero.isBlank()
         val generoValido = genero in listOf("Macho", "Hembra", "Binario")
         val pesoEsNumero = peso.matches(Regex("^\\d+(\\.\\d+)?$"))

         if (camposVacios || !generoValido || !pesoEsNumero) {
             Toast.makeText(this, "Completa todos los campos correctamente", Toast.LENGTH_SHORT).show()
             return
         }

         if (!soloLetras(nombre) || !soloLetras(raza)) {
             Toast.makeText(this, "Nombre y raza deben contener solo letras", Toast.LENGTH_SHORT).show()
             return
         }

         ArchivoUtils.guardarEnArchivo(this, nombre, raza, genero, peso)

         BDController.guardarEnFirebase(
             nombre,
             raza,
             genero,
             peso,
             onSuccess = {
                 Toast.makeText(this, "Mascota guardada correctamente en Firebase", Toast.LENGTH_SHORT).show()


                 val intent = Intent(this, ListadoPet::class.java)
                 startActivity(intent)

             },
             onFailure = { e ->
                 Toast.makeText(this, "Error al guardar en Firebase: ${e.message}", Toast.LENGTH_LONG).show()
             }
         )
     }

     fun soloLetras(texto: String): Boolean {
         return texto.matches(Regex("^[a-zA-ZáéíóúÁÉÍÓÚüÜñÑ\\s]+$"))
     }
     override fun onSupportNavigateUp(): Boolean {
         onBackPressedDispatcher.onBackPressed()
         return true
     }
     override fun onResume() {
         super.onResume()

         etNombre.setText("")
         etRaza.setText("")
         etPeso.setText("")

     }
 }



