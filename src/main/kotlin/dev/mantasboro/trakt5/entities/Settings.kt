package dev.mantasboro.trakt5.entities

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Settings(
    var user: User? = null,
    var account: Account? = null,
    var connections: Connections? = null,
    var sharing_text: SharingText? = null,
)
