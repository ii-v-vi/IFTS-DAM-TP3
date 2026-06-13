package com.example.dam_comb_grupo4_faigenbom_flores_jara_grau_luccaroni

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class SQLiteHelper(context: Context): SQLiteOpenHelper(context, "clubdeportivo.db", null, 1) {

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL("CREATE TABLE Socios(id INTEGER PRIMARY KEY AUTOINCREMENT, nombre TEXT, apellido TEXT, dni TEXT, telefono TEXT, mail TEXT, fechaNacimiento TEXT, fichaMedica INTEGER)")
        db.execSQL("CREATE TABLE NoSocios(id INTEGER PRIMARY KEY AUTOINCREMENT, nombre TEXT, apellido TEXT, dni TEXT, telefono TEXT, mail TEXT, fechaNacimiento TEXT, fichaMedica INTEGER)")
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS Socios")
        db.execSQL("DROP TABLE IF EXISTS NoSocios")
        onCreate(db)
    }

    fun insertarSocio(socio: Socio): Long {
        val db = writableDatabase
        val valores = ContentValues().apply {
            put("nombre", socio.nombre); put("apellido", socio.apellido); put("dni", socio.dni)
            put("telefono", socio.telefono); put("mail", socio.mail)
            put("fechaNacimiento", socio.fechaNacimiento); put("fichaMedica", if (socio.fichaMedica) 1 else 0)
        }
        val id = db.insert("Socios", null, valores)
        db.close()
        return id
    }

    fun insertarNoSocio(noSocio: NoSocio): Long {
        val db = writableDatabase
        val valores = ContentValues().apply {
            put("nombre", noSocio.nombre); put("apellido", noSocio.apellido); put("dni", noSocio.dni)
            put("telefono", noSocio.telefono); put("mail", noSocio.mail)
            put("fechaNacimiento", noSocio.fechaNacimiento); put("fichaMedica", if (noSocio.fichaMedica) 1 else 0)
        }
        val id = db.insert("NoSocios", null, valores)
        db.close()
        return id
    }

    fun obtenerSocios(): MutableList<Socio> {
        val lista = mutableListOf<Socio>()
        val db = readableDatabase
        val cursor = db.rawQuery("SELECT * FROM Socios", null)
        if (cursor.moveToFirst()) {
            do {
                // Empezamos en índice 1 porque el 0 es el 'id' autoincremental
                lista.add(Socio(cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getString(6), cursor.getInt(7) == 1))
            } while (cursor.moveToNext())
        }
        cursor.close(); db.close()
        return lista
    }

    fun obtenerNoSocios(): MutableList<NoSocio> {
        val lista = mutableListOf<NoSocio>()
        val db = readableDatabase
        val cursor = db.rawQuery("SELECT * FROM NoSocios", null)
        if (cursor.moveToFirst()) {
            do {
                lista.add(NoSocio(cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getString(6), cursor.getInt(7) == 1))
            } while (cursor.moveToNext())
        }
        cursor.close(); db.close()
        return lista
    }

    fun obtenerSocioPorDni(dni: String): Socio? {
        val db = readableDatabase
        val cursor = db.rawQuery("SELECT * FROM Socios WHERE dni = ?", arrayOf(dni))
        var socio: Socio? = null
        if (cursor.moveToFirst()) {
            socio = Socio(cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getString(6), cursor.getInt(7) == 1)
        }
        cursor.close(); db.close()
        return socio
    }

    fun obtenerNoSocioPorDni(dni: String): NoSocio? {
        val db = readableDatabase
        val cursor = db.rawQuery("SELECT * FROM NoSocios WHERE dni = ?", arrayOf(dni))
        var noSocio: NoSocio? = null
        if (cursor.moveToFirst()) {
            noSocio = NoSocio(cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getString(6), cursor.getInt(7) == 1)
        }
        cursor.close(); db.close()
        return noSocio
    }

    fun eliminarSocio(dni: String): Int {
        val db = writableDatabase
        val resultado = db.delete("Socios", "dni = ?", arrayOf(dni))
        db.close()
        return resultado
    }

    fun eliminarNoSocio(dni: String): Int {
        val db = writableDatabase
        val resultado = db.delete("NoSocios", "dni = ?", arrayOf(dni))
        db.close()
        return resultado
    }
}