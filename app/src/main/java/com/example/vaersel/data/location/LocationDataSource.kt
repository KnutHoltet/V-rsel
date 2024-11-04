package com.example.vaersel.data.location

//import com.example.vaersel.data.apikeys.proxykey
import com.example.vaersel.model.LocationInfo
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.request.get
import io.ktor.client.statement.HttpResponse
import io.ktor.serialization.gson.gson
import io.ktor.util.appendIfNameAbsent

class LocationDataSource {
    private val url = "https://api.met.no/weatherapi/airqualityforecast/0.1/?"
    private val client = HttpClient(CIO) {
        /*
        defaultRequest {
            url("https://gw-uio.intark.uh-it.no/in2000/")
            headers.appendIfNameAbsent("X-Gravitee-API-Key", proxykey)
        }
        */
        install(ContentNegotiation) {
            gson()
        }
    }
    suspend fun fetchLocationData(lat: Double, lon: Double): LocationInfo?{
        val newUrl = url + "lat=${lat}&lon=${lon}&show=metadata"

        val location: LocationInfo? = try {
            val httpResponse : HttpResponse = client.get(newUrl)
            if (httpResponse.status.value !in 200..299) {
                println(httpResponse.status.value)
                println(newUrl)
                return null
            }
            httpResponse.body<LocationInfo>()
        } catch(e : Exception){
            println(e)
            println("url: " + newUrl)
            return null
        }
        return location
    }
}
