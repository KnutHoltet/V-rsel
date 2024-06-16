package com.example.vaersel.data.location

import com.example.vaersel.model.LocationInfo

class
LocationRepository {
    private val locationDatasource = LocationDataSource()
    var location: LocationInfo? = null

    suspend fun fetchLocation(lat: Double, lon: Double) {
        location = locationDatasource.fetchLocationData(lat, lon)
    }

    fun getLocation(): String? {
        return location?.meta?.superlocation?.name
    }
}