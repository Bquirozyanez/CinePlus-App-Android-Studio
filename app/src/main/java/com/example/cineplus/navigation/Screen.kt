package com.example.cineplus.navigation

// las rutas
sealed class Screen(val route: String) {

    data object HomeScreen : Screen("home_page")

    data object ProfileScreen : Screen("profile_page")

    data object RegisterScreen : Screen("register_page")


}