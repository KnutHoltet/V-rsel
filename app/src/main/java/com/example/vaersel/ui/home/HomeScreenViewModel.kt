package com.example.vaersel.ui.home

import android.util.Log
import androidx.annotation.MainThread
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vaersel.data.location.LocationRepository
import com.example.vaersel.data.location.UserLocation
import com.example.vaersel.data.weather.WeatherRepository
import com.example.vaersel.model.TimeSeriesEntry
import com.example.vaersel.usecases.location.GetLocationNameUseCase
import com.example.vaersel.usecases.weather.GetWeatherCurrentHourUseCase
import com.example.vaersel.usecases.weather.GetWeatherForSpecificDayUseCase
import com.example.vaersel.usecases.weather.GetWeatherForTheNextHoursUseCase
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class HomeScreenViewModel(
    private val coords: UserLocation,
    private val weatherRepository: WeatherRepository = WeatherRepository(),
    private val locationRepository: LocationRepository = LocationRepository()
): ViewModel() {


    /* Weather
    * For the next hours
    * */
    private val _weatherNextHoursUiState = MutableStateFlow(HourlyWeatherUiState.Success(emptyList(), "", null ))
    val weatherUiState: StateFlow<HourlyWeatherUiState> = _weatherNextHoursUiState.asStateFlow()


    /* Weather
    *  For the next days
    * */
    private val _weatherNextDaysUiState = MutableStateFlow(WeekForecastUiState.Success(emptyList())
    )
    val weatherNextDaysUiState: StateFlow<WeekForecastUiState> = _weatherNextDaysUiState.asStateFlow()



    /* Location */
    private val _locationUiState = MutableStateFlow(LocationWeatherUiState.Success(""))
    val locationUiState: StateFlow<LocationWeatherUiState> = _locationUiState.asStateFlow()


    /* Ready */
    private val _readyUiState = MutableStateFlow(false)
    val readyUiState: StateFlow<Boolean> = _readyUiState.asStateFlow()

    /* Snackbar */
    private val _showSnackbarState = MutableStateFlow(false)
    val showSnackbarState: StateFlow<Boolean> = _showSnackbarState.asStateFlow()


    init {
        initialize()
    }

    private var initializedCalled = false

    @MainThread
    fun initialize() {
        if(initializedCalled) return
        initializedCalled = true

        viewModelScope.launch {

            /* Loading */
            while (coords.lat == 0.0){
                delay(100)
            }

            /* LOG */
            Log.d("API-kall", "i HomeScreenViewModel")

            /* Fetch from repositories */
            weatherRepository.fetchWeatherData(coords.lat, coords.lon)
            locationRepository.fetchLocation(coords.lat, coords.lon)

            if (weatherRepository.getWeatherData() == null || locationRepository.getLocation() == null){

                return@launch
            }

            /* Weather
            * For the next hours
            *  */
            val weatherForTheNextHoursUseCase = GetWeatherForTheNextHoursUseCase(weatherRepository)
            val weatherNextHoursResult = weatherForTheNextHoursUseCase.invoke(12)

            /* Weather
            * Right now
            *  */
            val currentWeatherHourUseCase = GetWeatherCurrentHourUseCase(weatherRepository)
            val currentWeatherHour = currentWeatherHourUseCase.invoke()

            _weatherNextHoursUiState.value = HourlyWeatherUiState.Success(weatherNextHoursResult , currentWeatherHour, weatherNextHoursResult[0])

            /* Weather
            * For the next seven days
            * */
            val weatherForTheThreeNextDaysUseCase = GetWeatherForSpecificDayUseCase(weatherRepository)
            val weatherForTheThreeNextDays = mutableListOf<List<TimeSeriesEntry>>()
                for(i in 1..7) {
                    weatherForTheThreeNextDays.add(weatherForTheThreeNextDaysUseCase.invoke(i))
                }
            _weatherNextDaysUiState.value = WeekForecastUiState.Success(weatherForTheThreeNextDays)

            /* Location */
            val locationUseCase = GetLocationNameUseCase(locationRepository)
            val location = locationUseCase.invoke()
            _locationUiState.value = LocationWeatherUiState.Success(location)


            /* Ready */
            _readyUiState.value = true // ready signal so the screen can be showed
        }
    }


    fun refresh() {
        initializedCalled = false
        initialize()
    }

    // shows snackbar for 5 seconds, temporarily prevents new snackbar showing
    fun showSnackbar(){
        if(!showSnackbarState.value) {
            viewModelScope.launch {
                _showSnackbarState.value = true
                delay(5000)
                _showSnackbarState.value = false
            }
        }
    }
}

sealed class HourlyWeatherUiState {
    data class Success(val nextHours: List<TimeSeriesEntry>, val weatherNowString: String, val weatherNowEntry: TimeSeriesEntry?): HourlyWeatherUiState()

}

sealed class LocationWeatherUiState {
    data class Success(val location: String): LocationWeatherUiState()
}

sealed class WeekForecastUiState {
    data class Success(val sevenNextDays: List<List<TimeSeriesEntry>>): WeekForecastUiState()
}
