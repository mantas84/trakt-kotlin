package dev.mantasboro.trakt5.enums

import com.squareup.moshi.Json

enum class SortHow {

    @Json(name = "asc")
    ASC,

    @Json(name = "desc")
    DESC;
}
