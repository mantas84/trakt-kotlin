package dev.mantasboro.trakt5.services

import dev.mantasboro.trakt5.BaseTestCase
import dev.mantasboro.trakt5.TestData
import dev.mantasboro.trakt5.entities.Movie
import dev.mantasboro.trakt5.enums.Extended
import dev.mantasboro.trakt5.enums.Type
import org.assertj.core.api.Assertions
import org.junit.Test
import java.io.IOException
import java.lang.String

class MoviesTest : BaseTestCase() {
    @Test
    @Throws(IOException::class)
    fun test_popular() {
        val movies = executeCall(trakt.movies().popular(2, null, null))
        Assertions.assertThat(movies).isNotNull
        Assertions.assertThat(movies.size).isLessThanOrEqualTo(DEFAULT_PAGE_SIZE)
        for (movie in movies) {
            assertMovieNotNull(movie)
        }
    }

    @Test
    @Throws(IOException::class)
    fun test_trending() {
        val movies = executeCall(trakt.movies().trending(1, null, null))
        Assertions.assertThat(movies).isNotNull
        Assertions.assertThat(movies.size).isLessThanOrEqualTo(DEFAULT_PAGE_SIZE)
        for (movie in movies) {
            Assertions.assertThat(movie.watchers).isNotNull
            assertMovieNotNull(movie.movie)
        }
    }

    private fun assertMovieNotNull(movie: Movie) {
        Assertions.assertThat(movie.title).isNotEmpty
        Assertions.assertThat(movie.ids).isNotNull
        Assertions.assertThat(movie.ids!!.trakt).isNotNull
        Assertions.assertThat(movie.year).isNotNull
    }

    @Test
    @Throws(IOException::class)
    fun test_summary_slug() {
        val movie = executeCall(trakt.movies().summary(TestData.MOVIE_SLUG, Extended.FULL))
        assertTestMovie(movie)
    }

    @Test
    @Throws(IOException::class)
    fun test_summary_trakt_id() {
        val movie = executeCall(trakt.movies().summary(String.valueOf(TestData.MOVIE_TRAKT_ID), Extended.FULL))
        assertTestMovie(movie)
    }

    @Test
    @Throws(IOException::class)
    fun test_translations() {
        val translations = executeCall(trakt.movies().translations("batman-begins-2005"))
        Assertions.assertThat(translations).isNotNull
        for (translation in translations) {
            Assertions.assertThat(translation.language).isNotEmpty
        }
    }

    @Test
    @Throws(IOException::class)
    fun test_translation() {
        val translations = executeCall(
            trakt.movies().translation(
                "batman-begins-2005",
                "de"
            )
        )
        Assertions.assertThat(translations).isNotNull
        // we know that Batman Begins has a German translation, otherwise this test would fail
        Assertions.assertThat(translations).hasSize(1)
        Assertions.assertThat(translations[0].language).isEqualTo("de")
    }

    @Test
    @Throws(IOException::class)
    fun test_comments() {
        val comments = executeCall(
            trakt.movies().comments(
                TestData.MOVIE_SLUG, 1, null,
                null
            )
        )
        Assertions.assertThat(comments).isNotNull
        Assertions.assertThat(comments.size).isLessThanOrEqualTo(DEFAULT_PAGE_SIZE)
    }

    @Test
    @Throws(IOException::class)
    fun test_people() {
        val credits = executeCall(trakt.movies().people(TestData.MOVIE_SLUG))
        assertCast(credits, Type.PERSON)
        assertCrew(credits, Type.PERSON)
    }

    @Test
    @Throws(IOException::class)
    fun test_ratings() {
        val ratings = executeCall(trakt.movies().ratings(TestData.MOVIE_SLUG))
        assertRatings(ratings)
    }

    @Test
    @Throws(IOException::class)
    fun test_stats() {
        val stats = executeCall(trakt.movies().stats(TestData.MOVIE_SLUG))
        assertStats(stats)
    }

    companion object {
        fun assertTestMovie(movie: Movie) {
            Assertions.assertThat(movie).isNotNull
            Assertions.assertThat(movie.ids).isNotNull
            Assertions.assertThat(movie.title).isEqualTo(TestData.MOVIE_TITLE)
            Assertions.assertThat(movie.year).isEqualTo(TestData.MOVIE_YEAR)
            Assertions.assertThat(movie.ids!!.trakt).isEqualTo(TestData.MOVIE_TRAKT_ID)
            Assertions.assertThat(movie.ids!!.slug).isEqualTo(TestData.MOVIE_SLUG)
            Assertions.assertThat(movie.ids!!.imdb).isEqualTo(TestData.MOVIE_IMDB_ID)
            Assertions.assertThat(movie.ids!!.tmdb).isEqualTo(TestData.MOVIE_TMDB_ID)
        }
    }
}