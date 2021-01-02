package dev.mantasboro.trakt5.entities

import com.squareup.moshi.JsonClass
import org.threeten.bp.OffsetDateTime

/**
 * Type to use for parsing check in error response (call [dev.mantasboro.trakt5.TraktV2.checkForCheckinError]
 * with this class) if you get a 409 HTTP status code when checking in.
 */
@JsonClass(generateAdapter = true)
class CheckinError {
    /** Timestamp which is when the user can check in again.  */
    var expires_at: OffsetDateTime? = null
}
