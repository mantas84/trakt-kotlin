package dev.mantasboro.trakt5.entities

class EpisodeIds(
    trakt: Int? = null,
    imdb: String? = null,
    tmdb: Int? = null,
    val tvdb: Int? = null,
    val tvrage: Int? = null,
) : BaseIds(trakt, imdb, tmdb)