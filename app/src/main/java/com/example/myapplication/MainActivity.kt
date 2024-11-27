package com.example.myapplication

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val drawingView = DrawingView(this)

        setContentView(drawingView)

        android.util.Log.d("MainActivity", "DrawingView set as content view")
    }
}
