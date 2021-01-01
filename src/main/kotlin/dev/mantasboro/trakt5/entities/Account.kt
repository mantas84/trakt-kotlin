package dev.mantasboro.trakt5.entities

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Account(
    val timezone: String? = null,
    val date_format: String? = null,
    val time_24hr: Boolean = false,
    val cover_image: String? = null,
)
