package com.example.cineplus.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.cineplus.viewmodel.UsuarioViewModel
import com.example.cineplus.viewmodel.RegisterViewModel
import kotlinx.coroutines.delay

@Composable
fun RegisterScreen(
    navController: NavController,
    viewModel: UsuarioViewModel
) {
    val estado by viewModel.estado.collectAsState()

    //viewModel para conectarse con la API
    val registerViewModel: RegisterViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
    val isLoading by registerViewModel.isLoading.collectAsState()
    val isSuccess by registerViewModel.isSuccess.collectAsState()
    val error by registerViewModel.error.collectAsState()

    //si el registro fue exitoso, navegar tras un breve retraso
    LaunchedEffect(isSuccess) {
        if (isSuccess == true) {
            delay(1500)
            navController.navigate("resumen")
            registerViewModel.resetState()
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFEAF0FF))
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            // Icono superior con degradado
            Box(
                modifier = Modifier
                    .size(80.dp)
                    .clip(CircleShape)
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(Color(0xFF3F51B5), Color(0xFF81C784))
                        )
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = "Usuario",
                    tint = Color.White
                )
            }

            Text(
                text = "Crear cuenta",
                color = Color(0xFF3F51B5),
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold
            )

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(32.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    // Nombre
                    OutlinedTextField(
                        value = estado.nombre,
                        onValueChange = viewModel::onNombreChange,
                        label = { Text("Nombre", color = Color(0xFF3F51B5)) },
                        isError = estado.errores.nombre != null,
                        supportingText = {
                            estado.errores.nombre?.let {
                                Text(text = it, color = MaterialTheme.colorScheme.error)
                            }
                        },
                        modifier = Modifier.fillMaxWidth()
                    )

                    // Correo
                    OutlinedTextField(
                        value = estado.correo,
                        onValueChange = viewModel::onCorreoChange,
                        label = { Text("Correo electrónico", color = Color(0xFF3F51B5)) },
                        isError = estado.errores.correo != null,
                        supportingText = {
                            estado.errores.correo?.let {
                                Text(text = it, color = MaterialTheme.colorScheme.error)
                            }
                        },
                        modifier = Modifier.fillMaxWidth()
                    )

                    // Contraseña
                    OutlinedTextField(
                        value = estado.clave,
                        onValueChange = viewModel::onClaveChange,
                        label = { Text("Contraseña", color = Color(0xFF3F51B5)) },
                        visualTransformation = PasswordVisualTransformation(),
                        isError = estado.errores.clave != null,
                        supportingText = {
                            estado.errores.clave?.let {
                                Text(text = it, color = MaterialTheme.colorScheme.error)
                            }
                        },
                        modifier = Modifier.fillMaxWidth()
                    )

                    // Dirección
                    OutlinedTextField(
                        value = estado.direccion,
                        onValueChange = viewModel::onDireccionChange,
                        label = { Text("Dirección", color = Color(0xFF3F51B5)) },
                        isError = estado.errores.direccion != null,
                        supportingText = {
                            estado.errores.direccion?.let {
                                Text(text = it, color = MaterialTheme.colorScheme.error)
                            }
                        },
                        modifier = Modifier.fillMaxWidth()
                    )

                    //checkbox
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Checkbox(
                            checked = estado.aceptaTerminos,
                            onCheckedChange = viewModel::onAceptarTerminosChange
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Acepto los términos y condiciones",
                            color = Color(0xFF3F51B5)
                        )
                    }

                    // botomn Registrar (con conexion a la API)
                    Button(
                        onClick = {
                            if (viewModel.validarFormulario()) {
                                registerViewModel.register(
                                    email = estado.correo,
                                    password = estado.clave,
                                    name = estado.nombre
                                )
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(55.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                        contentPadding = PaddingValues()
                    ) {
                        Box(
                            modifier = Modifier
                                .background(
                                    brush = Brush.horizontalGradient(
                                        colors = listOf(
                                            Color(0xFF4CAF50),
                                            Color(0xFF81C784)
                                        )
                                    ),
                                    shape = RoundedCornerShape(50)
                                )
                                .fillMaxWidth()
                                .height(55.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            when {
                                isLoading -> CircularProgressIndicator(color = Color.White)
                                isSuccess == true -> Text(
                                    text = "Registro exitoso 🎉",
                                    color = Color.White,
                                    fontWeight = FontWeight.Bold
                                )
                                else -> Text(
                                    text = "Registrar",
                                    color = Color.White,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    }

                    //mostrar error si ocurre
                    error?.let {
                        Text(
                            text = it,
                            color = MaterialTheme.colorScheme.error,
                            style = MaterialTheme.typography.bodyMedium,
                            modifier = Modifier.padding(top = 8.dp)
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun RegisterScreenPreview() {
    val navController = rememberNavController()
    val fakeVm = UsuarioViewModel() // solo para preview
    RegisterScreen(navController = navController, viewModel = fakeVm)
}
