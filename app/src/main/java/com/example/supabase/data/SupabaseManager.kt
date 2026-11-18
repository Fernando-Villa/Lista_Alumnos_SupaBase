package com.example.supabase.data

import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest

/** Centraliza la creación y reutilización del cliente Supabase. */
object SupabaseManager {

    // La clave "publishable" es pública por diseño; el acceso real lo limitan las políticas RLS.
    val client = createSupabaseClient(
        supabaseUrl = "https://gsbclidxclklbuzsucoo.supabase.co",
        supabaseKey = "sb_publishable_4Lqb29EXY9rae2LOz6zbbw_v61Nn74o"
    ) {
        install(Postgrest)
    }
}
