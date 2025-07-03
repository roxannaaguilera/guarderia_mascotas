package com.example.petcareday

import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.AbsoluteSizeSpan
import android.text.style.ForegroundColorSpan
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class ModificarPet : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_modificar_pet)

        supportActionBar?.let { actionBar ->
            val titleColor = ContextCompat.getColor(this, android.R.color.white)
            val title = SpannableString("Editar")
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


    }
    }
