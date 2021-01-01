package dev.mantasboro.trakt5.entities

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class TraktOAuthError(
    var error: String? = null,
    var error_description: String? = null,
)
