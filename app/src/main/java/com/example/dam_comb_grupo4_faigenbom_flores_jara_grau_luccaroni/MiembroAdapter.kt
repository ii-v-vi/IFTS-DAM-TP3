package com.example.dam_comb_grupo4_faigenbom_flores_jara_grau_luccaroni

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

// Clase contenedora flexible para unificar la lista visual
data class WrapperMiembro(
    val nombre: String,
    val apellido: String,
    val dni: String,
    val esSocio: Boolean,
    val deudorVencido: Boolean = false,
    val fechaVencimiento: String? = null
)

class MiembroAdapter(
    private val lista: MutableList<WrapperMiembro>
) : RecyclerView.Adapter<MiembroAdapter.ViewHolder>() {

    class ViewHolder(vista: View) : RecyclerView.ViewHolder(vista) {
        val tvIniciales = vista.findViewById<TextView>(R.id.tvIniciales)
        val tvNombreCompleto = vista.findViewById<TextView>(R.id.tvNombreCompleto)
        val tvDniMiembro = vista.findViewById<TextView>(R.id.tvDniMiembro)
        val btnAccionCard = vista.findViewById<Button>(R.id.btnAccionCard)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val vista = LayoutInflater.from(parent.context).inflate(R.layout.item_miembro_card, parent, false)
        return ViewHolder(vista)
    }

    override fun getItemCount(): Int = lista.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val miembro = lista[position]
        val context = holder.itemView.context

        // 1. Asignamos Nombre y DNI
        holder.tvNombreCompleto.text = "${miembro.nombre} ${miembro.apellido}"
        holder.tvDniMiembro.text = "DNI ${miembro.dni}"

        // 2. Extraemos dinámicamente las iniciales para el avatar circular
        val iniN = miembro.nombre.take(1).uppercase()
        val iniA = miembro.apellido.take(1).uppercase()
        holder.tvIniciales.text = "$iniN$iniA"

        // 3. Modificamos el texto del botón según el estado (Visual)
        if (miembro.deudorVencido) {
            holder.btnAccionCard.text = "Vence Hoy"
        } else if (miembro.esSocio) {
            holder.btnAccionCard.text = "Socio"
        } else {
            holder.btnAccionCard.text = "No Socio"
        }

        // 4. CORRECCIÓN: Ahora el clic decide según el TIPO de miembro, ignorando si debe o no
        holder.btnAccionCard.setOnClickListener {
            if (miembro.esSocio) {
                // Si es Socio (deba o esté al día), va directo a su perfil de Socio
                val intent = Intent(context, PerfilSocioActivity::class.java)
                intent.putExtra("MEMBER_DNI", miembro.dni)
                context.startActivity(intent)
            } else {
                // Si es No Socio, va a su perfil de No Socio
                val intent = Intent(context, PerfilNoSocioActivity::class.java)
                intent.putExtra("MEMBER_DNI", miembro.dni)
                context.startActivity(intent)
            }
        }
    }
}