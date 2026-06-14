package com.example.dam_comb_grupo4_faigenbom_flores_jara_grau_luccaroni

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class CobrarCuotaActivity : AppCompatActivity() {

    private lateinit var helper: SQLiteHelper
    private var dniRecibido: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Usamos el layout limpio (sin la barra de navegación inferior)
        setContentView(R.layout.activity_cobrar_cuota)

        helper = SQLiteHelper(this)
        dniRecibido = intent.getStringExtra("MEMBER_DNI")

        // Vincular vistas según el XML actualizado
        val tvNombreSocio = findViewById<TextView>(R.id.nombreSocio)
        val tvNroSocio = findViewById<TextView>(R.id.nroSocio)
        val tvNroSocioCuadrado = findViewById<TextView>(R.id.textView_nro_socio_cuadrado)
        val tvIniciales = findViewById<TextView>(R.id.txt_iniciales_socio)
        val tvActividad = findViewById<TextView>(R.id.tvActividad)
        val rgTipoDePago = findViewById<RadioGroup>(R.id.rgTipoDePago)
        val btnContinuar = findViewById<Button>(R.id.btnContinuar)

        // Lógica de carga dinámica (Soporta Socios existentes, nuevos y No Socios)
        if (dniRecibido != null) {
            val socio = helper.obtenerSocioPorDni(dniRecibido!!)
            if (socio != null) {
                // Caso: Socio (Existente o Recién Creado si se guardó en esta tabla)
                tvNombreSocio.text = "${socio.nombre} ${socio.apellido}"
                tvNroSocio.text = "Socio DNI: ${socio.dni}   -"
                tvNroSocioCuadrado.text = socio.dni.takeLast(4)
                tvActividad.text = "Cuota Mensual"
                tvIniciales.text = "${socio.nombre.firstOrNull() ?: ""}${socio.apellido.firstOrNull() ?: ""}".uppercase()
            } else {
                val noSocio = helper.obtenerNoSocioPorDni(dniRecibido!!)
                if (noSocio != null) {
                    // Caso: No Socio / Pase transitorio
                    tvNombreSocio.text = "${noSocio.nombre} ${noSocio.apellido}"
                    tvNroSocio.text = "No Socio DNI: ${noSocio.dni}   -"
                    tvNroSocioCuadrado.text = noSocio.dni.takeLast(4)
                    tvActividad.text = "Inscripción Pase"
                    tvIniciales.text = "${noSocio.nombre.firstOrNull() ?: ""}${noSocio.apellido.firstOrNull() ?: ""}".uppercase()
                } else {
                    Toast.makeText(this, "No se encontraron registros para el DNI: $dniRecibido", Toast.LENGTH_SHORT).show()
                    finish()
                }
            }
        }

        // Procesar selección y avanzar de manera segura
        btnContinuar.setOnClickListener {
            val idSeleccionado = rgTipoDePago.checkedRadioButtonId
            if (idSeleccionado != -1) {
                val formaPago = findViewById<RadioButton>(idSeleccionado).text.toString()

                // Armamos el Intent asegurando el paso de parámetros clave
                val intent = Intent(this, ActivityConfirmarPagoSocio::class.java).apply {
                    putExtra("MEMBER_DNI", dniRecibido) // Mantiene la referencia indexada al registro de la BD
                    putExtra("FORMA_PAGO", formaPago)
                }
                startActivity(intent)
                finish()
            } else {
                Toast.makeText(this, "Seleccione método de pago", Toast.LENGTH_SHORT).show()
            }
        }
    }
}