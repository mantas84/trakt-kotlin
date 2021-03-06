package dev.mantasboro.trakt5.services

import dev.mantasboro.trakt5.entities.Genre
import retrofit2.Call
import retrofit2.http.GET

interface Genres {
    /**
     * Get a list of all genres for shows, including names and slugs.
     */
    @GET("genres/movies")
    fun movies(): Call<List<Genre>>

    /**
     * Get a list of all genres for movies, including names and slugs.
     */
    @GET("genres/shows")
    fun shows(): Call<List<Genre>>
}
