package dev.mantasboro.trakt5.entities

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SharingText(
    var watching: String? = null,
    var watched: String? = null,
)
