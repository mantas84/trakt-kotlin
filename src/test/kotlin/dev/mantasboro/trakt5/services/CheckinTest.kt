package dev.mantasboro.trakt5.services

import dev.mantasboro.trakt5.BaseTestCase
import dev.mantasboro.trakt5.TestData
import dev.mantasboro.trakt5.entities.EpisodeCheckin
import dev.mantasboro.trakt5.entities.EpisodeCheckinResponse
import dev.mantasboro.trakt5.entities.EpisodeIds
import dev.mantasboro.trakt5.entities.MovieCheckin
import dev.mantasboro.trakt5.entities.MovieIds
import dev.mantasboro.trakt5.entities.ShareSettings
import dev.mantasboro.trakt5.entities.Show
import dev.mantasboro.trakt5.entities.SyncEpisode
import dev.mantasboro.trakt5.entities.SyncMovie
import dev.mantasboro.trakt5.entities.base.BaseCheckinData
import dev.mantasboro.trakt5.entities.base.BaseIdsData
import org.assertj.core.api.Assertions
import org.junit.Test
import org.threeten.bp.OffsetDateTime
import retrofit2.Call
import retrofit2.Response
import java.io.IOException


class CheckinTest : BaseTestCase() {

    @Throws(IOException::class)
    override fun <T> executeCall(call: Call<T>): T {
        val response = call.execute()
        if (!response.isSuccessful) {
            if (trakt.checkForCheckinError(response) != null) {
                Assertions.fail<Any>("Check-in still in progress, may be left over from failed test")
            } else if (response.code() == 401) {
                Assertions.fail<Any>(
                    "Authorization required, supply a valid OAuth access token: "
                            + response.code() + " " + response.message()
                )
            } else {
                Assertions.fail<Any>("Request failed: " + response.code() + " " + response.message())
            }
        }
        val body = response.body()
        return body ?: throw IllegalStateException("Body should not be null for successful response")
    }

    @Test
    @Throws(IOException::class)
    fun test_checkin_episode() {
        val checkin = buildEpisodeCheckin()
        val response = executeCall(trakt.checkin().checkin(checkin))

        // delete check-in first
        test_checkin_delete()
        assertEpisodeCheckin(response)
    }

    @Test
    @Throws(IOException::class)
    fun test_checkin_episode_without_ids() {
        val checkin = buildEpisodeCheckinWithoutIds()
        val response = executeCall(trakt.checkin().checkin(checkin))

        // delete check-in first
        test_checkin_delete()
        assertEpisodeCheckin(response)
    }

    private fun assertEpisodeCheckin(response: EpisodeCheckinResponse) {
        Assertions.assertThat(response).isNotNull
        // episode should be over in less than an hour
        Assertions.assertThat(response.watched_at!!.isBefore(OffsetDateTime.now().plusHours(1))).isTrue
        Assertions.assertThat(response.episode).isNotNull
        Assertions.assertThat(response.episode!!.ids).isNotNull
        Assertions.assertThat(response.episode!!.ids!!.trakt).isEqualTo(TestData.EPISODE_TRAKT_ID)
        Assertions.assertThat(response.episode!!.ids!!.tvdb).isEqualTo(TestData.EPISODE_TVDB_ID)
        Assertions.assertThat(response.show).isNotNull
    }

    @Test
    @Throws(IOException::class)
    fun test_checkin_movie() {
        val checkin = buildMovieCheckin()
        val response = executeCall(trakt.checkin().checkin(checkin))
        Assertions.assertThat(response).isNotNull
        // movie should be over in less than 3 hours
        Assertions.assertThat(response.watched_at!!.isBefore(OffsetDateTime.now().plusHours(3))).isTrue
        MoviesTest.assertTestMovie(response.movie!!)
        test_checkin_delete()
    }

    private fun buildMovieCheckin(): MovieCheckin {
        val shareSettings = ShareSettings()
        shareSettings.facebook = true
        val delegate = BaseCheckinData(
            app_version = APP_VERSION,
            app_date = APP_DATE,
            message = ("This is a toasty movie!"),
            sharing = shareSettings
        )
        val ids = MovieIds(data = BaseIdsData(trakt = TestData.MOVIE_TRAKT_ID))
        return MovieCheckin(
            data = delegate,
            movie = SyncMovie().id(ids),
        )
    }

    @Test
    @Throws(IOException::class)
    fun test_checkin_blocked() {
        val checkin = trakt.checkin()
        val episodeCheckin = buildEpisodeCheckin()
        val response = executeCall(checkin.checkin(episodeCheckin))
        val movieCheckin = buildMovieCheckin()
        val responseBlocked = checkin.checkin(movieCheckin).execute()
        if (responseBlocked.code() == 401) {
            Assertions.fail<Any>(
                "Authorization required, supply a valid OAuth access token: "
                        + responseBlocked.code() + " " + responseBlocked.message()
            )
        }
        if (responseBlocked.code() != 409) {
            Assertions.fail<Any>("Check-in was not blocked")
        }
        val checkinError = trakt.checkForCheckinError(responseBlocked)
        // episode check in should block until episode duration has passed
        Assertions.assertThat(checkinError).isNotNull
        Assertions.assertThat(checkinError!!.expires_at).isNotNull
        Assertions.assertThat(checkinError.expires_at!!.isBefore(OffsetDateTime.now().plusHours(1))).isTrue

        // clean the check in
        test_checkin_delete()
    }

    @Test
    @Throws(IOException::class)
    fun test_checkin_delete() {
        // tries to delete a check in even if none active
        val response: Response<*> = trakt.checkin().deleteActiveCheckin().execute()
        assertSuccessfulResponse(response)
        Assertions.assertThat(response.code()).isEqualTo(204)
    }

    private fun buildEpisodeCheckin(): EpisodeCheckin {
        val delegate = BaseCheckinData(
            app_version = APP_VERSION,
            app_date = APP_DATE,
            message = "This is a toasty episode!"
        )
        return EpisodeCheckin(
            data = delegate,
            episode = SyncEpisode(ids = EpisodeIds(tvdb = TestData.EPISODE_TVDB_ID)),
        )
    }

    private fun buildEpisodeCheckinWithoutIds(): EpisodeCheckin {
        val show = Show()
        show.title = TestData.SHOW_TITLE
        val delegate = BaseCheckinData(
            app_version = APP_VERSION,
            app_date = APP_DATE,
            message = "This is a toasty episode!"
        )

        return EpisodeCheckin(
            data = delegate,
            episode = SyncEpisode().season(TestData.EPISODE_SEASON).number(TestData.EPISODE_NUMBER),
            show = show,
        )
    }
}