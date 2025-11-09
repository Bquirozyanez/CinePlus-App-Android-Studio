package com.example.cineplus

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.view.WindowCompat
import com.example.cineplus.navigation.AppNavigation
import com.example.cineplus.ui.theme.CineplusTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            CineplusTheme {
                AppNavigation()
            }
        }
    }
}
