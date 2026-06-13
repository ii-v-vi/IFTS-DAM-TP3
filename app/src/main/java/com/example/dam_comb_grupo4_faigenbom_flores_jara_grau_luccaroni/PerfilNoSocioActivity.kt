package com.example.dam_comb_grupo4_faigenbom_flores_jara_grau_luccaroni

import android.content.Intent
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView

class PerfilNoSocioActivity : AppCompatActivity() {

    private lateinit var helper: SQLiteHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_perfil_no_socio)

        helper = SQLiteHelper(this)

        val btnVolver = findViewById<LinearLayout>(R.id.btn_volver_a_miembros)
        val txtIniciales = findViewById<TextView>(R.id.txt_iniciales_no_socio)
        val txtNombreCompleto = findViewById<TextView>(R.id.txt_nombre_no_socio)

        val tvDni = findViewById<TextView>(R.id.datos_no_socio_dni)
        val tvEmail = findViewById<TextView>(R.id.datos_no_socio_email)
        val tvTelefono = findViewById<TextView>(R.id.datos_no_socio_telefono)
        val tvNacimiento = findViewById<TextView>(R.id.datos_no_socio_nacimiento)
        val tvActividad = findViewById<TextView>(R.id.datos_no_socio_ficha_medica)

        // Vinculación corregida de botones usando las IDs exactas de tu XML
        val btnCobrarActividad = findViewById<CardView>(R.id.btn_datos_no_socios_cobrar_actividad)
        val btnEditarNoSocio = findViewById<CardView>(R.id.btn_datos_no_socios_editar)
        val btnInactivarNoSocio = findViewById<CardView>(R.id.btn_datos_no_socios_inactivar)

        val dniRecibido = intent.getStringExtra("MEMBER_DNI")

        if (dniRecibido != null) {
            val noSocio = helper.obtenerNoSocioPorDni(dniRecibido)

            if (noSocio != null) {
                txtNombreCompleto.text = String.format("%s %s", noSocio.nombre, noSocio.apellido)
                tvDni.text = noSocio.dni
                tvEmail.text = noSocio.mail
                tvTelefono.text = noSocio.telefono
                tvNacimiento.text = noSocio.fechaNacimiento

                val iniN = noSocio.nombre.take(1).uppercase()
                val iniA = noSocio.apellido.take(1).uppercase()
                txtIniciales.text = String.format("%s%s", iniN, iniA)

                if (noSocio.fichaMedica) {
                    tvActividad.text = "Pase Libre (Apto)"
                    tvActividad.setTextColor(getColor(R.color.titan_text_primary))
                } else {
                    tvActividad.text = "Falta Ficha Médica"
                    tvActividad.setTextColor(getColor(R.color.titan_red))
                }
            } else {
                Toast.makeText(this, "Error BD", Toast.LENGTH_SHORT).show()
            }
        }

        btnVolver.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        btnCobrarActividad.setOnClickListener {
            // CAMBIO: Ahora va primero a elegir la actividad
            val intentActividad = Intent(this, EleccionActividadActivity::class.java)
            intentActividad.putExtra("MEMBER_DNI", dniRecibido)
            startActivity(intentActividad)
        }

        btnEditarNoSocio.setOnClickListener {
            Toast.makeText(this, "Edit", Toast.LENGTH_SHORT).show()
        }

        btnInactivarNoSocio.setOnClickListener {
            Toast.makeText(this, "Inactive", Toast.LENGTH_SHORT).show()
        }
    }
}