package com.example.myapplication

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import org.json.JSONArray
import org.json.JSONObject
import java.io.OutputStreamWriter
import java.net.HttpURLConnection
import java.net.URL

fun postJson(json: JSONObject): String? {
    val url = URL("https://3e77-149-156-124-2.ngrok-free.app/api/data")
    val connection = url.openConnection() as HttpURLConnection
    println(json.toString())

    try {

        connection.requestMethod = "POST"
        connection.setRequestProperty("Content-Type", "application/json; utf-8")
        connection.setRequestProperty("Accept", "application/json")
        connection.doOutput = true


        connection.outputStream.use { os ->
            OutputStreamWriter(os, "UTF-8").use { writer ->
                writer.write(json.toString())
                writer.flush()
            }
        }

        return connection.inputStream.bufferedReader().use { it.readText() }

    } catch (e: Exception) {
        e.printStackTrace()
        return null
    } finally {
        connection.disconnect()
    }
}


class DrawingView(context: Context, attrs: AttributeSet?) : View(context, attrs), CoroutineScope by MainScope() {

    private val paint = Paint()
    private val path = Path()
    private var cords = JSONObject().apply {
        put("x", JSONArray())
        put("y", JSONArray())
    }

    constructor(context: Context) : this(context, null)

    init {
        paint.color = Color.BLACK
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = 5f
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawPath(path, paint)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        val x = event.x
        val y = event.y

        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                path.moveTo(x, y)
                Log.d("DrawingView", "Touch down at ($x, $y)")
                return true
            }
            MotionEvent.ACTION_MOVE -> {
                path.lineTo(x, y)
                (cords.getJSONArray("x")).put(x)
                (cords.getJSONArray("y")).put(y)
                invalidate()
                return true
            }
            MotionEvent.ACTION_UP -> {
                path.reset()
                println(cords.toString())
                launch(Dispatchers.IO) {
                    postJson(cords)
                    cords = JSONObject().apply {
                        put("x", JSONArray())
                        put("y", JSONArray())
                    }
                }

                return false
            }
            else -> return false
        }
    }
}
