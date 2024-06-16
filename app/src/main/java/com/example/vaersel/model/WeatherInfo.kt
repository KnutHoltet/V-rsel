package com.example.vaersel.model

import kotlinx.serialization.Serializable

@Serializable
data class WeatherInfo(
    val properties: ForecastProperties?
)

@Serializable
data class ForecastProperties(
    val timeseries: List<TimeSeriesEntry>
)
@Serializable
data class TimeSeriesEntry(
    val time: String,
    val data: DataEntry
)
@Serializable
data class DataEntry(
    val instant: InstantDetails,
    val next_1_hours: NextPeriodSummary,
    val next_6_hours: NextPeriodSummary
)

@Serializable
data class InstantDetails(
    val details: Details
)
@Serializable
data class Details(
    val air_temperature: Double,
    val wind_speed: Double
)

@Serializable
data class NextPeriodSummary (
    val summary: WeatherSummary,
    val details: WeatherDetails,
)

@Serializable
data class WeatherSummary(
    val symbol_code: String
)

@Serializable
data class WeatherDetails (
    val precipitation_amount: Double
)