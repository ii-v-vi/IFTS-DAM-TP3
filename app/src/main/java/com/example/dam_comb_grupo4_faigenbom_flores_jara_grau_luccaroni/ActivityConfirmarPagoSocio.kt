package com.example.dam_comb_grupo4_faigenbom_flores_jara_grau_luccaroni

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class ActivityConfirmarPagoSocio : AppCompatActivity() {

    private lateinit var helper: SQLiteHelper
    private var dniRecibido: String? = null
    private var formaPagoRecibida: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_confirmar_pago_socio)

        helper = SQLiteHelper(this)

        dniRecibido = intent.getStringExtra("MEMBER_DNI")
        formaPagoRecibida = intent.getStringExtra("FORMA_PAGO") ?: "Efectivo"

        val btnConfirmarPago = findViewById<View>(R.id.btn_pago_registrado_compartir_comprobante)

        val btnCancelar = findViewById<View>(R.id.btn_pago_registrado_volver_a_inicio)

        val tvIniciales = findViewById<TextView>(R.id.txt_iniciales_no_socio)
        val tvNombreSocio = findViewById<TextView>(R.id.pago_registrado_socio2)
        val tvNroCuota = findViewById<TextView>(R.id.pago_registrado_cuota2)
        val tvVencimiento = findViewById<TextView>(R.id.pago_registrado_vencimiento2)
        val tvTipoPago = findViewById<TextView>(R.id.pago_registrado_tipopago2)

        if (dniRecibido != null) {
            val socio = helper.obtenerSocioPorDni(dniRecibido!!)
            if (socio != null) {
                tvNombreSocio.text = "${socio.nombre} ${socio.apellido}"

                val letraNombre = socio.nombre.firstOrNull()?.uppercase() ?: ""
                val letraApellido = socio.apellido.firstOrNull()?.uppercase() ?: ""
                tvIniciales.text = "$letraNombre$letraApellido"

                tvNroCuota.text = "1"
                tvTipoPago.text = formaPagoRecibida

                val calendar = Calendar.getInstance()
                calendar.add(Calendar.MONTH, 1)
                val formatoFecha = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                tvVencimiento.text = formatoFecha.format(calendar.time)

            } else {
                Toast.makeText(this, "Error al recuperar datos del socio", Toast.LENGTH_SHORT).show()
                finish()
            }
        }

        btnConfirmarPago.setOnClickListener {
            if (dniRecibido != null && formaPagoRecibida != null) {

                val montoCuota = 0.0
                helper.registrarPagoYActualizarVencimiento(dniRecibido!!, formaPagoRecibida!!, montoCuota)
            }

            val intentarCuotaCobrada = Intent(this, CuotaCobradaActivity::class.java).apply {
                putExtra("MEMBER_DNI", dniRecibido)
            }
            startActivity(intentarCuotaCobrada)
            finish()
        }
        btnCancelar.setOnClickListener {
            val intentarCancelar = Intent(this, ActivityListaMiembros::class.java)
            intentarCancelar.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intentarCancelar)
            finish()
        }
    }
}