package com.example.vaersel.data.weather

import com.example.vaersel.model.WeatherInfo

class WeatherRepository {
    private val alertsDataSource = WeatherDataSource()
    private var weatherInfo : WeatherInfo? = null

    suspend fun fetchWeatherData(lat: Double, lon: Double) {
        weatherInfo = alertsDataSource.fetchWeatherData(lat, lon)
    }

    fun getWeatherData(): WeatherInfo? {
        return weatherInfo
    }
}
