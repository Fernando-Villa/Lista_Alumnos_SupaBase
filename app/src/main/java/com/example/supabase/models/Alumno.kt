package com.example.supabase.models

import kotlinx.serialization.Serializable

/** Modelo que representa una fila de la tabla `alumnos` en Supabase. */
@Serializable
data class Alumno(
    val id: Long,
    val nombres: String,
    val paralelo: String? = null,
    val correo: String? = null,
    val telefono: String? = null,
    val foto: String? = null,
    val created_at: String? = null
)
