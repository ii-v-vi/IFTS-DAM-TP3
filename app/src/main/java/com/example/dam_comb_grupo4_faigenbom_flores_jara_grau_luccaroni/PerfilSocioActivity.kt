package com.example.dam_comb_grupo4_faigenbom_flores_jara_grau_luccaroni

import android.content.Intent
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView

class PerfilSocioActivity : AppCompatActivity() {

    private lateinit var helper: SQLiteHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_perfil_socio)

        helper = SQLiteHelper(this)

        val btnVolver = findViewById<LinearLayout>(R.id.btn_volver_a_miembros)
        val txtIniciales = findViewById<TextView>(R.id.txt_iniciales_socio)
        val txtNombreCompleto = findViewById<TextView>(R.id.txt_nombre_socio)

        val tvDni = findViewById<TextView>(R.id.datos_socio_dni)
        val tvEmail = findViewById<TextView>(R.id.datos_socio_email)
        val tvTelefono = findViewById<TextView>(R.id.datos_socio_telefono)
        val tvNacimiento = findViewById<TextView>(R.id.datos_socio_nacimiento)
        val tvFichaMedica = findViewById<TextView>(R.id.datos_socio_ficha_medica)

        val btnCobrar = findViewById<CardView>(R.id.btn_datos_socios_cobrar_cuota)
        val btnEditar = findViewById<CardView>(R.id.btn_datos_socios_editar)
        val btnInactivar = findViewById<CardView>(R.id.btn_datos_socios_inactivar)

        val dniRecibido = intent.getStringExtra("MEMBER_DNI")

        if (dniRecibido != null) {
            val socio = helper.obtenerSocioPorDni(dniRecibido)

            if (socio != null) {
                txtNombreCompleto.text = String.format("%s %s", socio.nombre, socio.apellido)
                tvDni.text = socio.dni
                tvEmail.text = socio.mail
                tvTelefono.text = socio.telefono
                tvNacimiento.text = socio.fechaNacimiento

                val iniN = socio.nombre.take(1).uppercase()
                val iniA = socio.apellido.take(1).uppercase()
                txtIniciales.text = String.format("%s%s", iniN, iniA)

                if (socio.fichaMedica) {
                    tvFichaMedica.text = "✓ Completa"
                    tvFichaMedica.setTextColor(getColor(R.color.titan_text_green))
                } else {
                    tvFichaMedica.text = "❌ Pendiente"
                    tvFichaMedica.setTextColor(getColor(R.color.titan_red))
                }
            } else {
                Toast.makeText(this, "Error BD", Toast.LENGTH_SHORT).show()
            }
        }

        btnVolver.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        btnCobrar.setOnClickListener {
            val intentCobrar = Intent(this, CobrarCuotaActivity::class.java)
            intentCobrar.putExtra("MEMBER_DNI", dniRecibido)
            startActivity(intentCobrar)
        }

        btnEditar.setOnClickListener {
            Toast.makeText(this, "Edit", Toast.LENGTH_SHORT).show()
        }

        btnInactivar.setOnClickListener {
            Toast.makeText(this, "Inactive", Toast.LENGTH_SHORT).show()
        }
    }
}