package com.example.photoview

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.photoview.navigation.graphs.PhotoViewGraph
import com.example.photoview.ui.theme.PhotoViewTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PhotoViewTheme {
                PhotoViewGraph()
            }
        }
    }
}