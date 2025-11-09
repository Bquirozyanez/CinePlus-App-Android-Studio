package com.example.cineplus.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.cineplus.data.DarkModeDataStore
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class DarkModeViewModel(application: Application) : AndroidViewModel(application) {

    private val dataStore = DarkModeDataStore(application)

    //(true/false o null mientras carga)
    private val _isDarkMode = MutableStateFlow<Boolean?>(null)
    val isDarkMode: StateFlow<Boolean?> = _isDarkMode

    //muestra el mensaje de feedback
    private val _showMessage = MutableStateFlow(false)
    val showMessage: StateFlow<Boolean> = _showMessage

    init {
        loadState()
    }

    //carga el valor guardado de DataStore
    private fun loadState() {
        viewModelScope.launch {
            val saved = dataStore.darkModeFlow.first()
            _isDarkMode.value = saved
        }
    }

    //alternar y guardar el modo oscuro
    fun toggleDarkMode() {
        viewModelScope.launch {
            val current = _isDarkMode.value ?: false
            val new = !current

            // Guardar en DataStore
            dataStore.saveDarkMode(new)

            // Actualizar estado
            _isDarkMode.value = new

            // Mostrar feedback animado por 1.5 segundos
            _showMessage.value = true
            delay(1500)
            _showMessage.value = false
        }
    }
}
