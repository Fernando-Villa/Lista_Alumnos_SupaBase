package com.example.supabase

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.example.supabase.data.SupabaseManager
import com.example.supabase.models.Alumno
import com.example.supabase.utils.SupabaseErrorHandler
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.postgrest.query.Order
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var txtAlumnos: TextView
    private lateinit var progressBar: ProgressBar
    private lateinit var btnActualizar: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        txtAlumnos = findViewById(R.id.txtAlumnos)
        progressBar = findViewById(R.id.progressBar)
        btnActualizar = findViewById(R.id.btnActualizar)

        btnActualizar.setOnClickListener { consultarAlumnos() }

        consultarAlumnos()
    }

    private fun consultarAlumnos() {
        lifecycleScope.launch {
            mostrarCarga(true)
            try {
                val alumnos = SupabaseManager.client
                    .from("alumnos")
                    .select {
                        order("nombres", Order.ASCENDING)
                    }
                    .decodeList<Alumno>()

                if (alumnos.isEmpty()) {
                    txtAlumnos.text = getString(R.string.sin_registros)
                    SupabaseErrorHandler.showEmpty(this@MainActivity)
                } else {
                    txtAlumnos.text = formatearAlumnos(alumnos)
                }
            } catch (e: CancellationException) {
                throw e // no tragarse la cancelación de la corrutina
            } catch (e: Exception) {
                txtAlumnos.text = getString(R.string.error_carga)
                SupabaseErrorHandler.show(this@MainActivity, e)
            } finally {
                mostrarCarga(false)
            }
        }
    }

    private fun formatearAlumnos(alumnos: List<Alumno>): String =
        alumnos.mapIndexed { i, a ->
            """
            ${i + 1}. ${a.nombres}
                Paralelo: ${a.paralelo ?: "—"}
                Correo: ${a.correo ?: "—"}
                Teléfono: ${a.telefono ?: "—"}
            """.trimIndent()
        }.joinToString("\n\n")

    private fun mostrarCarga(cargando: Boolean) {
        progressBar.visibility = if (cargando) View.VISIBLE else View.GONE
        btnActualizar.isEnabled = !cargando
    }
}
