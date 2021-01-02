package dev.mantasboro.trakt5.entities

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class DeviceCodeAccessTokenRequest(
    val code: String?,
    val client_id: String?,
    val client_secret: String?,
)
