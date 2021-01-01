package dev.mantasboro.trakt5.services

import dev.mantasboro.trakt5.BaseTestCase
import dev.mantasboro.trakt5.entities.Genre
import org.assertj.core.api.Assertions
import org.junit.Test
import java.io.IOException

class GenresTest : BaseTestCase() {
    @Test
    @Throws(IOException::class)
    fun test_genres_shows() {
        val genres = executeCall(trakt.genres().shows())
        assertGenres(genres)
    }

    @Test
    @Throws(IOException::class)
    fun test_genres_movies() {
        val genres = executeCall(trakt.genres().movies())
        assertGenres(genres)
    }

    private fun assertGenres(genres: List<Genre>) {
        for ((name, slug) in genres) {
            Assertions.assertThat(name).isNotEmpty
            Assertions.assertThat(slug).isNotEmpty
        }
    }
}