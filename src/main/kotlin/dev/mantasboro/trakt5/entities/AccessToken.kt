package dev.mantasboro.trakt5.entities

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class AccessToken(
    val access_token: String?,
    val token_type: String?,
    val expires_in: Int?,
    val refresh_token: String?,
    val scope: String?,
    val created_at: Int?,
)
