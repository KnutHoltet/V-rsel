package com.example.vaersel.model

import kotlinx.serialization.Serializable

@Serializable
data class AlertInfo(
    val features: List<Feature>
)

@Serializable
data class Feature(
    val properties: Property,
)


@Serializable
data class Property(
    val awareness_level: String,
    val consequences: String,
    val description: String,
    val event: String,
    val eventAwarenessName: String
)