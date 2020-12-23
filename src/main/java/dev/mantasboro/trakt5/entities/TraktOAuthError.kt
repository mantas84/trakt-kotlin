package dev.mantasboro.trakt5.entities

data class TraktOAuthError(
    var error: String? = null,
    var error_description: String? = null,
)