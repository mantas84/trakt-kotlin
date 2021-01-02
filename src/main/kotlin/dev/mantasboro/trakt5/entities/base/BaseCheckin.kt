package dev.mantasboro.trakt5.entities.base

import com.squareup.moshi.JsonClass
import dev.mantasboro.trakt5.entities.ShareSettings

interface BaseCheckin {
    /** Control sharing to any connected social networks.  */
    var sharing: ShareSettings?

    /** Message used for sharing. If not sent, it will use the watching string in the user settings.  */
    var message: String?

    /** Foursquare venue ID.  */
    var venue_id: String?

    /** Foursquare venue name.  */
    var venue_name: String?
    var app_version: String?

    /** Build date of the app.  */
    var app_date: String?
}

@JsonClass(generateAdapter = true)
data class BaseCheckinData(
    override var sharing: ShareSettings? = null,
    override var message: String? = null,
    override var venue_id: String? = null,
    override var venue_name: String? = null,
    override var app_version: String? = null,
    override var app_date: String? = null,
) : BaseCheckin
