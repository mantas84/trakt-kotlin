package dev.mantasboro.trakt5.services

import dev.mantasboro.trakt5.BaseTestCase
import dev.mantasboro.trakt5.TestData
import org.assertj.core.api.Assertions
import org.junit.Test
import java.io.IOException

class RecommendationsTest : BaseTestCase() {
    @Test
    @Throws(IOException::class)
    fun test_movies() {
        val movies = executeCall(trakt.recommendations().movies(null, null, null))
        Assertions.assertThat(movies).isNotEmpty
    }

    @Test
    @Throws(IOException::class)
    fun test_movies_pages() {
        val movies1 = executeCall(trakt.recommendations().movies(1, 5, null))
        Assertions.assertThat(movies1).isNotEmpty
        val movies2 = executeCall(trakt.recommendations().movies(2, 5, null))
        Assertions.assertThat(movies2).isNotEmpty.isNotEqualTo(movies1)
    }

    @Test
    @Throws(IOException::class)
    fun test_dismissMovie() {
        executeVoidCall(trakt.recommendations().dismissMovie(TestData.MOVIE_TRAKT_ID.toString()))
    }

    @Test
    @Throws(IOException::class)
    fun test_shows() {
        val shows = executeCall(trakt.recommendations().shows(null, null, null))
        Assertions.assertThat(shows).isNotEmpty
    }

    @Test
    @Throws(IOException::class)
    fun test_shows_pages() {
        val shows1 = executeCall(trakt.recommendations().shows(1, 5, null))
        Assertions.assertThat(shows1).isNotEmpty
        val shows2 = executeCall(trakt.recommendations().shows(2, 5, null))
        Assertions.assertThat(shows2).isNotEmpty.isNotEqualTo(shows1)
    }

    @Test
    @Throws(IOException::class)
    fun test_dismissShow() {
        executeVoidCall(trakt.recommendations().dismissShow(TestData.SHOW_TRAKT_ID.toString()))
    }
}