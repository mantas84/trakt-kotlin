package dev.mantasboro.trakt5.services

import dev.mantasboro.trakt5.BaseTestCase
import dev.mantasboro.trakt5.TestData
import dev.mantasboro.trakt5.enums.Extended
import org.assertj.core.api.Assertions
import org.junit.Test
import java.io.IOException
import java.lang.String

class EpisodesTest : BaseTestCase() {
    @Test
    @Throws(IOException::class)
    fun test_summary() {
        val episode = executeCall(
            trakt.episodes().summary(
                String.valueOf(TestData.SHOW_TRAKT_ID),
                TestData.EPISODE_SEASON,
                TestData.EPISODE_NUMBER, Extended.FULL
            )
        )
        Assertions.assertThat(episode).isNotNull
        Assertions.assertThat(episode.title).isEqualTo(TestData.EPISODE_TITLE)
        Assertions.assertThat(episode.season).isEqualTo(TestData.EPISODE_SEASON)
        Assertions.assertThat(episode.number).isEqualTo(TestData.EPISODE_NUMBER)
        Assertions.assertThat(episode.ids!!.imdb).isEqualTo(TestData.EPISODE_IMDB_ID)
        Assertions.assertThat(episode.ids!!.tmdb).isEqualTo(TestData.EPISODE_TMDB_ID)
        Assertions.assertThat(episode.ids!!.tvdb).isEqualTo(TestData.EPISODE_TVDB_ID)
        Assertions.assertThat(episode.runtime).isGreaterThan(0)
        Assertions.assertThat(episode.comment_count).isGreaterThanOrEqualTo(0)
    }

    @Test
    @Throws(IOException::class)
    fun test_comments() {
        executeCall(
            trakt.episodes().comments(
                TestData.SHOW_SLUG, TestData.EPISODE_SEASON,
                TestData.EPISODE_NUMBER, 1, DEFAULT_PAGE_SIZE, null
            )
        )
    }

    @Test
    @Throws(IOException::class)
    fun test_ratings() {
        val ratings = executeCall(
            trakt.episodes().ratings(
                TestData.SHOW_SLUG, TestData.EPISODE_SEASON,
                TestData.EPISODE_NUMBER
            )
        )
        assertRatings(ratings)
    }

    @Test
    @Throws(IOException::class)
    fun test_stats() {
        val stats =
            executeCall(trakt.episodes().stats(TestData.SHOW_SLUG, TestData.EPISODE_SEASON, TestData.EPISODE_NUMBER))
        assertStats(stats)
    }
}