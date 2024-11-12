package com.example.c2

import android.os.Bundle
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
import androidx.compose.runtime.*
import androidx.compose.foundation.lazy.items
import android.annotation.SuppressLint

class MainActivity : ComponentActivity() {
    @SuppressLint("CoroutineCreationDuringComposition")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            C2Theme {
                Surface {
                    val retrofit = RetrofitHelper.getInstance()
                    val users = remember { mutableStateOf<List<UserDataResponse>>(emptyList()) }

                    lifecycleScope.launch(Dispatchers.IO) {
                        val resultado = retrofit.getUsers()
                        withContext(Dispatchers.Main) {
                            if (resultado.isSuccessful) {
                                users.value = resultado.body() ?: emptyList()
                            }
                        }
                    }
                    UserList(users = users.value)
                }
            }
        }
    }
}

@Composable
fun UserList(users: List<UserDataResponse>) {
    LazyColumn(
        modifier = Modifier
            .padding(10.dp)
            .fillMaxSize()
    ) {
        item {
            Text(text = "Lista de Usuarios", modifier = Modifier.padding(vertical = 8.dp))
        }
        items(users) { user ->
            Text(
                text = "ID: ${user.id} / Nombre: ${user.name} / Username: ${user.username} / Email: ${user.email}",
                modifier = Modifier.padding(vertical = 8.dp)
            )
        }
    }
}

