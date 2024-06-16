package com.example.vaersel.usecases.weather

import com.example.vaersel.data.weather.WeatherRepository
import com.example.vaersel.model.TimeSeriesEntry
import com.example.vaersel.ui.home.components.currentDay.formatTimeString
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.time.LocalTime

class GetWeatherForTheNextHoursUseCase(
    private val weatherRepository: WeatherRepository,
    private val defaultDispatcher: CoroutineDispatcher = Dispatchers.Default
) {

    suspend operator fun invoke(amount: Int): List<TimeSeriesEntry> =
        withContext(defaultDispatcher) {

            val time = LocalTime.now()
            val getWeatherForSpecificDayUseCase = GetWeatherForSpecificDayUseCase(weatherRepository)

            /* API-fetch gives us data for a few previous hours, oneliner below filters them out
               */
            var todaysWeather: MutableList<TimeSeriesEntry> = getWeatherForSpecificDayUseCase.invoke(0).filter{
                time.minusHours(1).isBefore(LocalTime.parse(formatTimeString(it.time)))
            }.toMutableList()

            /* Oneliner above only takes current day into account. If it's late at night, it wont
            * add hours for next day and gives a small list and table. The code below adds weather data
            *  from next day until the list/table reaches a set size. */

            if (todaysWeather.size in 1..< amount) {
                todaysWeather.addAll(getWeatherForSpecificDayUseCase(1))
                todaysWeather = todaysWeather.subList(0, amount)
            }
            todaysWeather
        }
}