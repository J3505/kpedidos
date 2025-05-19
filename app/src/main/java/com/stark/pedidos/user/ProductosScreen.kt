package com.stark.pedidos.user

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.produceState
import coil.compose.AsyncImage
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

data class Producto(
    val id: Int,
    val nombre: String,
    val descripcion: String?,
    val precio: Float,
    val stock: Int,
    val imagen: String?
)

interface ApiService {
    @GET("producto")
    suspend fun getProductos(): List<Producto>
}

object RetrofitClient {
    // cambiar IP según tu backend
    private const val BASE_URL = "http://10.0.2.2:3000/pedidos/api/"

    val apiService: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }
}
@Composable
fun ProductosScreen() {
    val productosState = produceState<List<Producto>?>(initialValue = null) {
        value = try {
            RetrofitClient.apiService.getProductos()
        } catch (e: Exception) {
            Log.e("ProductosScreen", "Error: ${e.message}")
            emptyList()

        }
    }

    val productos = productosState.value

    if (productos == null) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    } else {
        LazyColumn {
            items(productos) { producto ->
                Card(
                    modifier = Modifier
                        .padding(8.dp)
                        .fillMaxWidth(),
                    elevation = CardDefaults.cardElevation()
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(producto.nombre, style = MaterialTheme.typography.titleLarge)
                        producto.descripcion?.let {
                            Text(it, style = MaterialTheme.typography.bodyMedium)
                        }
                        Text("Precio: S/ ${producto.precio}", style = MaterialTheme.typography.bodyLarge)
                        Text("Stock: ${producto.stock}", style = MaterialTheme.typography.bodySmall)

                        producto.imagen?.let { imagenUrl ->
                            AsyncImage(
                                model = imagenUrl,
                                contentDescription = producto.nombre,
                                modifier = Modifier
                                    .height(180.dp)
                                    .fillMaxWidth()
                            )
                        }

                        Button(
                            onClick = { /* TODO: Agregar al carrito */ },
                            modifier = Modifier.padding(top = 8.dp)
                        ) {
                            Text("Agregar al carrito")
                        }
                    }
                }
            }
        }
    }
}

