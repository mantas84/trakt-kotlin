package dev.mantasboro.trakt5.services

import dev.mantasboro.trakt5.BaseTestCase
import dev.mantasboro.trakt5.TestData
import dev.mantasboro.trakt5.enums.Extended
import dev.mantasboro.trakt5.enums.IdType
import dev.mantasboro.trakt5.enums.Type
import org.assertj.core.api.Assertions
import org.junit.Test
import java.io.IOException

class SearchTest : BaseTestCase() {
    @Test
    @Throws(IOException::class)
    fun test_textQuery_show() {
        val results = executeCall(
            trakt.search().textQueryShow(
                "House",
                null, null, null, null, null, null, null, null, null,
                Extended.FULL, 1, DEFAULT_PAGE_SIZE
            )
        )
        Assertions.assertThat(results).isNotNull
        Assertions.assertThat(results).isNotEmpty
        for ((_, score, _, show) in results) {
            Assertions.assertThat(score).isPositive
            Assertions.assertThat(show).isNotNull
        }
    }

    @Test
    @Throws(IOException::class)
    fun test_textQuery_show_withYear() {
        val results = executeCall(
            trakt.search().textQueryShow(
                "Empire", "2015",
                null, null, null, null, null, null, null, null,
                Extended.FULL, 1, DEFAULT_PAGE_SIZE
            )
        )
        Assertions.assertThat(results).isNotNull
        Assertions.assertThat(results).isNotEmpty
        for ((_, score, _, show) in results) {
            Assertions.assertThat(score).isPositive
            Assertions.assertThat(show).isNotNull
        }
    }

    @Test
    @Throws(IOException::class)
    fun test_textQuery_movie() {
        val results = executeCall(
            trakt.search().textQueryMovie(
                "Tron",
                null, null, null, null, null, null, null,
                Extended.FULL, 1, DEFAULT_PAGE_SIZE
            )
        )
        Assertions.assertThat(results).isNotNull
        Assertions.assertThat(results).isNotEmpty
        for ((_, score, movie) in results) {
            Assertions.assertThat(score).isPositive
            Assertions.assertThat(movie).isNotNull
        }
    }

    @Test
    @Throws(IOException::class)
    fun test_textQuery_person() {
        val results = executeCall(
            trakt.search().textQuery(
                Type.PERSON, "Bryan Cranston",
                null, null, null, null, null, null,
                Extended.FULL, 1, DEFAULT_PAGE_SIZE
            )
        )
        Assertions.assertThat(results).isNotNull
        Assertions.assertThat(results).isNotEmpty
        for ((_, score, _, _, _, person) in results) {
            Assertions.assertThat(score).isPositive
            Assertions.assertThat(person).isNotNull
        }
    }

    @Test
    @Throws(IOException::class)
    fun test_idLookup() {
        var results = executeCall(
            trakt.search().idLookup(
                IdType.TVDB, TestData.SHOW_TVDB_ID.toString(), Type.SHOW,
                Extended.FULL, 1, DEFAULT_PAGE_SIZE
            )
        )
        Assertions.assertThat(results).isNotNull
        Assertions.assertThat(results).hasSize(1)
        results = executeCall(
            trakt.search().idLookup(
                IdType.TMDB, TestData.MOVIE_TMDB_ID.toString(), Type.MOVIE,
                null, 1, DEFAULT_PAGE_SIZE
            )
        )
        Assertions.assertThat(results).isNotNull
        Assertions.assertThat(results).hasSize(1)
    }
}