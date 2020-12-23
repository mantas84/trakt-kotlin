package dev.mantasboro.trakt5.services

import dev.mantasboro.trakt5.BaseTestCase
import dev.mantasboro.trakt5.TestData
import dev.mantasboro.trakt5.entities.BaseShow
import dev.mantasboro.trakt5.entities.Show
import dev.mantasboro.trakt5.enums.Extended
import dev.mantasboro.trakt5.enums.Type
import org.assertj.core.api.Assertions
import org.junit.Test
import java.io.IOException
import java.lang.String

class ShowsTest : BaseTestCase() {
    @Test
    @Throws(IOException::class)
    fun test_popular() {
        val shows = executeCall(trakt.shows().popular(2, null, null))
        Assertions.assertThat(shows).isNotNull
        Assertions.assertThat(shows.size).isLessThanOrEqualTo(DEFAULT_PAGE_SIZE)
        for (show in shows) {
            assertShowNotNull(show)
        }
    }

    @Test
    @Throws(IOException::class)
    fun test_trending() {
        val shows = executeCall(trakt.shows().trending(1, null, null))
        Assertions.assertThat(shows).isNotNull
        Assertions.assertThat(shows.size).isLessThanOrEqualTo(DEFAULT_PAGE_SIZE)
        for (show in shows) {
            Assertions.assertThat(show.watchers).isNotNull
            assertShowNotNull(show.show)
        }
    }

    private fun assertShowNotNull(show: Show?) {
        Assertions.assertThat(show).isNotNull
        Assertions.assertThat(show!!.title).isNotEmpty
        Assertions.assertThat(show.ids).isNotNull
        Assertions.assertThat(show.ids!!.trakt).isNotNull
        Assertions.assertThat(show.year).isNotNull
    }

    @Test
    @Throws(IOException::class)
    fun test_summary_slug() {
        val show = executeCall(trakt.shows().summary(TestData.SHOW_SLUG, Extended.FULL))
        assertTestShow(show)
    }

    @Test
    @Throws(IOException::class)
    fun test_summary_trakt_id() {
        val show = executeCall(
            trakt.shows().summary(String.valueOf(TestData.SHOW_TRAKT_ID), Extended.FULL)
        )
        assertTestShow(show)
    }

    private fun assertTestShow(show: Show) {
        Assertions.assertThat(show).isNotNull
        Assertions.assertThat(show.title).isEqualTo(TestData.SHOW_TITLE)
        Assertions.assertThat(show.year).isEqualTo(TestData.SHOW_YEAR)
        Assertions.assertThat(show.ids).isNotNull
        Assertions.assertThat(show.ids!!.trakt).isEqualTo(TestData.SHOW_TRAKT_ID)
        Assertions.assertThat(show.ids!!.slug).isEqualTo(TestData.SHOW_SLUG)
        Assertions.assertThat(show.ids!!.imdb).isEqualTo(TestData.SHOW_IMDB_ID)
        Assertions.assertThat(show.ids!!.tmdb).isEqualTo(TestData.SHOW_TMDB_ID)
        Assertions.assertThat(show.ids!!.tvdb).isEqualTo(TestData.SHOW_TVDB_ID)
        Assertions.assertThat(show.ids!!.tvrage).isEqualTo(TestData.SHOW_TVRAGE_ID)
    }

    @Test
    @Throws(IOException::class)
    fun test_translations() {
        val translations = executeCall(trakt.shows().translations("breaking-bad"))
        Assertions.assertThat(translations).isNotNull
        for (translation in translations) {
            Assertions.assertThat(translation.language).isNotEmpty
        }
    }

    @Test
    @Throws(IOException::class)
    fun test_translation() {
        val translations = executeCall(trakt.shows().translation("breaking-bad", "de"))
        // we know that Breaking Bad has a German translation, otherwise this test would fail
        Assertions.assertThat(translations).isNotNull
        Assertions.assertThat(translations).hasSize(1)
        Assertions.assertThat(translations[0].language).isEqualTo("de")
    }

    @Test
    @Throws(IOException::class)
    fun test_comments() {
        val comments = executeCall(
            trakt.shows().comments(
                TestData.SHOW_SLUG, 1, null,
                null
            )
        )
        Assertions.assertThat(comments).isNotNull
        Assertions.assertThat(comments.size).isLessThanOrEqualTo(DEFAULT_PAGE_SIZE)
    }

    @Test
    @Throws(IOException::class)
    fun test_people() {
        val credits = executeCall(trakt.shows().people(TestData.SHOW_SLUG))
        assertCast(credits, Type.PERSON)
        assertCrew(credits, Type.PERSON)
    }

    @Test
    @Throws(IOException::class)
    fun test_ratings() {
        val ratings = executeCall(trakt.shows().ratings(TestData.SHOW_SLUG))
        assertRatings(ratings)
    }

    @Test
    @Throws(IOException::class)
    fun test_stats() {
        val stats = executeCall(trakt.shows().stats(TestData.SHOW_SLUG))
        assertShowStats(stats)
    }

    @Test
    @Throws(IOException::class)
    fun test_collected_progress() {
        val show = executeCall(
            trakt
                .shows()
                .collectedProgress(TestData.SHOW_SLUG, null, null, null, null, null)
        )
        Assertions.assertThat(show).isNotNull
        Assertions.assertThat(show.last_collected_at).isNotNull
        assertProgress(show)
    }

    @Test
    @Throws(IOException::class)
    fun test_watched_progress() {
        val show = executeCall(
            trakt
                .shows()
                .watchedProgress(TestData.SHOW_SLUG, null, null, null, null, null)
        )
        Assertions.assertThat(show).isNotNull
        Assertions.assertThat(show.last_watched_at).isNotNull
        assertProgress(show)
    }

    private fun assertProgress(show: BaseShow) {
        Assertions.assertThat(show.aired).isGreaterThan(30)
        Assertions.assertThat(show.completed).isGreaterThanOrEqualTo(1)
        Assertions.assertThat(show.last_episode).isNotNull

        // show progress not complete
        Assertions.assertThat(show.completed).isLessThan(show.aired)
        Assertions.assertThat(show.next_episode).isNotNull

        // Killjoys has 5 aired seasons
        Assertions.assertThat(show.seasons).isNotNull
        Assertions.assertThat(show.seasons).hasSize(5)
        val (number, episodes, aired, completed) = show.seasons!![0]
        Assertions.assertThat(number).isEqualTo(1)
        // all aired
        Assertions.assertThat(aired).isEqualTo(10)
        // always at least 1 watched
        Assertions.assertThat(completed).isGreaterThanOrEqualTo(1)

        // episode 1 should always be watched
        Assertions.assertThat(episodes).isNotNull
        val (number1, _, _, _, completed1) = episodes!![0]
        Assertions.assertThat(number1).isEqualTo(1)
        Assertions.assertThat(completed1).isTrue
    }

    @Test
    @Throws(IOException::class)
    fun test_related() {
        val related = executeCall(trakt.shows().related(TestData.SHOW_SLUG, 1, null, null))
        Assertions.assertThat(related).isNotNull
        Assertions.assertThat(related.size).isLessThanOrEqualTo(DEFAULT_PAGE_SIZE)
        for (show in related) {
            assertShowNotNull(show)
        }
    }
}