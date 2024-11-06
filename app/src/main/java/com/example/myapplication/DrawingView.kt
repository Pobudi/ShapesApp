package com.example.myapplication

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View

// The main custom view class
class DrawingView(context: Context, attrs: AttributeSet?) : View(context, attrs) {

    private val paint = Paint()
    private val path = Path()

    // Primary constructor used for XML inflation or programmatic creation
    constructor(context: Context) : this(context, null)

    init {
        paint.color = Color.BLACK
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = 5f
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        // Draw the current path
        canvas.drawPath(path, paint)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        val x = event.x
        val y = event.y

        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                // Start a new path
                path.moveTo(x, y)
                Log.d("DrawingView", "Touch down at ($x, $y)")
                return true
            }
            MotionEvent.ACTION_MOVE -> {
                // Continue drawing the path
                path.lineTo(x, y)
//                Log.d("DrawingView", "Touch move at ($x, $y)")
                invalidate() // Redraw the view to update the drawing
                return true
            }
            MotionEvent.ACTION_UP -> {
                path.reset()
                return false
            }
            else -> return false
        }
    }
}
