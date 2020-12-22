package dev.mantasboro.trakt5.entities;

import org.jetbrains.annotations.Nullable;
import org.threeten.bp.OffsetDateTime;
import retrofit2.Response;


/**
 * Type to use for parsing check in error response (call {@link dev.mantasboro.trakt5.TraktV2#checkForCheckinError(Response)}
 * with this class) if you get a 409 HTTP status code when checking in.
 */
public class CheckinError {

    /** Timestamp which is when the user can check in again. */
    @Nullable
    public OffsetDateTime expires_at;

}
