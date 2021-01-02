package dev.mantasboro.trakt5.entities

import com.squareup.moshi.JsonClass
import org.threeten.bp.OffsetDateTime

@JsonClass(generateAdapter = true)
data class ListEntry(
    val id: Long? = null,
    val rank: Int? = null,
    val listed_at: OffsetDateTime? = null,
    val type: String? = null,
    val movie: Movie? = null,
    val show: Show? = null,
    val episode: Episode? = null,
    val person: Person? = null,
)
