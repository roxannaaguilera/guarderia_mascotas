package com.example.petcareday

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView

class PetAdapter(context: Context, private val lista: List<Pet>) : ArrayAdapter<Pet>(context, 0, lista) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val itemView = convertView ?: LayoutInflater.from(context).inflate(R.layout.item_pet, parent, false)

        val pet = lista[position]

        val nombreTextView = itemView.findViewById<TextView>(R.id.texto_nombre)
        val razaTextView = itemView.findViewById<TextView>(R.id.texto_raza)

        nombreTextView.text = pet.nombre
        razaTextView.text = pet.raza

        return itemView
    }
}
