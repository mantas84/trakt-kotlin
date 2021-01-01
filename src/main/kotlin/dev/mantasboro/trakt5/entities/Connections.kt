package dev.mantasboro.trakt5.entities

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Connections(
    val facebook: Boolean = false,
    val twitter: Boolean = false,
    val tumblr: Boolean = false,
)
