package com.example.myapplication

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize the DrawingView using the correct constructor
        val drawingView = DrawingView(this)

        // Set the content view to your custom view
        setContentView(drawingView)

        // Logging for debugging activity creation
        android.util.Log.d("MainActivity", "DrawingView set as content view")
    }
}
