package dev.mantasboro.trakt5.services

import dev.mantasboro.trakt5.BaseTestCase
import dev.mantasboro.trakt5.entities.*
import dev.mantasboro.trakt5.entities.base.BaseIdsData
import dev.mantasboro.trakt5.entities.base.GenericProgressData
import org.assertj.core.api.Assertions
import org.junit.Test
import java.io.IOException

class ScrobbleTest : BaseTestCase() {
    private fun genEpisodeProgress(progress: Double): ScrobbleProgress {
        // Use a different episode than for other tests to avoid conflicts.
        val episode = SyncEpisode(ids = EpisodeIds(tvdb = 4647887))/* Agents of Shield S01E02 */
        val delegate = GenericProgressData(episode = episode, progress = progress)
        return ScrobbleProgress(data = delegate, app_version = APP_VERSION, app_date = APP_DATE)
    }

    private fun genMovieProgress(progress: Double): ScrobbleProgress {
        // Use a different movie than for other tests to avoid conflicts.
        val movie = SyncMovie().id(MovieIds(data = BaseIdsData(tmdb = 333339)))/* Ready Player One */
        val delegate = GenericProgressData(movie = movie, progress = progress)
        return ScrobbleProgress(data = delegate, app_version = APP_VERSION, app_date = APP_DATE)
    }

    @Test
    @Throws(IOException::class, InterruptedException::class)
    fun scrobble_episode() {
        var playbackResponse = executeCall(trakt.scrobble().startWatching(genEpisodeProgress(20.0)))
        Assertions.assertThat(playbackResponse.action).isEqualTo("start")

        // Give the server some time to process the request.
        Thread.sleep(1500)
        playbackResponse = executeCall(trakt.scrobble().pauseWatching(genEpisodeProgress(50.0)))
        Assertions.assertThat(playbackResponse.action).isEqualTo("pause")

        // Give the server some time to process the request.
        Thread.sleep(1500)
        playbackResponse = executeCall(trakt.scrobble().stopWatching(genEpisodeProgress(75.0)))
        // Above 80% progress action is "scrobble", below "pause".
        // Pause to avoid blocking the next test until the episode runtime has passed.
        Assertions.assertThat(playbackResponse.action).isEqualTo("pause")
    }

    @Test
    @Throws(IOException::class, InterruptedException::class)
    fun scrobble_movie() {
        var playbackResponse = executeCall(trakt.scrobble().startWatching(genMovieProgress(20.0)))
        Assertions.assertThat(playbackResponse.action).isEqualTo("start")

        // Give the server some time to process the request.
        Thread.sleep(1500)
        playbackResponse = executeCall(trakt.scrobble().pauseWatching(genMovieProgress(50.0)))
        Assertions.assertThat(playbackResponse.action).isEqualTo("pause")

        // Give the server some time to process the request.
        Thread.sleep(1500)
        playbackResponse = executeCall(trakt.scrobble().stopWatching(genMovieProgress(75.0)))
        // Above 80% progress action is "scrobble", below "pause".
        // Pause to avoid blocking the next test until the movie runtime has passed.
        Assertions.assertThat(playbackResponse.action).isEqualTo("pause")
    }

    companion object {
        private const val APP_VERSION = "trakt-java-4"
        private const val APP_DATE = "2014-10-15"
    }
}