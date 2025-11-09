package com.example.cineplus.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.cineplus.viewmodel.UsuarioViewModel

@Composable
fun ResumenScreen(
    navController: NavController,
    viewModel: UsuarioViewModel
) {
    val estado by viewModel.estado.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFEAF0FF))
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = "Resumen del registro",
                color = Color(0xFF3F51B5),
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold
            )

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Text("Nombre: ${estado.nombre}", color = Color(0xFF3F51B5))
                    Text("Correo: ${estado.correo}", color = Color(0xFF3F51B5))
                    Text("Dirección: ${estado.direccion}", color = Color(0xFF3F51B5))
                    Text(
                        text = "Contraseña: ${"*".repeat(estado.clave.length)}",
                        color = Color(0xFF3F51B5)
                    )
                    Text(
                        text = "Términos: " +
                                if (estado.aceptaTerminos) "Aceptados" else "No aceptados",
                        color = if (estado.aceptaTerminos)
                            Color(0xFF4CAF50)
                        else
                            MaterialTheme.colorScheme.error,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }

            // boton para ir a la cartelera (HomeScreen)
            Button(
                onClick = {
                    navController.navigate("home")
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF3F51B5),
                    contentColor = Color.White
                ),
                shape = RoundedCornerShape(16.dp)
            ) {
                Text(
                    text = "Ir a la cartelera",
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Suppress("ViewModelConstructorInComposition")
@Composable
fun ResumenScreenPreview() {
    val fakeVm = UsuarioViewModel().apply {
        onNombreChange("Juan Pérez")
        onCorreoChange("juan@example.com")
        onDireccionChange("Av. Siempre Viva 123")
        onClaveChange("secreta123")
        onAceptarTerminosChange(true)
    }

    val fakeNavController = rememberNavController()

    ResumenScreen(
        navController = fakeNavController,
        viewModel = fakeVm
    )
}
