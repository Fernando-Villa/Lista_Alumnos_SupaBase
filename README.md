# SupaBase – Consumo de Supabase con Kotlin (Android)

App Android nativa (Kotlin + XML) que consulta la tabla `alumnos` de un proyecto Supabase
usando el SDK oficial **supabase-kt** (módulo **Postgrest**) y muestra la lista ordenada
alfabéticamente en un `TextView`.

## Características
- `SupabaseManager`: cliente Supabase centralizado (`object`).
- `Alumno`: modelo `@Serializable` con campos opcionales.
- Consulta asíncrona con `lifecycleScope.launch` + `decodeList<Alumno>()`.
- Indicador de carga (`ProgressBar`) y botón **Actualizar**.
- `SupabaseErrorHandler`: errores y respuestas vacías con `MaterialAlertDialogBuilder`.

## Estructura
```
app/src/main/java/com/example/supabase/
├── MainActivity.kt
├── data/SupabaseManager.kt
├── models/Alumno.kt
└── utils/SupabaseErrorHandler.kt
supabase/schema.sql   ← tabla, RLS y datos de ejemplo
```

## Ejecución
1. Crear un proyecto en https://supabase.com y ejecutar `supabase/schema.sql` en el SQL Editor.
2. En `SupabaseManager.kt` colocar la **URL** del proyecto y la **clave publicable** (`sb_publishable_...`).
3. Abrir el proyecto en Android Studio, sincronizar Gradle y ejecutar en un emulador/dispositivo (minSdk 26).

> Nunca publicar la clave `secret` / `service_role`. La clave publicable es pública y su acceso lo limitan las políticas RLS.

## Capturas de pantalla
<img width="268" height="607" alt="image" src="https://github.com/user-attachments/assets/6c28d30c-d38d-4dd6-af49-31d8028273d1" />

