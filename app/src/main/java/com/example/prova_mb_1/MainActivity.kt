package com.example.prova_mb_1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.ViewCompat
import com.example.prova_mb_1.model.User
import com.example.prova_mb_1.repository.mock.MockUserRepository
import com.example.prova_mb_1.ui.UserDetailScreen
import com.example.prova_mb_1.ui.UserListScreen
import com.example.prova_mb_1.viewmodel.UserViewModel
import dagger.hilt.android.AndroidEntryPoint

/* Prova tècnica d'Android

Desarrollador de Android
Requisitos:

1. Interfaz de Usuario (UI)
✅	a. Implementar una pantalla principal que muestre una lista de usuarios con su nombre y correo electrónico.
✅	b. Al seleccionar un usuario, se debe mostrar una pantalla de detalles con información adicional: una descripción o frase (ocurrente o graciosa)
✅	c. Utilizar Jetpack Compose para la construcción de la UI.

2. Datos
✅  a. Obtener los datos desde una API simulada. Puedes utilizar https://jsonplaceholder.typicode.com/users como fuente de datos.
✅  b. Implementar un repositorio que maneje la obtención de datos y use un patrón de arquitectura (MWM recomendado).

3. Inyección de Dependencias
✅  a. Usar Hilt o Dagger para la inyección de dependencias en ViewModels y Repositorios.

4. Data Binding
✅	a. Usar Data Binding en la UI para enlazar datos a los elementos de la vista.

5. Testing
✅	a. Implementar pruebas unitarias con JUnit y Mockk para validar la lógica de negocio.
✅	b. Implementar pruebas de integración para verificar la comunicación entre capas.
	c. Implementar una prueba E2E con Espresso que valide la navegación entre la lista y los detalles.


Entrega:
	• Subir el código a un repositorio público de GitHub.
	• Incluir un archivo README.md explicando la arquitectura utilizada y cómo ejecutar las pruebas.
	• Compartir el enlace del repositorio para su evaluación.  */

@AndroidEntryPoint //Entry point for Hilt
class MainActivity : ComponentActivity() {

    private val userViewModel: UserViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            UserAppWithInsets(userViewModel)
            Debug.status(this)
        }
    }
}

@Composable
fun UserAppWithInsets(userViewModel: UserViewModel) {
    val context = LocalContext.current
    val insets = remember { mutableStateOf(WindowInsetsCompat.CONSUMED) }
    var selectedUser by remember { mutableStateOf<User?>(null) }

    val userListState by userViewModel.userListState.collectAsState() //obtain userlist from viewmodel

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

    @OptIn(ExperimentalMaterial3Api::class) // allow use of experimental API for TopAppBar
    Scaffold(topBar = {
        TopAppBar(title = { Text("Users List") })
    }) { paddingValues ->
        Column(modifier = Modifier.padding(paddingValues).padding(16.dp)) {
            if (selectedUser == null) {
                UserListScreen(
                    users = userListState.users,
                    onUserClick = { selectedUser = it })
            } else {
                UserDetailScreen(user = selectedUser, onBack = { selectedUser = null })
            }
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewUserAppWithInsets() {
    val mockUserViewModel = UserViewModel(MockUserRepository()) // Using a mock repository (dummy data)
    UserAppWithInsets(mockUserViewModel)
}

//TODO("DELETE THIS COMMENT WHEN DONE")
/* //passos conversió punt 5. Testing
	a. Implementar pruebas unitarias con JUnit y Mockk para validar la lógica de negocio.
	b. Implementar pruebas de integración para verificar la comunicación entre capas.
	c. Implementar una prueba E2E con Espresso que valide la navegación entre la lista y los detalles.

Pas 1: Proves unitàries
    Com que ja hem analitzat totes les classes, sabem en quines classes hem de fer proves unitàries
    i quins casos hem de provar. Anem a fer una llista de les classes amb les proves que farem,
     i així podrem anar marcant com a fetes les que ja tinguem:
    Classes amb proves unitàries:
✅  1. ApiService:
        Provar que getUsers() fa la crida a l'endpoint correcte (/users) i amb el mètode HTTP correcte (GET).
        Provar que si el servidor retorna una resposta amb èxit (codi 200), Retrofit transforma correctament
        les dades JSON rebudes a una llista d'objectes User.
        Provar que si el servidor retorna un error (codis 400, 500, etc.), Retrofit tracta l'error correctament.
        Provar que si la crida falla per algun altre motiu (problema de xarxa, etc.), Retrofit tracta l'error correctament.
✅  2. RetrofitModule:
        Provar que provideRetrofit() retorna un objecte Retrofit amb la URL base correcte i amb el GsonConverterFactory.
        Provar que provideApiService() retorna un objecte ApiService correctament configurat.
✅  3. UserDetailScreen (funció getUserField):
        Si user és null, ha de retornar "".
        Si menuItem és 1, ha de retornar user.username o "".
        Si menuItem és 2, ha de retornar user.phone o "".
        Si menuItem és 3, ha de retornar user.website o "".
        Si menuItem no és cap dels anteriors, ha de retornar "".
✅  4. MockUserRepository:
        Provar que, quan es crida a getUsers(), retorna una llista que:
        No és null.
        Conté 2 elements.
        El primer element té id = 1, name = "Mock User 1" i email = "mock1@example.com".
        El segon element té id = 2, name = "Mock User 2" i email = "mock2@example.com".
✅  5. UserRepositoryImpl:
        Provar que, quan es crida a getUsers(), es crida a apiService.getUsers().
        Provar que, si apiService.getUsers() retorna una resposta amb èxit (codi 200) i amb cos
        (una llista d'usuaris), getUsers() retorna aquesta llista d'usuaris.
        Provar que, si apiService.getUsers() retorna una resposta amb èxit (codi 200) però amb cos null,
        getUsers() retorna una llista buida.
        Provar que, si apiService.getUsers() retorna una resposta amb error (codi 400 o 500), getUsers() retorna una llista buida.
✅  6. UserViewModel:
        Provar que, quan es crea UserViewModel, es crida a loadUsers().
        Provar que loadUsers() crida a userRepository.getUsers().
        Provar que, si userRepository.getUsers() retorna una llista d'usuaris, users emet aquesta llista.
        Provar que, si userRepository.getUsers() retorna una llista buida, users emet una llista buida.
    7. Debug:
        Provar que, quan es crida a showMessage() amb debug = true i el context és una Activity, es mostra un Toast amb el missatge correcte.
        Provar que, quan es crida a showMessage() amb debug = false i el context és una Activity, no es mostra cap Toast.
        Provar que, quan es crida a showMessage(), es mostra el missatge correcte al logcat.
        Provar que, quan es crida a showMessage() amb un context que no és una Activity, no es mostra cap Toast.
        Provar que, quan es crida a showMessage() amb un context que no és una Activity, es mostra l'error al logcat.
        Provar que, quan es crida a enabled() amb debug = true i el context és una Activity, es mostra un Toast amb el missatge correcte.
        Provar que, quan es crida a enabled() amb debug = false i el context és una Activity, no es mostra cap Toast.
        Provar que, quan es crida a enabled(), es mostra el missatge correcte al logcat.
        Provar que, quan es crida a enabled() amb un context que no és una Activity, no es mostra cap Toast.
        Provar que, quan es crida a enabled() amb un context que no és una Activity, es mostra l'error al logcat. Pas 2: Proves d'integració
Pas 2: Un cop tinguem les proves unitàries, passarem a les proves d'integració. Aquestes proves verificaran la comunicació entre:
    UserRepositoryImpl i ApiService
    UserViewModel i UserRepositoryImpl
Pas 3: Proves E2E (Espresso)
Finalment, farem les proves E2E amb Espresso per a verificar la navegació entre la llista i els detalls.
Prioritat
Començarem per les classes més senzilles i que tenen menys dependències, i anirem avançant cap a les classes més complexes.
Proposta
Proposo que comencem a crear els tests de la classe UserDetailScreen (funció getUserField), ja que és la més senzilla i no té cap dependència.

*/