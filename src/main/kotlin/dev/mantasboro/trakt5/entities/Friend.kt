package dev.mantasboro.trakt5.entities

import com.squareup.moshi.JsonClass
import org.threeten.bp.OffsetDateTime

@JsonClass(generateAdapter = true)
data class Friend(
    val friends_at: OffsetDateTime?,
    val user: User?
)
