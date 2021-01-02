package dev.mantasboro.trakt5.entities

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CrewMember(
    val job: String? = null,
    val movie: Movie? = null,
    val show: Show? = null,
    val person: Person? = null,
)
