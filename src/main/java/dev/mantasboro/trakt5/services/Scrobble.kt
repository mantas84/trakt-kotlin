package dev.mantasboro.trakt5.services

import dev.mantasboro.trakt5.entities.PlaybackResponse
import dev.mantasboro.trakt5.entities.ScrobbleProgress
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface Scrobble {
    /**
     * **OAuth Required**
     *
     *
     *  User starts a video
     */
    @POST("scrobble/start")
    fun startWatching(@Body prog: ScrobbleProgress): Call<PlaybackResponse>

    /**
     * **OAuth Required**
     *
     *
     *  User pauses a video
     */
    @POST("scrobble/pause")
    fun pauseWatching(@Body prog: ScrobbleProgress): Call<PlaybackResponse>

    /**
     * **OAuth Required**
     *
     *
     *  User stops a video
     */
    @POST("scrobble/stop")
    fun stopWatching(@Body prog: ScrobbleProgress): Call<PlaybackResponse>
}
