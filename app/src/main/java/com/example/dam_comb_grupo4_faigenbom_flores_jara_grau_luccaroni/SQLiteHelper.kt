package com.example.dam_comb_grupo4_faigenbom_flores_jara_grau_luccaroni

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class SQLiteHelper(context: Context): SQLiteOpenHelper(context, "clubdeportivo.db", null, 1) {
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(
            """
            CREATE TABLE Socios(
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                nombre TEXT,
                apellido TEXT,
                dni TEXT,
                telefono TEXT,
                mail TEXT,
                fechaNacimiento TEXT,
                fichaMedica INTEGER
            )                
            """
        )
        db.execSQL(
            """
            CREATE TABLE NoSocios(
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                nombre TEXT,
                apellido TEXT,
                dni TEXT,
                telefono TEXT,
                mail TEXT,
                fechaNacimiento TEXT,
                fichaMedica INTEGER
            )                
            """
        )
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS Socios")
        db.execSQL("DROP TABLE IF EXISTS NoSocios")
        onCreate(db)
    }

    // --- MÉTODOS PARA SOCIOS ---
    fun insertarSocio(socio: Socio): Long {
        val db = writableDatabase
        val valores = ContentValues().apply {
            put("nombre", socio.nombre)
            put("apellido", socio.apellido)
            put("dni", socio.dni)
            put("telefono", socio.telefono)
            put("mail", socio.mail)
            put("fechaNacimiento", socio.fechaNacimiento)
            put("fichaMedica", if (socio.fichaMedica) 1 else 0)
        }
        val id = db.insert("Socios", null, valores)
        db.close()
        return id
    }

    fun obtenerSocios(): MutableList<Socio> {
        val listaSocios = mutableListOf<Socio>()
        val db = readableDatabase
        val cursor: Cursor = db.rawQuery("SELECT * FROM Socios", null)

        if (cursor.moveToFirst()) {
            do {
                val socio = Socio(
                    nombre = cursor.getString(cursor.getColumnIndexOrThrow("nombre")),
                    apellido = cursor.getString(cursor.getColumnIndexOrThrow("apellido")),
                    dni = cursor.getString(cursor.getColumnIndexOrThrow("dni")),
                    telefono = cursor.getString(cursor.getColumnIndexOrThrow("telefono")),
                    mail = cursor.getString(cursor.getColumnIndexOrThrow("mail")),
                    fechaNacimiento = cursor.getString(cursor.getColumnIndexOrThrow("fechaNacimiento")),
                    fichaMedica = cursor.getInt(cursor.getColumnIndexOrThrow("fichaMedica")) == 1
                )
                listaSocios.add(socio)
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return listaSocios
    }

    fun eliminarSocio(dni: String): Int {
        val db = writableDatabase
        val resultado = db.delete("Socios", "dni = ?", arrayOf(dni))
        db.close()
        return resultado
    }

    // --- MÉTODOS PARA NO SOCIOS ---
    fun insertarNoSocio(noSocio: NoSocio): Long {
        val db = writableDatabase
        val valores = ContentValues().apply {
            put("nombre", noSocio.nombre)
            put("apellido", noSocio.apellido)
            put("dni", noSocio.dni)
            put("telefono", noSocio.telefono)
            put("mail", noSocio.mail)
            put("fechaNacimiento", noSocio.fechaNacimiento)
            put("fichaMedica", if (noSocio.fichaMedica) 1 else 0)
        }
        val id = db.insert("NoSocios", null, valores)
        db.close()
        return id
    }

    fun obtenerNoSocios(): MutableList<NoSocio> {
        val listaNoSocios = mutableListOf<NoSocio>()
        val db = readableDatabase
        val cursor: Cursor = db.rawQuery("SELECT * FROM NoSocios", null)

        if (cursor.moveToFirst()) {
            do {
                val noSocio = NoSocio(
                    nombre = cursor.getString(cursor.getColumnIndexOrThrow("nombre")),
                    apellido = cursor.getString(cursor.getColumnIndexOrThrow("apellido")),
                    dni = cursor.getString(cursor.getColumnIndexOrThrow("dni")),
                    telefono = cursor.getString(cursor.getColumnIndexOrThrow("telefono")),
                    mail = cursor.getString(cursor.getColumnIndexOrThrow("mail")),
                    fechaNacimiento = cursor.getString(cursor.getColumnIndexOrThrow("fechaNacimiento")),
                    fichaMedica = cursor.getInt(cursor.getColumnIndexOrThrow("fichaMedica")) == 1
                )
                listaNoSocios.add(noSocio)
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()

        return listaNoSocios
    }

    fun eliminarNoSocio(dni: String): Int {
        val db = writableDatabase
        val resultado = db.delete("NoSocios", "dni = ?", arrayOf(dni))
        db.close()
        return resultado
    }
}
