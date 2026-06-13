package com.example.dam_comb_grupo4_faigenbom_flores_jara_grau_luccaroni

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView

class SocioAdapter(
    private val lista: MutableList<Socio>
) : RecyclerView.Adapter<SocioAdapter.ViewHolder>() {

    class ViewHolder(
        vista: View
    ) : RecyclerView.ViewHolder(vista) {

        val tvNombre =
            vista.findViewById<TextView>(
                R.id.tvNombre
            )

        val btnEliminar =
            vista.findViewById<Button>(
                R.id.btnEliminar
            )
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {

        val vista =
            LayoutInflater
                .from(parent.context)
                .inflate(
                    R.layout.item_socio,
                    parent,
                    false
                )

        return ViewHolder(vista)
    }

    override fun getItemCount(): Int {
        return lista.size
    }

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int
    ) {

        val socio = lista[position]

        holder.tvNombre.text =
            "${socio.nombre} ${socio.apellido} - ${socio.dni}"

        holder.btnEliminar.setOnClickListener {
            val context = holder.itemView.context
            val nombreCompleto = "${socio.nombre} ${socio.apellido}"

            AlertDialog.Builder(context)
                .setTitle("Confirmar eliminación")
                .setMessage("¿Estás seguro de que deseas eliminar al socio $nombreCompleto?")
                .setPositiveButton("Eliminar") { _, _ ->
                    val currentPosition = holder.bindingAdapterPosition
                    if (currentPosition != RecyclerView.NO_POSITION) {
                        val socioAEliminar = lista[currentPosition]
                        val helper = SQLiteHelper(context)
                        
                        // Eliminar de la base de datos
                        helper.eliminarSocio(socioAEliminar.dni)
                        
                        // Eliminar de la lista de la interfaz
                        lista.removeAt(currentPosition)
                        notifyItemRemoved(currentPosition)
                        notifyItemRangeChanged(currentPosition, lista.size)
                        
                        Toast.makeText(
                            context,
                            "Se ha eliminado el socio $nombreCompleto",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
                .setNegativeButton("Cancelar", null)
                .show()
        }
    }
}