package com.example.dam_comb_grupo4_faigenbom_flores_jara_grau_luccaroni

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.Button
import android.content.Intent

class ActivityListaMiembros : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_miembros)

            val btnAgregarMiembro = findViewById<Button>(R.id.btnAgregarMiembro)

            btnAgregarMiembro.setOnClickListener {
                val intent = Intent(this, MainActivity::class.java)

                startActivity(intent)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
                startActivity(intent)
        }
    }
}