package com.example.dam_comb_grupo4_faigenbom_flores_jara_grau_luccaroni

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class ActivityConfirmarPagoNoSocio : AppCompatActivity() {

    private lateinit var helper: SQLiteHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_confirmar_pago_no_socio)

        helper = SQLiteHelper(this)
        val dni = intent.getStringExtra("MEMBER_DNI") ?: ""
        val nombreAct = intent.getStringExtra("ACTIVIDAD_NOMBRE") ?: ""
        val precioAct = intent.getStringExtra("ACTIVIDAD_PRECIO") ?: "0"

        val noSocio = helper.obtenerNoSocioPorDni(dni)
        val nombreCompleto = noSocio?.let { "${it.nombre} ${it.apellido}" } ?: "Invitado"
        val iniciales = (noSocio?.nombre?.firstOrNull()?.toString() ?: "I") + (noSocio?.apellido?.firstOrNull()?.toString() ?: "A")

        findViewById<TextView>(R.id.txt_iniciales_no_socio).text = iniciales.uppercase()
        findViewById<TextView>(R.id.pago_registrado_socio2).text = nombreCompleto
        findViewById<TextView>(R.id.pago_registrado_cuota2).text = nombreAct
        findViewById<TextView>(R.id.pago_registrado_vencimiento2).text = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(Date())
        findViewById<TextView>(R.id.pago_registrado_monto2).text = "$$precioAct"

        // BOTÓN: Confirmar Pago / Ver Comprobante
        findViewById<View>(R.id.btn_pago_registrado_compartir_comprobante).setOnClickListener {

            // 1. Convertimos el precio String a Double de forma segura para la BD
            val montoDouble = precioAct.replace("$", "").trim().toDoubleOrNull() ?: 0.0

            // 2. FIJACIÓN DE LA LÓGICA ROTA: Guardamos el pase diario en la tabla PagosNoSocios
            if (dni.isNotEmpty()) {
                val exito = helper.registrarPagoNoSocio(dni, nombreAct, montoDouble)
                if (!exito) {
                    Toast.makeText(this, "Error local al guardar el pago", Toast.LENGTH_SHORT).show()
                }
            }

            // 3. Redirige a la pantalla del comprobante (ActividadCobradaActivity)
            val intent = Intent(this, ActividadCobradaActivity::class.java).apply {
                putExtra("NOMBRE_COMPLETO", nombreCompleto)
                putExtra("ACTIVIDAD_NOMBRE", nombreAct)
                putExtra("ACTIVIDAD_PRECIO", precioAct)
                putExtra("FORMA_PAGO", "Efectivo")
            }
            startActivity(intent)
            finish()
        }

        findViewById<View>(R.id.btn_cancelar).setOnClickListener { finish() }
    }
}