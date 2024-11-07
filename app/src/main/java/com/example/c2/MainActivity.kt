package com.example.c2

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.lifecycleScope
import com.example.c2.ui.theme.C2Theme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response

import androidx.compose.runtime.*

import androidx.compose.foundation.lazy.items



class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Instancia de Retrofit
        val retrofit = RetrofitHelper.getInstance()

        // Estado mutable para la lista de superhéroes
        var superheroesList by mutableStateOf<List<SuperHeroItemResponse>>(emptyList())

        // Llama a la API en un coroutine para obtener los datos
        lifecycleScope.launch(Dispatchers.IO) {
            val response: Response<SuperHeroDataResponse> = retrofit.getSuperheroes("Q")
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    // Actualiza la lista de superhéroes
                    superheroesList = response.body()?.results ?: emptyList()
                    Toast.makeText(this@MainActivity, "Datos cargados", Toast.LENGTH_SHORT).show()
                } else {
                    Log.e("Error", "Error al obtener los datos: ${response.errorBody()}")
                }
            }
        }

        // Configura el contenido de la actividad
        setContent {
            C2Theme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    // Llama a SuperheroList y le pasa la lista de superhéroes
                    SuperheroList(superheroesList)
                }
            }
        }
    }
}
@Composable
fun SuperheroList(superheroes: List<SuperHeroItemResponse>) {
    LazyColumn {
        items(superheroes) { superhero ->
            Text(
                text = superhero.name,
                modifier = Modifier.padding(16.dp) // Añadimos padding para que se vea bien
            )
        }
    }
}