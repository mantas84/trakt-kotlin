package dev.mantasboro.trakt5.entities

data class Credits(
    val cast: List<CastMember>? = null,
    val crew: Crew? = null,
)
