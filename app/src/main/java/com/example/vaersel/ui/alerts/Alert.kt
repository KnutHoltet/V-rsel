package com.example.vaersel.ui.alerts

import AlertDialogPopUpContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.example.vaersel.data.alerts.icons.alertIcon


@Composable
fun Alert(
        viewModel:  AlertsViewModel
){
    /* Alerts */
    val alertUiState by viewModel.alertUiState.collectAsState()
    PrivateAlert(alertUiState)
}

@Composable
private fun PrivateAlert(alertUiState: AlertUiState) {

    if(alertUiState is AlertUiState.Success) {

        val openAlertDialog = remember { mutableStateOf(false) }

        if (alertUiState.alert.isNotEmpty()) {

            Box{
                val symbol = alertIcon(alertUiState.alert[0]["iconString"]!!)
                Image(painter = painterResource(id = symbol), contentDescription = "show alerts", modifier = Modifier.clickable { openAlertDialog.value = !openAlertDialog.value })
            }

            when {
                openAlertDialog.value -> {
                    AlertDialogPopUpContent(
                        onDismissRequest = { openAlertDialog.value = false },
                        onConfirmation = {
                            openAlertDialog.value = false
                        },
                        alerts = alertUiState.alert
                    )
                }
            }
        }
    }
}