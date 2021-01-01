package dev.mantasboro.trakt5.enums

import com.squareup.moshi.Json

enum class ListPrivacy : TraktEnum {
    @Json(name = "private")
    PRIVATE,

    @Json(name = "friends")
    FRIENDS,

    @Json(name = "public")
    PUBLIC;
}
