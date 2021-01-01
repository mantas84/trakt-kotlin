package dev.mantasboro.trakt5.services

import dev.mantasboro.trakt5.BaseTestCase
import dev.mantasboro.trakt5.TestData
import dev.mantasboro.trakt5.entities.Episode
import dev.mantasboro.trakt5.enums.Extended
import org.assertj.core.api.Assertions
import org.junit.Test
import java.io.IOException

class SeasonsTest : BaseTestCase() {
    @Test
    @Throws(IOException::class)
    fun test_summary() {
        val seasons = executeCall(
            trakt.seasons().summary(
                TestData.SHOW_SLUG,
                Extended.FULLEPISODES
            )
        )
        Assertions.assertThat(seasons).isNotNull
        Assertions.assertThat(seasons).hasSize(5)
        for (season in seasons) {
            Assertions.assertThat(season).isNotNull
            // must have at least trakt and tvdb id
            Assertions.assertThat(season.ids!!.trakt).isPositive
            if (season.ids!!.tvdb != null) {
                Assertions.assertThat(season.ids!!.tvdb).isPositive
            }
            Assertions.assertThat(season.title).isNotNull
            Assertions.assertThat(season.network).isNotNull
            // seasons start at 0 for specials
            Assertions.assertThat(season.number).isGreaterThanOrEqualTo(0)
            Assertions.assertThat(season.episode_count).isPositive
            Assertions.assertThat(season.aired_episodes).isGreaterThanOrEqualTo(0)
            Assertions.assertThat(season.rating).isBetween(0.0, 10.0)
            Assertions.assertThat(season.votes).isGreaterThanOrEqualTo(0)
            // episode details
            if (season.number == TestData.EPISODE_SEASON) {
                Assertions.assertThat(season.episodes).isNotNull
                Assertions.assertThat(season.episodes).isNotEmpty
                var firstEp: Episode? = null
                for (episode in season.episodes!!) {
                    if (episode.number == TestData.EPISODE_NUMBER) {
                        firstEp = episode
                        break
                    }
                }
                Assertions.assertThat(firstEp).isNotNull
                Assertions.assertThat(firstEp!!.title).isEqualTo(TestData.EPISODE_TITLE)
                Assertions.assertThat(firstEp.season).isEqualTo(TestData.EPISODE_SEASON)
                Assertions.assertThat(firstEp.number).isEqualTo(TestData.EPISODE_NUMBER)
                Assertions.assertThat(firstEp.ids!!.imdb).isEqualTo(TestData.EPISODE_IMDB_ID)
                Assertions.assertThat(firstEp.ids!!.tmdb).isEqualTo(TestData.EPISODE_TMDB_ID)
                Assertions.assertThat(firstEp.ids!!.tvdb).isEqualTo(TestData.EPISODE_TVDB_ID)
                Assertions.assertThat(firstEp.overview).isNotEmpty
            }
        }
    }

    @Test
    @Throws(IOException::class)
    fun test_season() {
        val season = executeCall(
            trakt.seasons().season(
                TestData.SHOW_SLUG, TestData.EPISODE_SEASON,
                null
            )
        )
        Assertions.assertThat(season).isNotNull
        Assertions.assertThat(season).isNotEmpty
        for (episode in season) {
            Assertions.assertThat(episode.season).isEqualTo(TestData.EPISODE_SEASON)
        }
    }

    @Test
    @Throws(IOException::class)
    fun test_comments() {
        executeCall(trakt.seasons().comments(TestData.SHOW_SLUG, TestData.EPISODE_SEASON))
    }

    @Test
    @Throws(IOException::class)
    fun test_ratings() {
        val ratings = executeCall(trakt.seasons().ratings(TestData.SHOW_SLUG, TestData.EPISODE_SEASON))
        assertRatings(ratings)
    }

    @Test
    @Throws(IOException::class)
    fun test_stats() {
        val stats = executeCall(trakt.seasons().stats(TestData.SHOW_SLUG, TestData.EPISODE_SEASON))
        assertShowStats(stats)
    }
}