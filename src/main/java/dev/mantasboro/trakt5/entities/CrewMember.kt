package dev.mantasboro.trakt5.entities

data class CrewMember(
    val job: String? = null,
    val movie: Movie? = null,
    val show: Show? = null,
    val person: Person? = null,
)