package com.example.vaersel.data.weather

import com.example.vaersel.data.apikeys.proxykey
import com.example.vaersel.model.WeatherInfo
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.request.get
import io.ktor.client.statement.HttpResponse
import io.ktor.serialization.gson.gson
import io.ktor.util.appendIfNameAbsent

class WeatherDataSource {
    private val client = HttpClient(CIO) {
        defaultRequest {
            url("https://gw-uio.intark.uh-it.no/in2000/")
            headers.appendIfNameAbsent("X-Gravitee-API-Key", proxykey)
        }
        install(ContentNegotiation) {
            gson()

        }
    }
    suspend fun fetchWeatherData(lat: Double, lon: Double): WeatherInfo?{
        val url = "weatherapi/locationforecast/2.0/compact?lat=${lat}&lon=${lon}"
        val weather : WeatherInfo? = try {
            val httpResponse : HttpResponse = client.get(url)
            if (httpResponse.status.value !in 200..299) {
                println(httpResponse.status.value)
                println("url: $url")
                return null
            }
            httpResponse.body<WeatherInfo>()
        } catch(e : Exception){
            println(e)
            return null
        }
        return weather
    }
}



