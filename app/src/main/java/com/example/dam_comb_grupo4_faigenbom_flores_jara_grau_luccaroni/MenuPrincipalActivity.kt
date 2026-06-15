package com.example.dam_comb_grupo4_faigenbom_flores_jara_grau_luccaroni

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class MenuPrincipalActivity : AppCompatActivity() {

    private lateinit var helper: SQLiteHelper

    private lateinit var tvFechaInicio: TextView
    private lateinit var tvCantSociosActivos: TextView
    private lateinit var tvCantNoSocios: TextView
    private lateinit var tvCantVencenHoyBoton: TextView
    private lateinit var contenedorActividades: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu_principal)

        helper = SQLiteHelper(this)

        tvFechaInicio = findViewById(R.id.datetime_inicio)
        tvCantSociosActivos = findViewById(R.id.card_cantidad_socios_activos)
        tvCantNoSocios = findViewById(R.id.card_cantidad_no_socios)
        tvCantVencenHoyBoton = findViewById(R.id.txt_cantidad_socios_vencen_hoy)
        contenedorActividades = findViewById(R.id.contenedor_actividades_dinamico)

        val btnVencenHoy = findViewById<LinearLayout>(R.id.btn_aviso_socios_vencen_hoy)
        val navInicio = findViewById<LinearLayout>(R.id.nav_inicio)
        val navMiembros = findViewById<LinearLayout>(R.id.nav_miembros)
        val navMas = findViewById<LinearLayout>(R.id.nav_mas)

        btnVencenHoy.setOnClickListener {
            val intentLista = Intent(this, ActivityListaMiembros::class.java)
            intentLista.putExtra("FILTRO_MODO", "FILTRO_VENCEN_HOY")
            startActivity(intentLista)
        }

        navInicio.setOnClickListener {
        }
        navMiembros.setOnClickListener {
            startActivity(Intent(this, ActivityListaMiembros::class.java))
        }
        navMas.setOnClickListener {
            Toast.makeText(this, "EN PROGRESO...", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onResume() {
        super.onResume()
        sincronizarDashboard()
    }

    private fun sincronizarDashboard() {
        val calendar = Calendar.getInstance()
        val formatoSencillo = SimpleDateFormat("EEEE d 'de' MMMM, yyyy", Locale("es", "ES"))
        val fechaFormateada = formatoSencillo.format(calendar.time).replaceFirstChar { it.uppercase() }
        tvFechaInicio.text = fechaFormateada

        val activos = helper.contarSociosActivos()
        val noSocios = helper.contarNoSocios()
        val vencenHoy = helper.contarSociosVencenHoy()

        tvCantSociosActivos.text = activos.toString()
        tvCantNoSocios.text = noSocios.toString()
        tvCantVencenHoyBoton.text = vencenHoy.toString()

        contenedorActividades.removeAllViews()
        val actividadesRecientes = helper.obtenerActividadesRecientes()

        if (actividadesRecientes.isEmpty()) {
            val tvVacio = TextView(this).apply {
                text = "No se registraron movimientos recientes."
                try {
                    typeface = ResourcesCompat.getFont(context, R.font.montserrat_regular)
                } catch (e: Exception) { /* Por si no encuentra la fuente */ }
                setTextColor(ContextCompat.getColor(context, R.color.titan_nav_inactive))
                textSize = 14f
                setPadding(0, 20, 0, 0)
            }
            contenedorActividades.addView(tvVacio)
        } else {
            for (act in actividadesRecientes) {
                val itemLayout = layoutInflater.inflate(R.layout.item_actividad_reciente, contenedorActividades, false)

                val puntoEstado = itemLayout.findViewById<View>(R.id.punto_color_estado)
                val txtDescripcion = itemLayout.findViewById<TextView>(R.id.txt_actividad_descripcion)
                val txtHora = itemLayout.findViewById<TextView>(R.id.txt_actividad_hora)

                txtDescripcion.text = act.first
                txtHora.text = act.second

                val drawableRes = when (act.third) {
                    "verde" -> R.drawable.bg_punto_verde
                    "azul" -> R.drawable.bg_punto_azul
                    "marron" -> R.drawable.bg_punto_marron
                    else -> R.drawable.bg_punto_rojo
                }
                puntoEstado.setBackgroundResource(drawableRes)

                contenedorActividades.addView(itemLayout)
            }
        }
    }
}