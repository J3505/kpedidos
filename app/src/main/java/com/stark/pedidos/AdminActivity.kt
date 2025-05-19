package com.stark.pedidos



import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import com.stark.pedidos.ui.theme.PedidosTheme


import android.widget.Toast

import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.stark.pedidos.ui.theme.PedidosTheme
// com.stark.pedidos.AdminActivity.AdminScreen()

class AdminActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PedidosTheme {
                MainAdminScreen()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainAdminScreen() {
    val context = LocalContext.current
    var selectedIndex by remember { mutableStateOf(0) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Panel de Administración") },
                actions = {
                    IconButton(onClick = { /* TODO: Implementar acción */ }) {
                        Icon(Icons.Default.Search, contentDescription = "Buscar")
                    }
                    IconButton(onClick = { /* TODO: Implementar acción */ }) {
                        Icon(Icons.Default.Notifications, contentDescription = "Notificaciones")
                    }
                    IconButton(onClick = {
                        // Acción de salir, puedes cerrar la actividad o navegar al login
                        Toast.makeText(context, "Sesión cerrada", Toast.LENGTH_SHORT).show()
                        (context as? ComponentActivity)?.finish() // Cierra la pantalla actual
                    }) {
                        Icon(Icons.Default.ExitToApp, contentDescription = "Salir")
                    }
                }
            )
        },

        bottomBar = {
            NavigationBar {
                NavigationBarItem(
                    icon = { Icon(Icons.Default.Home, contentDescription = "Inicio") },
                    label = { Text("Inicio") },
                    selected = selectedIndex == 0,
                    onClick = { selectedIndex = 0 }
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Default.Person, contentDescription = "Usuarios") },
                    label = { Text("Usuarios") },
                    selected = selectedIndex == 1,
                    onClick = { selectedIndex = 1 }
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Default.Settings, contentDescription = "Configuración") },
                    label = { Text("Ajustes") },
                    selected = selectedIndex == 2,
                    onClick = { selectedIndex = 2 }
                )
            }
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentAlignment = Alignment.Center
        ) {
            Text("Bienvenido, Administrador", style = MaterialTheme.typography.headlineMedium)
        }
    }
}