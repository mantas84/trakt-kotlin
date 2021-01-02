package dev.mantasboro.trakt5.entities

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import org.threeten.bp.OffsetDateTime

@JsonClass(generateAdapter = true)
data class User(
    val username: String? = null,

    @Json(name = "private")
    val isPrivate: Boolean? = null,
    val name: String? = null,

    /** If a user is a regular VIP.  */
    val vip: Boolean? = null,

    /** If a user is an execute producer.  */
    var vip_ep: Boolean? = null,
    val ids: UserIds? = null,

    // full
    val joined_at: OffsetDateTime? = null,
    val location: String? = null,
    val about: String? = null,
    val gender: String? = null,
    val age: Int? = null,
    val images: Images? = null,

)

data class UserIds(var slug: String? = null)
