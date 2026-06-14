package com.example.dam_comb_grupo4_faigenbom_flores_jara_grau_luccaroni

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import java.util.Calendar

class PerfilSocioActivity : AppCompatActivity() {

    private lateinit var helper: SQLiteHelper
    private var dniRecibido: String? = null
    private var esActivo: Boolean = true

    private lateinit var txtIniciales: TextView
    private lateinit var txtNombreCompleto: TextView
    private lateinit var txtEstadoMembresia: TextView
    private lateinit var tvDni: TextView
    private lateinit var tvEmail: TextView
    private lateinit var tvTelefono: TextView
    private lateinit var tvNacimiento: TextView
    private lateinit var tvFichaMedica: TextView
    private lateinit var tvVenceCuota: TextView
    private lateinit var btnInactivar: CardView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_perfil_socio)

        helper = SQLiteHelper(this)

        val btnVolver = findViewById<LinearLayout>(R.id.btn_volver_a_miembros)
        txtIniciales = findViewById<TextView>(R.id.txt_iniciales_socio)
        txtNombreCompleto = findViewById<TextView>(R.id.txt_nombre_socio)
        txtEstadoMembresia = findViewById<TextView>(R.id.txt_estado_membresia)

        tvDni = findViewById<TextView>(R.id.datos_socio_dni)
        tvEmail = findViewById<TextView>(R.id.datos_socio_email)
        tvTelefono = findViewById<TextView>(R.id.datos_socio_telefono)
        tvNacimiento = findViewById<TextView>(R.id.datos_socio_nacimiento)
        tvFichaMedica = findViewById<TextView>(R.id.datos_socio_ficha_medica)
        tvVenceCuota = findViewById<TextView>(R.id.datos_socio_vence_cuota)

        val btnCobrar = findViewById<CardView>(R.id.btn_datos_socios_cobrar_cuota)
        val btnEditar = findViewById<CardView>(R.id.btn_datos_socios_editar)
        btnInactivar = findViewById<CardView>(R.id.btn_datos_socios_inactivar)

        // ---------- SOLUCIÓN XML: VINCULACIÓN DEL BOTÓN ELIMINAR ----------
        val btnEliminar = findViewById<CardView>(R.id.btn_datos_socios_eliminar)

        dniRecibido = intent.getStringExtra("MEMBER_DNI")

        cargarDatosSocio()

        btnVolver.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        btnCobrar.setOnClickListener {
            val intentCobrar = Intent(this, CobrarCuotaActivity::class.java)
            intentCobrar.putExtra("MEMBER_DNI", dniRecibido)
            startActivity(intentCobrar)
        }

        btnInactivar.setOnClickListener {
            alternarEstadoSocio()
        }

        btnEditar.setOnClickListener {
            mostrarDialogoEditar()
        }

        // ---------- SOLUCIÓN KOTLIN: ASIGNACIÓN DE DISPARADOR ----------
        btnEliminar.setOnClickListener {
            mostrarDialogoConfirmarEliminar()
        }
    }

    private fun cargarDatosSocio() {
        if (dniRecibido != null) {
            val socio = helper.obtenerSocioPorDni(dniRecibido!!)

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

                val wrapper = helper.obtenerSociosCompleto().find { it.dni == dniRecibido }
                tvVenceCuota.text = wrapper?.fechaVencimiento ?: "Sin fecha"

                esActivo = helper.esSocioActivo(dniRecibido!!)
                actualizarInterfazEstado(esActivo)

            } else {
                Toast.makeText(this, "Error BD", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun actualizarInterfazEstado(activo: Boolean) {
        if (activo) {
            txtEstadoMembresia.text = "Socio activo"
            try {
                val contenedorInterno = btnInactivar.getChildAt(0) as LinearLayout
                val tvBoton = contenedorInterno.getChildAt(0) as TextView
                tvBoton.text = "Inactivar"
            } catch (e: Exception) {
                e.printStackTrace()
            }
        } else {
            txtEstadoMembresia.text = "Socio inactivo"
            try {
                val contenedorInterno = btnInactivar.getChildAt(0) as LinearLayout
                val tvBoton = contenedorInterno.getChildAt(0) as TextView
                tvBoton.text = "Activar"
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun alternarEstadoSocio() {
        if (dniRecibido == null) return

        val nuevoEstado = !esActivo
        val exito = helper.actualizarEstadoSocio(dniRecibido!!, nuevoEstado)

        if (exito) {
            esActivo = nuevoEstado
            actualizarInterfazEstado(esActivo)
            val mensaje = if (esActivo) "Socio activado correctamente" else "Socio inactivado correctamente"
            Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Error al cambiar estado", Toast.LENGTH_SHORT).show()
        }
    }

    // ---------- SOLUCIÓN LOGICA: CUADRO DE DIÁLOGO DE ADVERTENCIA ----------
    private fun mostrarDialogoConfirmarEliminar() {
        if (dniRecibido == null) return

        AlertDialog.Builder(this)
            .setTitle("¿Eliminar Socio permanentemente?")
            .setMessage("Esta acción eliminará por completo al socio de la base de datos y no se podrá deshacer.\n\nSi solo deseas suspender su acceso, utiliza la opción 'Inactivar'. ¿Confirmas la eliminación?")
            .setPositiveButton("Eliminar") { dialog, _ ->
                val filasAfectadas = helper.eliminarSocio(dniRecibido!!)

                if (filasAfectadas > 0) {
                    Toast.makeText(this, "Socio eliminado correctamente", Toast.LENGTH_SHORT).show()

                    // Limpiamos la pila de pantallas para forzar el refresco de listas en el Home
                    val intentVolver = Intent(this, MenuPrincipalActivity::class.java)
                    intentVolver.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
                    startActivity(intentVolver)
                    finish()
                } else {
                    Toast.makeText(this, "Error al intentar eliminar el socio", Toast.LENGTH_SHORT).show()
                }
                dialog.dismiss()
            }
            .setNegativeButton("Cancelar") { dialog, _ ->
                dialog.dismiss()
            }
            .setIcon(android.R.drawable.ic_dialog_alert)
            .show()
    }

    private fun mostrarDialogoEditar() {
        if (dniRecibido == null) return
        val socio = helper.obtenerSocioPorDni(dniRecibido!!) ?: return

        val builder = AlertDialog.Builder(this)
        builder.setTitle("Editar Datos")

        val contenedorInputs = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(50, 20, 50, 20)
        }

        val inputNombre = EditText(this).apply { setText(socio.nombre); hint = "Nombre" }
        val inputApellido = EditText(this).apply { setText(socio.apellido); hint = "Apellido" }
        val inputTelefono = EditText(this).apply { setText(socio.telefono); hint = "Teléfono" }
        val inputEmail = EditText(this).apply { setText(socio.mail); hint = "Email" }

        val inputNacimiento = EditText(this).apply {
            setText(socio.fechaNacimiento)
            hint = "Fecha de Nacimiento (DD/MM/AAAA)"
            isFocusable = false
            isClickable = true
        }

        inputNacimiento.setOnClickListener {
            val cal = Calendar.getInstance()
            val fechaPartida = inputNacimiento.text.toString().split("/")
            if (fechaPartida.size == 3) {
                try {
                    cal.set(Calendar.DAY_OF_MONTH, fechaPartida[0].toInt())
                    cal.set(Calendar.MONTH, fechaPartida[1].toInt() - 1)
                    cal.set(Calendar.YEAR, fechaPartida[2].toInt())
                } catch (e: Exception) { e.printStackTrace() }
            }

            DatePickerDialog(this, { _, year, month, dayOfMonth ->
                val fechaSeleccionada = String.format("%02d/%02d/%d", dayOfMonth, month + 1, year)
                inputNacimiento.setText(fechaSeleccionada)
            }, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH)).show()
        }

        contenedorInputs.addView(inputNombre)
        contenedorInputs.addView(inputApellido)
        contenedorInputs.addView(inputTelefono)
        contenedorInputs.addView(inputEmail)
        contenedorInputs.addView(inputNacimiento)

        builder.setView(contenedorInputs)

        builder.setPositiveButton("Guardar") { dialog, _ ->
            val nom = inputNombre.text.toString().trim()
            val ape = inputApellido.text.toString().trim()
            val tel = inputTelefono.text.toString().trim()
            val mail = inputEmail.text.toString().trim()
            val nac = inputNacimiento.text.toString().trim()

            if (nom.isNotEmpty() && ape.isNotEmpty() && nac.isNotEmpty()) {
                val exito = helper.editarSocio(dniRecibido!!, nom, ape, tel, mail, nac)
                if (exito) {
                    Toast.makeText(this, "Cambios guardados", Toast.LENGTH_SHORT).show()
                    cargarDatosSocio()
                } else {
                    Toast.makeText(this, "Error al actualizar", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Campos obligatorios incompletos", Toast.LENGTH_SHORT).show()
            }
            dialog.dismiss()
        }

        builder.setNegativeButton("Cancelar") { dialog, _ -> dialog.cancel() }
        builder.show()
    }
}