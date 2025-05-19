package com.stark.pedidos


import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

import com.stark.pedidos.ui.theme.PedidosTheme



enum class UserRole {
    ADMIN,
    PROVEEDOR,
    CLIENTE
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PedidosTheme {
                val navController = rememberNavController()

                NavHost(navController = navController, startDestination = Screen.Login.route) {
                    composable(Screen.Login.route) {
                        LoginScreenSimulator(navController)
                    }
                    composable(Screen.Admin.route) {
                        MainAdminScreen()
                    }
                    composable(Screen.Proveedor.route) {
                        MainClienteScreen()
                    }
                    composable(Screen.Cliente.route) {
                        MainProceedorScreen()
                    }
                }
            }
        }

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreenSimulator(navController: NavController) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var loginMessage by remember { mutableStateOf<String?>(null) }
    var isError by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Iniciar Sesión", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(24.dp))

        OutlinedTextField(
            value = username,
            onValueChange = { username = it },
            label = { Text("Usuario") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Contraseña") },
            modifier = Modifier.fillMaxWidth(),
            visualTransformation = PasswordVisualTransformation(),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(20.dp))

        loginMessage?.let { message ->
            Text(
                text = message,
                color = if (isError) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.bodyMedium
            )
            Spacer(modifier = Modifier.height(12.dp))
        }

        Button(
            onClick = {
                val detectedRole = getRoleForCredentials(username, password)
                if (detectedRole != null) {
                    loginMessage = "¡Login exitoso como ${detectedRole.name}!"
                    //isError = false
                        when (detectedRole) {
                            UserRole.ADMIN -> navController.navigate(Screen.Admin.route)
                            UserRole.PROVEEDOR -> navController.navigate(Screen.Proveedor.route)
                            UserRole.CLIENTE -> navController.navigate(Screen.Cliente.route)
                        }

                    when (detectedRole) {
                        UserRole.ADMIN -> Log.d("NAVEGACION", "Ir a pantalla ADMIN")
                        UserRole.PROVEEDOR -> Log.d("NAVEGACION", "Ir a pantalla PROVEEDOR")
                        UserRole.CLIENTE -> Log.d("NAVEGACION", "Ir a pantalla CLIENTE")
                    }
                } else {
                    loginMessage = "Error: Usuario o contraseña incorrecto."
                    isError = true
                    Log.w("LoginSim", "Fallo: Usuario: $username, Contraseña: $password")
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Ingresar")
        }
    }
}

// Detecta el rol según las credenciales
fun getRoleForCredentials(username: String, password: String): UserRole? {
    return when {
        username.equals("admin", ignoreCase = true) && password == "1234" -> UserRole.ADMIN
        username.equals("proveedor", ignoreCase = true) && password == "1234" -> UserRole.PROVEEDOR
        username.equals("cliente", ignoreCase = true) && password == "1234" -> UserRole.CLIENTE
        else -> null
    }
}

@Preview(showBackground = true, widthDp = 360, heightDp = 640)
@Composable
fun DefaultPreviewLoginSimulator() {
    PedidosTheme {
        val navController = rememberNavController()
        Surface(color = MaterialTheme.colorScheme.background) {
        }
    }
}

sealed class Screen(val route: String) {
    object Login : Screen("login")
    object Admin : Screen("admin")
    object Proveedor : Screen("proveedor")
    object Cliente : Screen("cliente")

}



