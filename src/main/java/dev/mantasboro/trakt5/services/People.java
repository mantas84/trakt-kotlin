package dev.mantasboro.trakt5.services;

import dev.mantasboro.trakt5.entities.Credits;
import dev.mantasboro.trakt5.entities.Person;
import dev.mantasboro.trakt5.enums.Extended;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface People {

    /**
     * Returns a single person's details.
     *
     * @param personId trakt ID, trakt slug, or IMDB ID Example: bryan-cranston.
     */
    @GET("people/{id}")
    Call<Person> summary(
            @Path("id") String personId,
            @Query("extended") Extended extended
    );

    @GET("people/{id}/movies")
    Call<Credits> movieCredits(
            @Path("id") String personId
    );

    @GET("people/{id}/shows")
    Call<Credits> showCredits(
            @Path("id") String personId
    );

}
