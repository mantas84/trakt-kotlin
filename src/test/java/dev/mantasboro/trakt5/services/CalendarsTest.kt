package dev.mantasboro.trakt5.services

import dev.mantasboro.trakt5.BaseTestCase
import dev.mantasboro.trakt5.entities.CalendarMovieEntry
import dev.mantasboro.trakt5.entities.CalendarShowEntry
import org.assertj.core.api.Assertions
import org.junit.Test
import java.io.IOException

class CalendarsTest : BaseTestCase() {

    @Test
    @Throws(IOException::class)
    fun test_myShows() {
        val entries = executeCall(trakt.calendars().myShows(START_DATE_MY_SHOWS, TEST_DAYS))
        assertShowCalendar(entries)
    }

    @Test
    @Throws(IOException::class)
    fun test_myNewShows() {
        val response = executeCall(trakt.calendars().myNewShows(START_DATE_MY_SHOWS, TEST_DAYS))
        assertShowCalendar(response)
    }

    @Test
    @Throws(IOException::class)
    fun test_mySeasonPremieres() {
        val response = executeCall(trakt.calendars().mySeasonPremieres(START_DATE_MY_SHOWS, TEST_DAYS))
        assertShowCalendar(response)
    }

    @Test
    @Throws(IOException::class)
    fun test_myMovies() {
        val response = executeCall(trakt.calendars().myMovies(START_DATE_MY_MOVIES, DAYS_MOVIES))
        assertMovieCalendar(response)
    }

    @Test
    @Throws(IOException::class)
    fun test_shows() {
        // do unauthenticated call
        trakt.accessToken(null)
        val response = executeCall(trakt.calendars().shows(START_DATE_ALL, TEST_DAYS))
        assertShowCalendar(response)

        // restore auth
        trakt.accessToken(TEST_ACCESS_TOKEN)
    }

    @Test
    @Throws(IOException::class)
    fun test_newShows() {
        // do unauthenticated call
        trakt.accessToken(null)
        val response = executeCall(
            trakt.calendars().newShows(START_DATE_ALL, TEST_DAYS)
        )
        assertShowCalendar(response)

        // restore auth
        trakt.accessToken(TEST_ACCESS_TOKEN)
    }

    @Test
    @Throws(IOException::class)
    fun test_seasonPremieres() {
        // do unauthenticated call
        trakt.accessToken(null)
        val response = executeCall(
            trakt.calendars().seasonPremieres(START_DATE_ALL, TEST_DAYS)
        )
        assertShowCalendar(response)

        // restore auth
        trakt.accessToken(TEST_ACCESS_TOKEN)
    }

    @Test
    @Throws(IOException::class)
    fun test_movies() {
        // do unauthenticated call
        trakt.accessToken(null)
        val response = executeCall(trakt.calendars().movies(START_DATE_ALL, 30))
        assertMovieCalendar(response)

        // restore auth
        trakt.accessToken(TEST_ACCESS_TOKEN)
    }

    private fun assertShowCalendar(shows: List<CalendarShowEntry>) {
        for (entry in shows) {
            Assertions.assertThat(entry.first_aired).isNotNull
            Assertions.assertThat(entry.episode).isNotNull
            Assertions.assertThat(entry.show).isNotNull
        }
    }

    private fun assertMovieCalendar(movies: List<CalendarMovieEntry>) {
        for ((released, movie) in movies) {
            Assertions.assertThat(released).isNotNull
            Assertions.assertThat(movie).isNotNull
        }
    }

    companion object {
        // week which has show premiere (and therefore season premiere)
        private const val START_DATE_ALL = "2016-10-01"
        private const val START_DATE_MY_SHOWS = "2014-09-01"
        private const val TEST_DAYS = 7
        const val START_DATE_MY_MOVIES = "2014-05-01"
        const val DAYS_MOVIES = 30
    }
}