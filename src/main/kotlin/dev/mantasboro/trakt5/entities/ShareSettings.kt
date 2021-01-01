package dev.mantasboro.trakt5.entities

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ShareSettings(
    var facebook: Boolean? = null,
    var twitter: Boolean? = null,
    var tumblr: Boolean? = null,
)
