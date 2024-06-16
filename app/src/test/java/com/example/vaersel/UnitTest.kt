package com.example.vaersel

import com.example.vaersel.data.alerts.AlertsDataSource
import com.example.vaersel.data.alerts.AlertsRepository
import com.example.vaersel.data.location.LocationRepository
import com.example.vaersel.data.weather.WeatherDataSource
import com.example.vaersel.data.weather.WeatherRepository
import com.example.vaersel.ui.home.animation.AnimationViewModel
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.junit.Assert.*


class UnitTest {


    // Weather Data Source
    @Test
    fun testWeatherDatasourceAPIFetchisNotEmpty() {
        runBlocking {
            // Arrange
            val dataSource = WeatherDataSource()

            // Act
            val weatherInfo = dataSource.fetchWeatherData(59.923, 10.731)

            // Assert
            println(weatherInfo)
            assertNotNull(weatherInfo)
        }
    }


    // Weather Repository
    @Test
    fun testWeatherRepositoryFetchisNotEmpty() {
        runBlocking {
            // Arrange
            val repository = WeatherRepository()

            // Act
            repository.fetchWeatherData(59.923859, 10.731417)
            val weatherInfo = repository.getWeatherData()

            // Assert
            assertNotNull(weatherInfo)
        }
    }


    // Alert Data Source
    @Test
    fun testAlertDatasourceAPIFetchisNotEmpty() {
        runBlocking {
            // Arrange
            val objekt = AlertsDataSource()

            // Act
            val alertInfo = objekt.fetchAlertData(59.944, 10.718)

            // Assert
            assertNotNull(alertInfo)
        }
    }


    // Alert Repository
    @Test
    fun testAlertsRepositoryFetchisNotEmpty(){
        runBlocking {
            // Arrange
            val repository = AlertsRepository()

            // Act
            repository.fetchAlertData(59.944, 10.718)
            val alertInfo = repository.getAlertData()

            // Assert
            assertNotNull(alertInfo)
        }
    }


    // Weather Data Serialization
    @Test
    fun testSerializeringForWeatherData() {
        runBlocking {
            // Arrange
            val objekt = WeatherDataSource()

            // Act
            val weatherTest = objekt.fetchWeatherData(59.911, 10.757)

            // Assert
            println(weatherTest)
            assertNotNull(weatherTest)
        }
    }


    // Location Data
    @Test
    fun testFetchLocationDataNotEmptyInDataSource() {
        runBlocking {
            // Arrange
            val repo = LocationRepository()

            // Act
            repo.fetchLocation(59.923859, 10.731417)
            val location = repo.getLocation()

            // Assert
            assertNotNull(location)
        }
    }


    // Location Repository
    @Test
    fun testSuperLocationIsRis() {
        runBlocking {
            // Arrange
            val locationRepository = LocationRepository()

            // Act
            locationRepository.fetchLocation(59.944, 10.718)
            val superLocation = locationRepository.getLocation()

            // Assert
            assertEquals(superLocation, "Ris")
        }
    }


    // Location Repository
    @Test
    fun testSuperLocationIsUllern() {
        runBlocking {
            // Arrange
            val locationRepository = LocationRepository()


            // Act
            locationRepository.fetchLocation(59.926, 10.655)
            val superLocation = locationRepository.getLocation()

            // Assert
            assertEquals(superLocation, "Ullern")
        }
    }



    // Animation View Model
    @Test
    fun testAnimationViewModelRainPopUpUiStateIsFalse() {
        runBlocking {
            // Arrange
            val animationViewModel = AnimationViewModel()

            // Act
            val rainPopUpState = animationViewModel.rainPopUpUiState.value

            // Assert
            assertEquals(rainPopUpState, false)
        }
    }


    @Test
    // Animation View Model
    fun testAnimationViewModelClothesPopUpUiStateIsFalse() {
       runBlocking {
           // Arrange
           val animationViewModel = AnimationViewModel()

           // Act
           val clothesPopUpState = animationViewModel.rainPopUpUiState.value

           // Assert
           assertEquals(clothesPopUpState, false)
       }
    }
}