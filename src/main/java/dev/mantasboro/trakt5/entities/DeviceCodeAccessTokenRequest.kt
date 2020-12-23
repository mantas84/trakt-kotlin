package dev.mantasboro.trakt5.entities

data class DeviceCodeAccessTokenRequest(
    val code: String?,
    val client_id: String?,
    val client_secret: String?,
)
