package com.example.cineplus.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.cineplus.ui.screens.HomeScreen
import com.example.cineplus.ui.screens.ProfileScreen
import com.example.cineplus.ui.screens.RegisterScreen
import com.example.cineplus.ui.screens.ResumenScreen
import com.example.cineplus.viewmodel.UsuarioViewModel

@Composable
fun AppNavigation() {

    val navController = rememberNavController()

    //viewModel compartido para formulario de registro / resumen
    val usuarioViewModel: UsuarioViewModel = viewModel()

    NavHost(
        navController = navController,
        startDestination = "profile"   // aca la app parte en el login
    ) {
        // LOGIN
        composable(route = "profile") {
            ProfileScreen(navController = navController)
        }

        // REGISTRO
        composable(route = "registro") {
            RegisterScreen(
                navController = navController,
                viewModel = usuarioViewModel
            )
        }

        // RESUMEN
        composable(route = "resumen") {
            ResumenScreen(
                navController = navController,
                viewModel = usuarioViewModel
            )
        }

        // HOME (cartelera)
        composable(route = "home") {
            HomeScreen()
        }
    }
}
