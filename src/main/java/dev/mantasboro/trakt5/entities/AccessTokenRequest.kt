package dev.mantasboro.trakt5.entities

data class AccessTokenRequest(
    val code: String,
    val client_id: String,
    val client_secret: String,
    val redirect_uri: String,
) {
    val grant_type: String = "authorization_code"
}
