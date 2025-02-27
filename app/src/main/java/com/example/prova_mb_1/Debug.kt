package com.example.prova_mb_1

import android.app.Activity
import android.content.Context
import android.util.Log
import android.widget.Toast

object Debug {
    val debug = true
    fun showMessage(context: Context, mensaje: String) {
        (context as? Activity)?.runOnUiThread {
            if (debug) {
                Toast.makeText(context, mensaje, Toast.LENGTH_SHORT).show()
            }
            Log.e(context.toString(), mensaje)
        } ?: Log.e("showMessage", "The Toast can not be shown: context is not an instance of Activity")
    }
}