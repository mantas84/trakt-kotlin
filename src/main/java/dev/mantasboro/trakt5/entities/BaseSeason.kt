package dev.mantasboro.trakt5.entities

data class BaseSeason(
    val number: Int? = null,
    val episodes: List<BaseEpisode>? = null,

    /** progress  */
    val aired: Int? = null,

    /** progress  */
    val completed: Int? = null,
)