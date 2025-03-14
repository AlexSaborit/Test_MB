package com.example.prova_mb_1

import android.app.Activity
import android.content.Context
import android.util.Log
import android.widget.Toast

object Debug {
    val debug = false // Set to true to enable debug mode
    
    /**
     * Displays a message as a Toast and logs an error.
     *
     * @param context The application context. Must be an instance of Activity to show a Toast.
     * @param mensaje The message to be displayed and logged.
     */
    fun showMessage(context: Context, mensaje: String) {
        (context as? Activity)?.runOnUiThread {
            if (debug) {
                Toast.makeText(context, mensaje, Toast.LENGTH_SHORT).show()
            }
            Log.e(context.toString(), mensaje)
        } ?: Log.e("showMessage", "Function Debug.showMessage: A Toast can not be shown: context " + context.toString() + " is not an instance of Activity")
    }

    /**
     * Displays the status of the debug mode (ENABLED/DISABLED) as a Toast and logs it.
     *
     * @param context The application context. Must be an instance of Activity to show a Toast.
     */
    fun status(context: Context) {
        (context as? Activity)?.runOnUiThread {
            if (debug) {
                Toast.makeText(context, "Debug mode ENABLED", Toast.LENGTH_SHORT).show()
                Log.e(context.toString(), "Debug mode ENABLED")
            } else {
                Log.e(context.toString(), "Debug mode DISABLED")
            }
        } ?: Log.e("showMessage", "Function Debug.status: A Toast can not be shown: context " + context.toString() + " is not an instance of Activity")
    }
}