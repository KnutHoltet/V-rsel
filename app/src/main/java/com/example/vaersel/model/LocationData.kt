package com.example.vaersel.model

import kotlinx.serialization.Serializable

@Serializable
data class LocationInfo(
    val meta: Meta
)

@Serializable
data class Meta(
    val superlocation: Superlocation,
)

@Serializable
data class Superlocation(
    val name: String,
)