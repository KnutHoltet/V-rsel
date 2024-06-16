package com.example.vaersel.data.alerts

import com.example.vaersel.model.AlertInfo

class AlertsRepository {
    private val alertsDataSource = AlertsDataSource()
    private var alertInfo : AlertInfo? = null

    suspend fun fetchAlertData(lat: Double, lon: Double) {
        alertInfo = alertsDataSource.fetchAlertData(lat, lon)
    }

    fun getAlertData(): AlertInfo?{
        return alertInfo
    }
}
