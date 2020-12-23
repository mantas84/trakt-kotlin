package dev.mantasboro.trakt5.entities

data class Ratings(
    val rating: Double? = null,
    val votes: Int? = null,
    val distribution: Map<String, Int>? = null,
)