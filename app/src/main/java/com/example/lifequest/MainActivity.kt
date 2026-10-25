package com.example.lifequest

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge

import com.example.lifequest.navigation.NavGraph
import com.example.lifequest.ui.theme.AplicacionTallerTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AplicacionTallerTheme {
                NavGraph()
            }
        }
    }
}

