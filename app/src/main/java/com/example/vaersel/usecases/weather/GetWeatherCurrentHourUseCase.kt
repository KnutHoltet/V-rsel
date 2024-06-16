package com.example.vaersel.usecases.weather

import com.example.vaersel.data.weather.WeatherRepository
import com.example.vaersel.ui.home.components.currentDay.formatTimeString
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.time.LocalTime
import kotlin.math.roundToInt

class GetWeatherCurrentHourUseCase(
    private val weatherRepository: WeatherRepository,
    private val defaultDispatcher: CoroutineDispatcher = Dispatchers.Default
) {
    val time = LocalTime.now()
    suspend operator fun invoke(): String {
        var temp = ""

        /* Gets weather data for current hour, filters out everything before and after */
        withContext(defaultDispatcher) {
            val liste = weatherRepository.getWeatherData()?.properties?.timeseries?.filter {
                time.minusHours(1).isBefore(LocalTime.parse(formatTimeString(it.time)))
            }
            temp =  liste?.get(0)?.data?.instant?.details?.air_temperature?.roundToInt().toString()
        }
        return temp
    }
}