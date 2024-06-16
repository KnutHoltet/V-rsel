package com.example.vaersel.data.alerts

import com.example.vaersel.model.AlertInfo
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.client.statement.HttpResponse
import io.ktor.serialization.gson.gson

class AlertsDataSource {
    private val client = HttpClient(CIO) {
        install(ContentNegotiation) {
            gson()
        }
    }

    suspend fun fetchAlertData(lat:Double, lon:Double): AlertInfo? {
        val url = "https://api.met.no/weatherapi/metalerts/1.1/.json?lat=$lat&lon=$lon"
        val alertInfo : AlertInfo? = try {
            val httpResponse : HttpResponse = client.get(url)
            if(httpResponse.status.value !in 200 .. 299) {
                return null
            }
            httpResponse.body<AlertInfo>()

        } catch (e: Exception) {
            return null
        }
        return alertInfo
    }
}