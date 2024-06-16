package com.example.vaersel.usecases.location

import com.example.vaersel.data.location.LocationRepository
import com.example.vaersel.usecases.location.constants.placeNames
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GetLocationNameUseCase(
    private val locationRepository: LocationRepository,
    private val defaultDispatcher: CoroutineDispatcher = Dispatchers.Default
) {

    suspend operator fun invoke(): String =
        withContext(defaultDispatcher) {

            val location = locationRepository.getLocation() ?: ""
            val result = placeNames(location)
            result
        }
}