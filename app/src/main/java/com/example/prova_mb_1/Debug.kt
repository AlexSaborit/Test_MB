package com.example.prova_mb_1

import android.app.Activity
import android.content.Context
import android.util.Log
import android.widget.Toast

object Debug {
    val debug = true
    fun mostrarMensaje(context: Context, mensaje: String) {
        (context as? Activity)?.runOnUiThread {
            if (debug) {
                Toast.makeText(context, mensaje, Toast.LENGTH_SHORT).show()
            }
            Log.e(context.toString(), mensaje)
        } ?: Log.e("mostrarMensaje", "No se puede mostrar el Toast: context no es una instancia de Activity")
    }
}