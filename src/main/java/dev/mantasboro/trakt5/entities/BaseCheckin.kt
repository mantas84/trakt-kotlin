package dev.mantasboro.trakt5.entities

abstract class BaseCheckin(
    /** Control sharing to any connected social networks.  */
    val sharing: ShareSettings?,

    /** Message used for sharing. If not sent, it will use the watching string in the user settings.  */
    val message: String?,

    /** Foursquare venue ID.  */
    val venue_id: String?,

    /** Foursquare venue name.  */
    val venue_name: String?,
    val app_version: String?,

    /** Build date of the app.  */
    val app_date: String?,
)