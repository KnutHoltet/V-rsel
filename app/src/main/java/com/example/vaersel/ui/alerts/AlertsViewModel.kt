package com.example.vaersel.ui.alerts

import androidx.annotation.MainThread
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vaersel.data.alerts.AlertsRepository
import com.example.vaersel.data.location.UserLocation
import com.example.vaersel.usecases.alerts.GetAlertInformationUseCase
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AlertsViewModel(
        private val coords: UserLocation
) : ViewModel(){

    /* Alerts */
    private val _alertUiState = MutableStateFlow(AlertUiState.Success(listOf()))
    val alertUiState: StateFlow<AlertUiState> = _alertUiState.asStateFlow()


    init {
        initialize()
    }

    private var initializedCalled = false

    @MainThread
    private fun initialize() {
        if (initializedCalled) return
        initializedCalled = true

        viewModelScope.launch {

            while (coords.lat == 0.0){
                delay(100)
            }

            val alertInformationUseCase = GetAlertInformationUseCase(AlertsRepository())
            val alertInformation = alertInformationUseCase.invoke(coords.lat, coords.lon)

            _alertUiState.value = AlertUiState.Success(alertInformation)
        }
    }



}

sealed class AlertUiState {
    data class Success(
        val alert: List<HashMap<String, String>>
    ): AlertUiState()
}
