package com.example.vaersel.usecases.alerts

import com.example.vaersel.data.alerts.AlertsRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GetAlertInformationUseCase(
    private val alertsRepository: AlertsRepository,
    private val defaultDispatcher: CoroutineDispatcher = Dispatchers.Default
) {

    suspend operator fun invoke(lat: Double, lon: Double): List<HashMap<String, String>> =
        withContext(defaultDispatcher) {
            alertsRepository.fetchAlertData(lat, lon)
            val alertsInfo = alertsRepository.getAlertData()

            if (alertsInfo == null) {
                listOf()
            }
            else {
                val allAlerts: MutableList<HashMap<String, String>> = mutableListOf()

                alertsInfo.features.forEach {

                    val information: HashMap<String, String> = hashMapOf()
                    var color =
                        it.properties.awareness_level.split(";")[1].removePrefix(
                            " "
                        )
                    when (color) {
                        "yellow" -> color = "Gul"
                        "orange" -> color = "Oransje"
                        "red" -> color = "Rød"
                    }

                    val event = it.properties.event
                    val eventColor = it.properties.awareness_level.split(";")[1].removePrefix(" ")
                    val result = event + "_" + eventColor

                    information["color"] = color
                    information["description"] = it.properties.consequences
                    information["eventName"] = it.properties.eventAwarenessName
                    information["iconString"] = result

                    allAlerts.add(information)
                }
                allAlerts
            }
        }
}