package com.example.dam_comb_grupo4_faigenbom_flores_jara_grau_luccaroni

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class EleccionActividadActivity : AppCompatActivity() {

    private var dniRecibido: String? = null
    private var cardSeleccionada: LinearLayout? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_eleccion_actividad)

        dniRecibido = intent.getStringExtra("MEMBER_DNI")

        val btnContinuar = findViewById<Button>(R.id.btnContinuar)
        val btnVolver = findViewById<TextView>(R.id.tvVolver)

        val tarjetas = listOf(R.id.llCardZumba, R.id.llCardPilates, R.id.llCardElongacion, R.id.llCardKarate, R.id.llCardSpinning, R.id.llCardHit)
        tarjetas.forEach { id ->
            findViewById<LinearLayout>(id).setOnClickListener { card ->
                cardSeleccionada?.setBackgroundColor(Color.parseColor("#DF4A47"))
                card.setBackgroundColor(Color.parseColor("#3AFF12"))
                cardSeleccionada = card as LinearLayout
            }
        }

        btnContinuar.setOnClickListener {
            if (cardSeleccionada == null) {
                Toast.makeText(this, "Debe seleccionar una actividad", Toast.LENGTH_LONG).show()
            } else {
                val nombreTv = when(cardSeleccionada!!.id) {
                    R.id.llCardZumba -> R.id.tvZumba; R.id.llCardPilates -> R.id.tvPilates; R.id.llCardElongacion -> R.id.tvElongacion
                    R.id.llCardKarate -> R.id.tvKarate; R.id.llCardSpinning -> R.id.tvSpinning; else -> R.id.tvHit
                }
                val precioTv = when(cardSeleccionada!!.id) {
                    R.id.llCardZumba -> R.id.tvPrecioZumba; R.id.llCardPilates -> R.id.tvPrecioPilates; R.id.llCardElongacion -> R.id.tvPrecioElongacion
                    R.id.llCardKarate -> R.id.tvPrecioKarate; R.id.llCardSpinning -> R.id.tvPrecioSpinning; else -> R.id.tvPrecioHit
                }

                val intentConfirmar = Intent(this, ActivityConfirmarPagoNoSocio::class.java).apply {
                    putExtra("MEMBER_DNI", dniRecibido)
                    putExtra("ACTIVIDAD_NOMBRE", findViewById<TextView>(nombreTv).text.toString())
                    putExtra("ACTIVIDAD_PRECIO", findViewById<TextView>(precioTv).text.toString().replace("$", ""))
                }
                startActivity(intentConfirmar)
            }
        }
        btnVolver.setOnClickListener { onBackPressedDispatcher.onBackPressed() }
    }
}