package com.example.vaersel.usecases.weather

import com.example.vaersel.data.weather.WeatherRepository
import com.example.vaersel.model.TimeSeriesEntry
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.time.LocalDate

class GetWeatherForSpecificDayUseCase(
    private val weatherRepository: WeatherRepository,
    private val defaultDispatcher: CoroutineDispatcher = Dispatchers.Default
) {
    suspend operator fun invoke(numberOfDaysAway: Int): List<TimeSeriesEntry> =
        withContext(defaultDispatcher) {

            val weatherForSpecificDay: MutableList<TimeSeriesEntry> = mutableListOf()

            /* Code below finds date, a given amount of days away from today, takes leap year
            * into account aswell*/
            var chosenDay =
                (LocalDate.now().dayOfMonth + numberOfDaysAway) % when (LocalDate.now().monthValue) {
                    2 -> if(LocalDate.now().isLeapYear) {29} else {28}
                    4, 6, 9, 11 -> 30
                    else -> 31
                }
            if (chosenDay == 0) {
                chosenDay = LocalDate.now().dayOfMonth + numberOfDaysAway
            }

            /* Code below fetches weather data for the date found above(chosenDay) */
            weatherRepository.getWeatherData()?.properties?.timeseries?.forEach { hourlyWeatherData ->
                if (hourlyWeatherData.time.subSequence(8, 10).toString().toInt() == chosenDay) {
                    weatherForSpecificDay.add(hourlyWeatherData)
                }
            }
            weatherForSpecificDay
        }
}
