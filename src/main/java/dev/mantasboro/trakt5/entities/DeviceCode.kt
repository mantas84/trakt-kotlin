package dev.mantasboro.trakt5.entities

data class DeviceCode(
    val device_code: String? = null,
    val user_code: String? = null,
    val verification_url: String? = null,
    val expires_in: Int? = null,
    val interval: Int? = null,
)