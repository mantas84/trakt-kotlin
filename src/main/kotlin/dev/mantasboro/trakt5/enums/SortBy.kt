package dev.mantasboro.trakt5.enums

import com.squareup.moshi.Json

enum class SortBy {

    @Json(name = "rank")
    RANK,

    @Json(name = "added")
    ADDED,

    @Json(name = "title")
    TITLE,

    @Json(name = "released")
    RELEASED,

    @Json(name = "runtime")
    RUNTIME,

    @Json(name = "popularity")
    POPULARITY,

    @Json(name = "percentage")
    PERCENTAGE,

    @Json(name = "votes")
    VOTES,

    @Json(name = "my_rating")
    MY_RATING,

    @Json(name = "random")
    RANDOM;
}
