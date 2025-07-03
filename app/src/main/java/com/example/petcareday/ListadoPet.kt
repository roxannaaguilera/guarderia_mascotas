package com.example.petcareday

import android.content.Context
import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.AbsoluteSizeSpan
import android.text.style.ForegroundColorSpan
import android.view.View
import android.widget.ImageButton
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat


class ListadoPet : AppCompatActivity(), OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_view)

        supportActionBar?.let { actionBar ->

            val titleColor = ContextCompat.getColor(this, android.R.color.white)
            val title = SpannableString("Listado de Mascotas")
            title.setSpan(ForegroundColorSpan(titleColor), 0, title.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
            title.setSpan(AbsoluteSizeSpan(24, true), 0, title.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
            actionBar.title = title


            val color = ContextCompat.getColor(this, R.color.rojoBotones)
            actionBar.setBackgroundDrawable(ColorDrawable(color))

        }

        val listView = findViewById<ListView>(R.id.list_items)

        val mascotas = ArchivoUtils.cargarMascotasDesdeArchivo(this)
        mostrarListaMascotas(this, listView, mascotas)
        configurarBotonHuella()
        configurarClickEnItems(listView, mascotas)


    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return true
    }

    fun mostrarListaMascotas(context: Context, listView: ListView, mascotas: List<Pet>) {
        val adapter = PetAdapter(context, mascotas)
        listView.adapter = adapter
    }
    private fun configurarBotonHuella() {
        val buttonHuella = findViewById<ImageButton>(R.id.boton_huella_list_view)
        buttonHuella.setOnClickListener {
            onClick(it)
        }
    }
    override fun onClick(view: View) {
        when (view.id) { R.id.boton_huella_list_view -> {

            val intent = Intent(this, CrearPet::class.java)
            startActivity(intent)
        }
            else -> {

                Toast.makeText(this, "Vista no manejada", Toast.LENGTH_SHORT).show()
            }
        }
    }
    private fun configurarClickEnItems(listView: ListView, mascotas: List<Pet>) {
        listView.setOnItemClickListener { _, _, position, _ ->
            val mascotaSeleccionada = mascotas[position]
            val intent = Intent(this, ModificarPet::class.java)
            intent.putExtra("mascota", mascotaSeleccionada)
            startActivity(intent)
        }
    }


}