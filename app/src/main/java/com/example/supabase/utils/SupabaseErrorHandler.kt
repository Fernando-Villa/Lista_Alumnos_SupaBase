package com.example.supabase.utils

import android.content.Context
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import io.github.jan.supabase.exceptions.HttpRequestException
import io.github.jan.supabase.exceptions.RestException
import kotlinx.serialization.SerializationException
import java.net.UnknownHostException

/** Clase reutilizable para mostrar errores y avisos de la consulta a Supabase. */
object SupabaseErrorHandler {

    /** Traduce la excepción a un mensaje entendible para el usuario. */
    fun obtenerMensaje(error: Throwable): String = when (error) {
        is UnknownHostException, is HttpRequestException ->
            "No se pudo conectar con el servidor. Verifique su conexión a Internet."
        is RestException ->
            "Error en la consulta a Supabase: ${error.error}. ${error.description}"
        is SerializationException ->
            "Los datos recibidos no coinciden con el modelo Alumno."
        else ->
            error.localizedMessage ?: "No se pudo completar la consulta."
    }

    fun show(context: Context, error: Throwable) {
        MaterialAlertDialogBuilder(context)
            .setTitle("Error")
            .setMessage(obtenerMensaje(error))
            .setPositiveButton("Aceptar", null)
            .show()
    }

    /** Aviso cuando la consulta fue correcta pero no devolvió registros. */
    fun showEmpty(context: Context) {
        MaterialAlertDialogBuilder(context)
            .setTitle("Sin resultados")
            .setMessage("La tabla 'alumnos' no contiene registros o la política RLS no permite leerlos.")
            .setPositiveButton("Aceptar", null)
            .show()
    }
}
