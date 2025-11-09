package com.example.cineplus.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.cineplus.R
import com.example.cineplus.viewmodel.DarkModeViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    darkModeViewModel: DarkModeViewModel = viewModel()
) {
    val movies = listOf(
        Movie("Creed", "1h 56m", R.drawable.creed),
        Movie("Kimetsu no Yaiba", "2h 0m", R.drawable.kimetsu),
        Movie("Shrek", "1h 30m", R.drawable.shrek),
        Movie("4 Fantásticos", "2h 5m", R.drawable.fantastic4)
    )

    val isDarkModeState by darkModeViewModel.isDarkMode.collectAsState()
    val showMessage by darkModeViewModel.showMessage.collectAsState()

    if (isDarkModeState == null) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
        return
    }

    val isDarkMode = isDarkModeState ?: false

    // fondo oscurito
    val backgroundColor by animateColorAsState(
        targetValue = if (isDarkMode)
            androidx.compose.ui.graphics.Color(0xFF202123)
        else
            androidx.compose.ui.graphics.Color(0xFFF5F5F7),
        label = "backgroundColor"
    )


    val cardColor by animateColorAsState(
        targetValue = if (isDarkMode)
            androidx.compose.ui.graphics.Color(0xFF2D2F34)
        else
            androidx.compose.ui.graphics.Color.White,
        label = "cardColor"
    )

    val modeLabel by remember(isDarkMode) {
        derivedStateOf {
            if (isDarkMode) "Modo oscuro activado" else "Modo oscuro desactivado"
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Cartelera",
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontWeight = FontWeight.SemiBold
                        ),
                        color = if (isDarkMode)
                            androidx.compose.ui.graphics.Color.White
                        else
                            androidx.compose.ui.graphics.Color(0xFF222222),
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentWidth(Alignment.CenterHorizontally)
                    )
                },
                actions = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(end = 8.dp)
                    ) {
                        Text(
                            text = if (isDarkMode) "🌙" else "☀️",
                            style = MaterialTheme.typography.bodyMedium,
                            color = if (isDarkMode)
                                androidx.compose.ui.graphics.Color.White
                            else
                                androidx.compose.ui.graphics.Color(0xFF222222)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Switch(
                            checked = isDarkMode,
                            onCheckedChange = { darkModeViewModel.toggleDarkMode() }
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = backgroundColor,
                    titleContentColor = if (isDarkMode)
                        androidx.compose.ui.graphics.Color.White
                    else
                        androidx.compose.ui.graphics.Color(0xFF222222),
                    actionIconContentColor = if (isDarkMode)
                        androidx.compose.ui.graphics.Color.White
                    else
                        androidx.compose.ui.graphics.Color(0xFF222222)
                )
            )
        },
        containerColor = backgroundColor
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(backgroundColor)
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            //texto estado pal modo oscuro
            Text(
                text = modeLabel,
                style = MaterialTheme.typography.bodyMedium,
                color = if (isDarkMode)
                    androidx.compose.ui.graphics.Color(0xFFDDDDDD)
                else
                    androidx.compose.ui.graphics.Color(0xFF666666)
            )

            //feedback animado
            AnimatedVisibility(
                visible = showMessage,
                enter = slideInVertically { fullHeight -> fullHeight / 2 } + fadeIn(),
                exit = slideOutVertically { fullHeight -> fullHeight / 2 } + fadeOut()
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                        .background(
                            color = MaterialTheme.colorScheme.primary.copy(alpha = 0.18f),
                            shape = RoundedCornerShape(12.dp)
                        )
                        .padding(12.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Preferencia guardada ✅",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(movies) { movie ->
                    Cartelera(
                        movie = movie,
                        cardColor = cardColor,
                        isDarkMode = isDarkMode
                    )
                }
            }
        }
    }
}

data class Movie(
    val title: String,
    val duration: String,
    val posterRes: Int
)

@Composable
fun Cartelera(
    movie: Movie,
    cardColor: androidx.compose.ui.graphics.Color,
    isDarkMode: Boolean
) {
    Card(
        shape = RoundedCornerShape(20.dp),
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(
            containerColor = cardColor
        )
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .height(IntrinsicSize.Min),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Card(
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier
                    .width(120.dp)
                    .fillMaxHeight()
            ) {
                Image(
                    painter = painterResource(id = movie.posterRes),
                    contentDescription = "Póster de ${movie.title}",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
            }

            Spacer(Modifier.width(16.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = movie.title,
                    style = MaterialTheme.typography.titleLarge,
                    color = if (isDarkMode)
                        androidx.compose.ui.graphics.Color.White
                    else
                        androidx.compose.ui.graphics.Color(0xFF1C1B1F),
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(Modifier.height(8.dp))
                Text(
                    text = "Duración: ${movie.duration}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = if (isDarkMode)
                        androidx.compose.ui.graphics.Color(0xFFCCCCCC)
                    else
                        androidx.compose.ui.graphics.Color(0xFF444444)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    HomeScreen()
}
