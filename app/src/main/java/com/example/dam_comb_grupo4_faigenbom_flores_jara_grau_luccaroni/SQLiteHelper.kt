package com.example.dam_comb_grupo4_faigenbom_flores_jara_grau_luccaroni

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter

class SQLiteHelper(context: Context): SQLiteOpenHelper(context, "clubdeportivo.db", null, 5) {

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL("CREATE TABLE Socios(id INTEGER PRIMARY KEY AUTOINCREMENT, nombre TEXT, apellido TEXT, dni TEXT, telefono TEXT, mail TEXT, fechaNacimiento TEXT, fichaMedica INTEGER, fechaVencimiento TEXT, activo INTEGER DEFAULT 1)")
        db.execSQL("CREATE TABLE NoSocios(id INTEGER PRIMARY KEY AUTOINCREMENT, nombre TEXT, apellido TEXT, dni TEXT, telefono TEXT, mail TEXT, fechaNacimiento TEXT, fichaMedica INTEGER)")
        db.execSQL("CREATE TABLE Pagos(id INTEGER PRIMARY KEY AUTOINCREMENT, dniSocio TEXT, fechaPago TEXT, formaPago TEXT, monto REAL)")
        db.execSQL("CREATE TABLE PagosNoSocios(id INTEGER PRIMARY KEY AUTOINCREMENT, dniNoSocio TEXT, fechaPago TEXT, actividad TEXT, monto REAL)")
        db.execSQL("CREATE TABLE Actividades(id INTEGER PRIMARY KEY AUTOINCREMENT, descripcion TEXT, hora TEXT, tipoPunto TEXT)")
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS Socios")
        db.execSQL("DROP TABLE IF EXISTS NoSocios")
        db.execSQL("DROP TABLE IF EXISTS Pagos")
        db.execSQL("DROP TABLE IF EXISTS PagosNoSocios")
        db.execSQL("DROP TABLE IF EXISTS Actividades")
        onCreate(db)
    }

    fun seedDatosDePruebaSiFaltanVencidos() {
        val db = readableDatabase
        val hoy = LocalDate.now().toString()
        val cursor = db.rawQuery("SELECT COUNT(*) FROM Socios WHERE activo = 1 AND fechaVencimiento <= ?", arrayOf(hoy))
        var cantidadVencidos = 0
        if (cursor.moveToFirst()) cantidadVencidos = cursor.getInt(0)
        cursor.close()
        db.close()

        if (cantidadVencidos == 0) {
            val dbWritable = writableDatabase
            val ayer = LocalDate.now().minusDays(1).toString()
            val semanaPasada = LocalDate.now().minusDays(7).toString()

            val sociosPrueba = listOf(
                ContentValues().apply {
                    put("nombre", "Juan"); put("apellido", "Pérez"); put("dni", "111");
                    put("telefono", "1122334455"); put("mail", "juan@ejemplo.com");
                    put("fechaNacimiento", "1990-01-01"); put("fichaMedica", 1);
                    put("fechaVencimiento", hoy); put("activo", 1)
                },
                ContentValues().apply {
                    put("nombre", "Ana"); put("apellido", "Gómez"); put("dni", "222");
                    put("telefono", "1199887766"); put("mail", "ana@ejemplo.com");
                    put("fechaNacimiento", "1995-05-15"); put("fichaMedica", 1);
                    put("fechaVencimiento", ayer); put("activo", 1)
                },
                ContentValues().apply {
                    put("nombre", "Luis"); put("apellido", "López"); put("dni", "333");
                    put("telefono", "1155443322"); put("mail", "luis@ejemplo.com");
                    put("fechaNacimiento", "1985-10-20"); put("fichaMedica", 0);
                    put("fechaVencimiento", semanaPasada); put("activo", 1)
                }
            )
            for (socio in sociosPrueba) { dbWritable.insert("Socios", null, socio) }
            dbWritable.close()
        }
    }

    private fun registrarActividad(db: SQLiteDatabase, descripcion: String, tipoPunto: String) {
        val horaActual = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm"))
        val valores = ContentValues().apply {
            put("descripcion", descripcion)
            put("hora", horaActual)
            put("tipoPunto", tipoPunto)
        }
        db.insert("Actividades", null, valores)
    }

    fun obtenerActividadesRecientes(): List<Triple<String, String, String>> {
        val lista = mutableListOf<Triple<String, String, String>>()
        val db = readableDatabase
        val cursor = db.rawQuery("SELECT descripcion, hora, tipoPunto FROM Actividades ORDER BY id DESC LIMIT 10", null)
        if (cursor.moveToFirst()) {
            do {
                lista.add(Triple(cursor.getString(0), cursor.getString(1), cursor.getString(2)))
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return lista
    }

    fun contarSociosActivos(): Int {
        val db = readableDatabase
        val cursor = db.rawQuery("SELECT COUNT(*) FROM Socios WHERE activo = 1", null)
        var cant = 0
        if (cursor.moveToFirst()) cant = cursor.getInt(0)
        cursor.close()
        db.close()
        return cant
    }

    fun contarNoSocios(): Int {
        val db = readableDatabase
        val cursor = db.rawQuery("SELECT COUNT(*) FROM NoSocios", null)
        var cant = 0
        if (cursor.moveToFirst()) cant = cursor.getInt(0)
        cursor.close()
        db.close()
        return cant
    }

    fun contarSociosVencenHoy(): Int {
        val db = readableDatabase
        val cursor = db.rawQuery("SELECT fechaVencimiento FROM Socios WHERE activo = 1", null)
        val hoy = LocalDate.now()
        var cant = 0
        if (cursor.moveToFirst()) {
            do {
                val fechaStr = cursor.getString(0)
                if (!fechaStr.isNullOrEmpty()) {
                    try {
                        val fechaVence = LocalDate.parse(fechaStr)
                        if (fechaVence.isBefore(hoy) || fechaVence.isEqual(hoy)) {
                            cant++
                        }
                    } catch (e: Exception) {}
                }
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return cant
    }

    fun insertarSocio(socio: Socio): Long {
        val db = writableDatabase
        val fechaVenceDefecto = LocalDate.now().plusDays(30).toString()
        val valores = ContentValues().apply {
            put("nombre", socio.nombre); put("apellido", socio.apellido); put("dni", socio.dni)
            put("telefono", socio.telefono); put("mail", socio.mail)
            put("fechaNacimiento", socio.fechaNacimiento)
            put("fichaMedica", if (socio.fichaMedica) 1 else 0)
            put("fechaVencimiento", fechaVenceDefecto)
            put("activo", 1)
        }
        val id = db.insert("Socios", null, valores)
        if (id != -1L) {
            registrarActividad(db, "Nuevo socio registrado · ${socio.nombre} ${socio.apellido.take(1)}.", "azul")
        }
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
        if (id != -1L) {
            registrarActividad(db, "Nuevo No Socio registrado · ${noSocio.nombre} ${noSocio.apellido.take(1)}.", "azul")
        }
        db.close()
        return id
    }

    fun actualizarEstadoSocio(dni: String, nuevoEstado: Boolean): Boolean {
        val db = writableDatabase
        val valores = ContentValues().apply { put("activo", if (nuevoEstado) 1 else 0) }
        val filasAfectadas = db.update("Socios", valores, "dni = ?", arrayOf(dni))
        if (filasAfectadas > 0) {
            val cursor = db.rawQuery("SELECT nombre, apellido FROM Socios WHERE dni = ?", arrayOf(dni))
            var nombreCompleto = "Socio"
            if (cursor.moveToFirst()) {
                nombreCompleto = "${cursor.getString(0)} ${cursor.getString(1).take(1)}."
            }
            cursor.close()
            val accion = if (nuevoEstado) "Socio activado" else "Socio inactivado"
            registrarActividad(db, "$accion · $nombreCompleto", if (nuevoEstado) "verde" else "rojo")
        }
        db.close()
        return filasAfectadas > 0
    }

    fun editarSocio(dniOriginal: String, nuevoNombre: String, nuevoApellido: String, nuevoTelefono: String, nuevoMail: String, nuevaFechaNac: String): Boolean {
        val db = writableDatabase
        val valores = ContentValues().apply {
            put("nombre", nuevoNombre); put("apellido", nuevoApellido)
            put("telefono", nuevoTelefono); put("mail", nuevoMail); put("fechaNacimiento", nuevaFechaNac)
        }
        val filasAfectadas = db.update("Socios", valores, "dni = ?", arrayOf(dniOriginal))
        if (filasAfectadas > 0) {
            registrarActividad(db, "Edición de socio · $nuevoNombre ${nuevoApellido.take(1)}.", "azul")
        }
        db.close()
        return filasAfectadas > 0
    }

    fun editarNoSocio(dniOriginal: String, nuevoNombre: String, nuevoApellido: String, nuevoTelefono: String, nuevoMail: String, nuevaFechaNac: String): Boolean {
        val db = writableDatabase
        val valores = ContentValues().apply {
            put("nombre", nuevoNombre); put("apellido", nuevoApellido)
            put("telefono", nuevoTelefono); put("mail", nuevoMail); put("fechaNacimiento", nuevaFechaNac)
        }
        val filasAfectadas = db.update("NoSocios", valores, "dni = ?", arrayOf(dniOriginal))
        if (filasAfectadas > 0) {
            registrarActividad(db, "Edición de No Socio · $nuevoNombre ${nuevoApellido.take(1)}.", "azul")
        }
        db.close()
        return filasAfectadas > 0
    }

    fun registrarPagoYActualizarVencimiento(dni: String, formaPago: String, monto: Double): Boolean {
        val db = writableDatabase
        db.beginTransaction()
        return try {
            val valoresPago = ContentValues().apply {
                put("dniSocio", dni); put("fechaPago", LocalDate.now().toString()); put("formaPago", formaPago); put("monto", monto)
            }
            db.insert("Pagos", null, valoresPago)
            val nuevaFechaVencimiento = LocalDate.now().plusDays(30).toString()
            val valoresSocio = ContentValues().apply { put("fechaVencimiento", nuevaFechaVencimiento) }
            db.update("Socios", valoresSocio, "dni = ?", arrayOf(dni))

            val cursor = db.rawQuery("SELECT nombre, apellido FROM Socios WHERE dni = ?", arrayOf(dni))
            var nombreCompleto = "Socio"
            if (cursor.moveToFirst()) {
                nombreCompleto = "${cursor.getString(0)} ${cursor.getString(1).take(1)}."
            }
            cursor.close()
            registrarActividad(db, "Cuota cobrada · $nombreCompleto", "verde")

            db.setTransactionSuccessful()
            true
        } catch (e: Exception) { false } finally { db.endTransaction(); db.close() }
    }
    fun registrarPagoNoSocio(dni: String, actividad: String, monto: Double): Boolean {
        val db = writableDatabase
        val valores = ContentValues().apply {
            put("dniNoSocio", dni)
            put("fechaPago", LocalDate.now().toString())
            put("actividad", actividad)
            put("monto", monto)
        }
        val id = db.insert("PagosNoSocios", null, valores)
        if (id != -1L) {
            val cursor = db.rawQuery("SELECT nombre, apellido FROM NoSocios WHERE dni = ?", arrayOf(dni))
            var nombreCompleto = "No Socio"
            if (cursor.moveToFirst()) {
                nombreCompleto = "${cursor.getString(0)} ${cursor.getString(1).take(1)}."
            }
            cursor.close()
            registrarActividad(db, "Pago actividad · $nombreCompleto · $actividad", "marron")
        }
        db.close()
        return id != -1L
    }
    fun eliminarSocio(dni: String): Int {
        val db = writableDatabase
        val cursor = db.rawQuery("SELECT nombre, apellido FROM Socios WHERE dni = ?", arrayOf(dni))
        var nombreSocio = "Socio"
        if (cursor.moveToFirst()) {
            nombreSocio = "${cursor.getString(0)} ${cursor.getString(1).take(1)}."
        }
        cursor.close()

        val resultado = db.delete("Socios", "dni = ?", arrayOf(dni))
        if (resultado > 0) {
            registrarActividad(db, "Eliminación de socio · $nombreSocio", "rojo")
        }
        db.close()
        return resultado
    }

    fun eliminarNoSocio(dni: String): Int {
        val db = writableDatabase
        val cursor = db.rawQuery("SELECT nombre, apellido FROM NoSocios WHERE dni = ?", arrayOf(dni))
        var nombreNoSocio = "No Socio"
        if (cursor.moveToFirst()) {
            nombreNoSocio = "${cursor.getString(0)} ${cursor.getString(1).take(1)}."
        }
        cursor.close()

        val resultado = db.delete("NoSocios", "dni = ?", arrayOf(dni))
        if (resultado > 0) {
            registrarActividad(db, "Eliminación de No Socio · $nombreNoSocio", "rojo")
        }
        db.close()
        return resultado
    }

    fun esSocioActivo(dni: String): Boolean {
        val db = readableDatabase
        val cursor = db.rawQuery("SELECT activo FROM Socios WHERE dni = ?", arrayOf(dni))
        var activo = true
        if (cursor.moveToFirst()) { activo = cursor.getInt(0) == 1 }
        cursor.close(); db.close()
        return activo
    }

    fun obtenerSocioPorDni(dni: String): Socio? {
        val db = readableDatabase
        val cursor = db.rawQuery("SELECT nombre, apellido, dni, telefono, mail, fechaNacimiento, fichaMedica FROM Socios WHERE dni = ?", arrayOf(dni))
        var socio: Socio? = null

        if (cursor.moveToFirst()) {
            fun getStringSafe(index: Int): String = cursor.getString(index) ?: ""

            socio = Socio(
                nombre = getStringSafe(0),
                apellido = getStringSafe(1),
                dni = getStringSafe(2),
                telefono = getStringSafe(3),
                mail = getStringSafe(4),
                fechaNacimiento = getStringSafe(5),
                fichaMedica = cursor.getInt(6) == 1 // Si es 1, es true
            )
        }
        cursor.close()
        db.close()
        return socio
    }

    fun obtenerNoSocioPorDni(dni: String): NoSocio? {
        val db = readableDatabase
        val cursor = db.rawQuery("SELECT * FROM NoSocios WHERE dni = ?", arrayOf(dni))
        var noSocio: NoSocio? = null
        if (cursor.moveToFirst()) { noSocio = NoSocio(cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getString(6), cursor.getInt(7) == 1) }
        cursor.close(); db.close()
        return noSocio
    }
    fun obtenerNoSociosCompleto(): MutableList<WrapperMiembro> {
        val lista = mutableListOf<WrapperMiembro>()
        val db = readableDatabase
        val cursor = db.rawQuery("SELECT nombre, apellido, dni FROM NoSocios", null)
        if (cursor.moveToFirst()) {
            do {
                lista.add(
                    WrapperMiembro(
                        nombre = cursor.getString(0),
                        apellido = cursor.getString(1),
                        dni = cursor.getString(2),
                        esSocio = false,
                        deudorVencido = false,
                        fechaVencimiento = null
                    )
                )
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return lista
    }
    fun obtenerSociosCompleto(): MutableList<WrapperMiembro> {
        val lista = mutableListOf<WrapperMiembro>()
        val db = readableDatabase
        val cursor = db.rawQuery("SELECT nombre, apellido, dni, fechaVencimiento, activo FROM Socios", null)
        val hoy = LocalDate.now()
        if (cursor.moveToFirst()) {
            do {
                val nombre = cursor.getString(0); val apellido = cursor.getString(1); val dni = cursor.getString(2)
                val fechaVencimientoStr = cursor.getString(3); val esActivoNum = cursor.getInt(4)
                var estaVencidoHoy = false
                if (esActivoNum == 1 && !fechaVencimientoStr.isNullOrEmpty()) {
                    try {
                        val fechaVence = LocalDate.parse(fechaVencimientoStr)
                        estaVencidoHoy = fechaVence.isBefore(hoy) || fechaVence.isEqual(hoy)
                    } catch (e: Exception) { estaVencidoHoy = false }
                }
                lista.add(WrapperMiembro(nombre, apellido, dni, true, estaVencidoHoy, fechaVencimientoStr))
            } while (cursor.moveToNext())
        }
        cursor.close(); db.close()
        return lista
    }
    fun obtenerUltimoPagoNoSocio(dni: String): Pair<String, String> {
        val db = readableDatabase
        val query = "SELECT fechaPago, actividad FROM PagosNoSocios WHERE dniNoSocio = ? ORDER BY id DESC LIMIT 1"
        val cursor = db.rawQuery(query, arrayOf(dni))
        var fecha = "-"
        var actividad = "Ninguna"
        if (cursor.moveToFirst()) {
            fecha = cursor.getString(0)
            actividad = cursor.getString(1)
        }
        cursor.close()
        db.close()
        return Pair(fecha, actividad)
    }
}