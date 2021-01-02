package dev.mantasboro.trakt5.entities

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class AccessTokenRefreshRequest(
    val refresh_token: String,
    val client_id: String,
    val client_secret: String,
    val redirect_uri: String,
) {
    var grant_type: String = "refresh_token"
}
