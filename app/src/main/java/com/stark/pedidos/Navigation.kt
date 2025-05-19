package com.stark.pedidos

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun Panatalla1(){
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Red)
            ) {
                Text( text = "pantalla 1", modifier = Modifier.align(Alignment.Center))
            }
}

@Composable
fun Panatalla2(){
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Red)
    ) {
        Text( text = "pantalla 2", modifier = Modifier.align(Alignment.Center))
    }
}

@Composable
fun Panatalla3(){
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Red)
    ) {
        Text( text = "pantalla 3", modifier = Modifier.align(Alignment.Center))
    }
}