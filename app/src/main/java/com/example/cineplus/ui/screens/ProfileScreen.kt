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
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.cineplus.viewmodel.LoginViewModel
import kotlinx.coroutines.delay

@Composable
fun ProfileScreen(navController: NavController? = null) {
    LoginContent(navController)
}

@Composable
private fun LoginContent(navController: NavController?) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    val loginViewModel: LoginViewModel = viewModel()
    val isLoading by loginViewModel.isLoading.collectAsState()
    val isSuccess by loginViewModel.isSuccess.collectAsState()
    val error by loginViewModel.error.collectAsState()

    // Efecto: si login es exitoso, navegar tras un breve retraso
    LaunchedEffect(isSuccess) {
        if (isSuccess == true) {
            delay(1000)
            // Puedes cambiar "home" por la ruta que quieras
            navController?.navigate("home")
            loginViewModel.resetState()
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFE8F0FF)),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .clip(RoundedCornerShape(40.dp))
                .background(Color(0xFFF5F8FF))
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            // icono usuario
            Box(
                modifier = Modifier
                    .size(96.dp)
                    .clip(CircleShape)
                    .background(
                        Brush.verticalGradient(
                            listOf(
                                Color(0xFF4B8CFF),
                                Color(0xFF67D7C4)
                            )
                        )
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Filled.Person,
                    contentDescription = "Usuario",
                    tint = Color.White,
                    modifier = Modifier.size(56.dp)
                )
            }

            Spacer(Modifier.height(24.dp))

            // USUARIO
            Text(
                text = "USUARIO",
                style = MaterialTheme.typography.labelLarge,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF4353FF)
            )
            Spacer(Modifier.height(6.dp))

            TextField(
                value = username,
                onValueChange = { username = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                singleLine = true,
                shape = RoundedCornerShape(26.dp),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White,
                    disabledContainerColor = Color.White,
                    errorContainerColor = Color.White,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent,
                    errorIndicatorColor = Color.Transparent
                )
            )

            Spacer(Modifier.height(16.dp))

            // CONTRASEÑA
            Text(
                text = "CONTRASEÑA",
                style = MaterialTheme.typography.labelLarge,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF4353FF)
            )
            Spacer(Modifier.height(6.dp))

            TextField(
                value = password,
                onValueChange = { password = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                singleLine = true,
                visualTransformation = PasswordVisualTransformation(),
                shape = RoundedCornerShape(26.dp),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White,
                    disabledContainerColor = Color.White,
                    errorContainerColor = Color.White,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent,
                    errorIndicatorColor = Color.Transparent
                )
            )

            Spacer(Modifier.height(24.dp))

            //boton iniciar sesion
            Button(
                onClick = {
                    if (username.isNotBlank() && password.isNotBlank()) {
                        loginViewModel.login(username, password)
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                contentPadding = PaddingValues()
            ) {
                Box(
                    modifier = Modifier
                        .background(
                            Brush.horizontalGradient(
                                listOf(Color(0xFF4CAF50), Color(0xFF81C784))
                            ),
                            shape = RoundedCornerShape(28.dp)
                        )
                        .fillMaxWidth()
                        .height(56.dp),
                    contentAlignment = Alignment.Center
                ) {
                    when {
                        isLoading -> CircularProgressIndicator(color = Color.White)
                        isSuccess == true -> Text(
                            text = "Bienvenido 🎉",
                            color = Color.White,
                            style = MaterialTheme.typography.labelLarge,
                            fontWeight = FontWeight.Bold
                        )
                        else -> Text(
                            text = "INICIAR SESIÓN",
                            color = Color.White,
                            style = MaterialTheme.typography.labelLarge,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }

            //mostrar error si ocurre
            error?.let {
                Spacer(Modifier.height(8.dp))
                Text(
                    text = it,
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            Spacer(Modifier.height(12.dp))

            // registrarse (lleva a RegisterScreen)
            TextButton(onClick = { navController?.navigate("registro") }) {
                Text(
                    text = "¿No tienes cuenta? Registrarse",
                    color = Color(0xFF4353FF),
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    }
}

@Preview(widthDp = 360, heightDp = 800, showBackground = true)
@Composable
private fun ProfileScreenPreview() {
    val navController = rememberNavController()
    ProfileScreen(navController)
}
