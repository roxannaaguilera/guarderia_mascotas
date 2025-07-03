package com.example.petcareday

import android.content.Context
import android.widget.Toast
import java.io.File
import java.io.FileWriter
import java.io.FileOutputStream


object ArchivoUtils {

    fun copiarArchivoRawASalida(context: Context, rawId: Int, nombreArchivoDestino: String) {
        val inputStream = context.resources.openRawResource(rawId)
        val outputFile = File(context.getExternalFilesDir(null), nombreArchivoDestino)

        if (!outputFile.exists()) {
            inputStream.use { input ->
                FileOutputStream(outputFile).use { output ->
                    input.copyTo(output)
                }
            }
        }
    }
    fun inicializarArchivoMascotasSiEsNecesario(context: Context) {
        val archivo = File(context.getExternalFilesDir(null), "mascotas.txt")
        if (!archivo.exists()) {
            copiarArchivoRawASalida(context, R.raw.mascotas, "mascotas.txt")
        }
    }
    fun guardarEnArchivo(context: Context, nombre: String, raza: String, genero: String, peso: String) {
        val linea = "$nombre|$raza|$genero|$peso\n"
        val archivo = File(context.getExternalFilesDir(null), "mascotas.txt")

        try {
            FileOutputStream(archivo, true).use { output ->
                output.write(linea.toByteArray())
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Toast.makeText(context, "Error al guardar en archivo", Toast.LENGTH_SHORT).show()
        }
    }
    fun cargarMascotasDesdeArchivo(context: Context): List<Pet> {
        val mascotas = mutableListOf<Pet>()
        val archivo = File(context.getExternalFilesDir(null), "mascotas.txt")

        if (archivo.exists()) {
            archivo.bufferedReader().useLines { lines ->
                lines.forEach { line ->
                    val partes = line.split("|")
                    if (partes.size == 4) {
                        val nombre = partes[0].trim()
                        val raza = partes[1].trim()
                        val genero = partes[2].trim()
                        val peso = partes[3].trim()
                        mascotas.add(Pet(nombre, raza, genero, peso))
                    }
                }
            }
        }

        return mascotas
    }


}

