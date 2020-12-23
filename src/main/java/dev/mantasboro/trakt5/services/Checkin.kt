package dev.mantasboro.trakt5.services;

import dev.mantasboro.trakt5.entities.EpisodeCheckin;
import dev.mantasboro.trakt5.entities.EpisodeCheckinResponse;
import dev.mantasboro.trakt5.entities.MovieCheckin;
import dev.mantasboro.trakt5.entities.MovieCheckinResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.POST;

public interface Checkin {

    /**
     * <b>OAuth Required</b>
     *
     * <p> Check into an episode. This should be tied to a user action to manually indicate they are watching something.
     * The item will display as watching on the site, then automatically switch to watched status once the duration has
     * elapsed.
     */
    @POST("checkin")
    Call<EpisodeCheckinResponse> checkin(
            @Body EpisodeCheckin episodeCheckin
    );

    /**
     * <b>OAuth Required</b>
     *
     * <p> Check into a movie. This should be tied to a user action to manually indicate they are watching something.
     * The item will display as watching on the site, then automatically switch to watched status once the duration has
     * elapsed.
     */
    @POST("checkin")
    Call<MovieCheckinResponse> checkin(
            @Body MovieCheckin movieCheckin
    );

    /**
     * <b>OAuth Required</b>
     *
     * <p> Removes any active checkins, no need to provide a specific item.
     */
    @DELETE("checkin")
    Call<Void> deleteActiveCheckin();

}
