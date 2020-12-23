package dev.mantasboro.trakt5.entities

data class CastMember(
    val character: String? = null,
    val movie: Movie? = null,
    val show: Show? = null,
    val person: Person? = null,
)