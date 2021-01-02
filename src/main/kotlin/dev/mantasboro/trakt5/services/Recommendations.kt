package dev.mantasboro.trakt5.services

import dev.mantasboro.trakt5.entities.Movie
import dev.mantasboro.trakt5.entities.Show
import dev.mantasboro.trakt5.enums.Extended
import retrofit2.Call
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface Recommendations {
    /**
     * **OAuth Required**
     *
     *
     *  Personalized movie recommendations for a user. Results returned with the top recommendation first.
     *
     * @param page Number of page of results to be returned. If `null` defaults to 1.
     * @param limit Number of results to return per page. If `null` defaults to 10.
     */
    @GET("recommendations/movies")
    fun movies(
        @Query("page") page: Int?,
        @Query("limit") limit: Int?,
        @Query(value = "extended", encoded = true) extended: Extended?
    ): Call<List<Movie>>

    /**
     * **OAuth Required**
     *
     *
     *  Dismiss a movie from getting recommended anymore.
     *
     * @param movieId trakt ID, trakt slug, or IMDB ID. Example: "tron-legacy-2010".
     */
    @DELETE("recommendations/movies/{id}")
    fun dismissMovie(@Path("id") movieId: String): Call<Void>

    /**
     * **OAuth Required**
     *
     *
     *  Personalized show recommendations for a user. Results returned with the top recommendation first.
     *
     * @param page Number of page of results to be returned. If `null` defaults to 1.
     * @param limit Number of results to return per page. If `null` defaults to 10.
     */
    @GET("recommendations/shows")
    fun shows(
        @Query("page") page: Int?,
        @Query("limit") limit: Int?,
        @Query(value = "extended", encoded = true) extended: Extended?
    ): Call<List<Show>>

    /**
     * **OAuth Required**
     *
     *
     *  Dismiss a show from getting recommended anymore.
     *
     * @param showId trakt ID, trakt slug, or IMDB ID. Example: 922.
     */
    @DELETE("recommendations/shows/{id}")
    fun dismissShow(@Path("id") showId: String): Call<Void>
}
