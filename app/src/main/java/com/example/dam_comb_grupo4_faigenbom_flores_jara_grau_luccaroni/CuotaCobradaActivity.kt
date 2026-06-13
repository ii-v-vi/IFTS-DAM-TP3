package com.example.dam_comb_grupo4_faigenbom_flores_jara_grau_luccaroni

import android.content.Intent
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class CuotaCobradaActivity : AppCompatActivity() {

    private lateinit var helper: SQLiteHelper
    private var dniRecibido: String? = null
    private var formaPagoRecibida: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cuota_cobrada)

        helper = SQLiteHelper(this)

        // 1. CAPTURAMOS EL DNI Y LA FORMA DE PAGO QUE ENVIAMOS EN EL INTENT
        dniRecibido = intent.getStringExtra("MEMBER_DNI")
        formaPagoRecibida = intent.getStringExtra("FORMA_PAGO") ?: "Efectivo"

        // 2. VINCULAMOS LOS TEXTVIEWS DEL COMPROBANTE (IDs exactas de tu XML)
        val tvIniciales = findViewById<TextView>(R.id.txt_iniciales_socio)
        val tvMontoPagado = findViewById<TextView>(R.id.pago_registrado_monto_pagado)
        val tvMedioPago = findViewById<TextView>(R.id.pago_registrado_medio_de_pago)
        val tvFechaHora = findViewById<TextView>(R.id.pago_registrado_datetime)

        val btnPagoRegistradoVolverAInicio = findViewById<LinearLayout>(R.id.btn_pago_registrado_volver_a_inicio)
        val btnCompartir = findViewById<LinearLayout>(R.id.btn_pago_registrado_compartir_comprobante)

        var nombreSocioCompleto = "Socio"

        // 3. INYECTAMOS LOS DATOS REALES SI EXISTE EL DNI
        if (dniRecibido != null) {
            val socio = helper.obtenerSocioPorDni(dniRecibido!!)
            if (socio != null) {
                nombreSocioCompleto = "${socio.nombre} ${socio.apellido}"

                // Calculamos las iniciales dinámicamente (Ej: Lucas Luccaroni -> LL)
                val letraNombre = socio.nombre.firstOrNull()?.uppercase() ?: ""
                val letraApellido = socio.apellido.firstOrNull()?.uppercase() ?: ""
                tvIniciales.text = "$letraNombre$letraApellido"
            }
        }

        // Asignamos los datos fijos/calculados a la tarjeta visual del XML
        tvMontoPagado.text = "$45.000"
        tvMedioPago.text = formaPagoRecibida

        // Seteamos la fecha y hora exacta actual del cobro
        val sdf = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
        val fechaActual = sdf.format(Date())
        tvFechaHora.text = fechaActual

        // ---------- BOTÓN COMPARTIR (CON DATOS DINÁMICOS) ----------
        btnCompartir.setOnClickListener {
            val textoACompartir = """
                ÚLTIMO COMPROBANTE DE PAGO - TITAN CLUB
                -------------------------------------
                Miembro: $nombreSocioCompleto
                DNI: ${dniRecibido ?: "N/A"}
                Concepto: Cuota Mensual N°1
                Medio de Pago: $formaPagoRecibida
                Fecha: $fechaActual
                Monto: $45.000
                Estado: ✓ PAGADO
                -------------------------------------
                ¡Gracias por tu pago!
            """.trimIndent()

            val shareIntent = Intent(Intent.ACTION_SEND).apply {
                type = "text/plain"
                putExtra(Intent.EXTRA_TEXT, textoACompartir)
            }
            startActivity(Intent.createChooser(shareIntent, "Compartir comprobante vía:"))
        }

        // ---------- BOTÓN VOLVER AL INICIO (Limpia la pila de pantallas) ----------
        btnPagoRegistradoVolverAInicio.setOnClickListener {
            val intentarVolver = Intent(this, MenuPrincipalActivity::class.java)
            intentarVolver.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intentarVolver)
            finish()
        }
    }
}