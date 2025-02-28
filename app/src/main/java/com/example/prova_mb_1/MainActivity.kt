package com.example.prova_mb_1

/* Prova tècnica d'Android

Desarrollador de Android
Requisitos:

1. Interfaz de Usuario (UI)
	a. Implementar una pantalla principal que muestre una lista de usuarios con su nombre y correo electrónico.
	b. Al seleccionar un usuario, se debe mostrar una pantalla de detalles con información adicional: una descripción o frase (ocurrente o graciosa)
	c. Utilizar Jetpack Compose para la construcción de la UI.

2. Datos a. Obtener los datos desde una API simulada. Puedes utilizar https://jsonplaceholder.typicode.com/users como fuente de datos. b. Implementar un repositorio que maneje la obtención de datos y use un patrón de arquitectura (MWM recomendado).

3. Inyección de Dependencias
	a. Usar Hilt o Dagger para la inyección de dependencias en ViewModels y Repositorios.

4. Data Binding
	a. Usar Data Binding en la UI para enlazar datos a los elementos de la vista.

5. Testing
	a. Implementar pruebas unitarias con JUnit y Mockk para validar la lógica de negocio.
	b. Implementar pruebas de integración para verificar la comunicación entre capas.
	c. Implementar una prueba E2E con Espresso que valide la navegación entre la lista y los detalles.


Entrega:
	• Subir el código a un repositorio público de GitHub.
	• Incluir un archivo README.md explicando la arquitectura utilizada y cómo ejecutar las pruebas.
	• Compartir el enlace del repositorio para su evaluación.  */

/*//original
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        if (Debug.debug) {
            Debug.showMessage(this,"Debug mode enabled")
        }
    }
}
 */

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsControllerCompat

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            UserAppWithInsets()
        }
    }
}

@Composable
fun UserAppWithInsets() {
    val context = LocalContext.current
    val insets = remember { mutableStateOf(WindowInsetsCompat.CONSUMED) }

    DisposableEffect(Unit) {
        val window = (context as? ComponentActivity)?.window
        window?.let {
            ViewCompat.setOnApplyWindowInsetsListener(it.decorView) { _, newInsets ->
                insets.value = newInsets
                newInsets
            }
        }
        onDispose {}
    }

    @OptIn(ExperimentalMaterial3Api::class) //accept use of experimental API for TopAppBar

    Scaffold(topBar = {
        TopAppBar(title = { Text("Llista d'Usuaris") })
    }) { paddingValues ->
        Column(modifier = Modifier.padding(paddingValues).padding(16.dp)) {
            Text("Aplicació amb Jetpack Compose")
            Spacer(modifier = Modifier.height(16.dp))
            if (Debug.debug) {
                Debug.showMessage(context, "Debug mode enabled")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewUserAppWithInsets() {
    UserAppWithInsets()
}
