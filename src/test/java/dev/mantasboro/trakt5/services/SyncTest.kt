package dev.mantasboro.trakt5.services

import dev.mantasboro.trakt5.BaseTestCase
import dev.mantasboro.trakt5.TestData
import dev.mantasboro.trakt5.entities.*
import dev.mantasboro.trakt5.enums.Rating
import dev.mantasboro.trakt5.enums.RatingsFilter
import org.assertj.core.api.Assertions
import org.junit.Test
import org.threeten.bp.Instant
import org.threeten.bp.OffsetDateTime
import org.threeten.bp.ZoneOffset
import java.io.IOException
import java.util.*

class SyncTest : BaseTestCase() {
    @Test
    @Throws(IOException::class)
    fun test_lastActivites() {
        val lastActivities = executeCall(trakt.sync().lastActivities())
        Assertions.assertThat(lastActivities).isNotNull
        Assertions.assertThat(lastActivities.all).isNotNull
        assertLastActivityMore(lastActivities.movies)
        assertLastActivityMore(lastActivities.episodes)
        assertLastActivity(lastActivities.shows)
        assertLastActivity(lastActivities.seasons)
        assertListsLastActivity(lastActivities.lists)
    }

    @Test
    @Throws(IOException::class, InterruptedException::class)
    fun test_getPlayback() {
        // Make sure there are paused entries.
        val agentsOfShield = 4420028 /* S01E01 */
        val episode = SyncEpisode().id(EpisodeIds(tvdb = agentsOfShield))
        val episodeProgress =
            ScrobbleProgress(episode = episode, progress = 25.0, app_date = APP_DATE, app_version = APP_VERSION)
        val episodeResponse = executeCall(trakt.scrobble().pauseWatching(episodeProgress))
        Assertions.assertThat(episodeResponse.action).isEqualTo("pause")

        // Give the server some time to process the request.
        Thread.sleep(1500)
        val interstellar = 157336
        val movie = SyncMovie().id(MovieIds(tmdb = 157336))
        val movieProgress =
            ScrobbleProgress(movie = movie, progress = 32.0, app_date = APP_DATE, app_version = APP_VERSION)
        val movieResponse = executeCall(trakt.scrobble().pauseWatching(movieProgress))
        Assertions.assertThat(movieResponse.action).isEqualTo("pause")

        // Give the server some time to process the request.
        Thread.sleep(1500)
        val playbacks = executeCall(trakt.sync().getPlayback(10))
        Assertions.assertThat(playbacks).isNotNull
        var foundEpisode = false
        var foundMovie = false
        for (playback in playbacks) {
            Assertions.assertThat(playback.type).isNotNull
            if (playback.episode != null && playback.episode!!.ids != null && playback.episode!!.ids!!.tvdb != null && playback.episode!!.ids!!.tvdb == agentsOfShield) {
                foundEpisode = true
                Assertions.assertThat(playback.paused_at).isNotNull
                Assertions.assertThat(playback.progress).isEqualTo(25.0)
            }
            if (playback.movie != null && playback.movie!!.ids != null && playback.movie!!.ids!!.tmdb != null && playback.movie!!.ids!!.tmdb == interstellar) {
                foundMovie = true
                Assertions.assertThat(playback.paused_at).isNotNull
                Assertions.assertThat(playback.progress).isEqualTo(32.0)
            }
        }
        if (!foundEpisode) Assertions.fail<Any>("Agents of Shield episode not paused.")
        if (!foundMovie) Assertions.fail<Any>("Interstellar movie not paused.")
    }

    private fun assertLastActivityMore(activityMore: LastActivityMore?) {
        assertLastActivity(activityMore)
        Assertions.assertThat(activityMore!!.paused_at).isNotNull
        Assertions.assertThat(activityMore.collected_at).isNotNull
        Assertions.assertThat(activityMore.watched_at).isNotNull
    }

    private fun assertLastActivity(activity: LastActivity?) {
        Assertions.assertThat(activity).isNotNull
        Assertions.assertThat(activity!!.commented_at).isNotNull
        Assertions.assertThat(activity.rated_at).isNotNull
        Assertions.assertThat(activity.watchlisted_at).isNotNull
    }

    private fun assertListsLastActivity(activity: ListsLastActivity?) {
        Assertions.assertThat(activity).isNotNull
        Assertions.assertThat(activity!!.commented_at).isNotNull
        Assertions.assertThat(activity.liked_at).isNotNull
        Assertions.assertThat(activity.updated_at).isNotNull
    }

    @Test
    @Throws(IOException::class)
    fun test_collectionMovies() {
        val movies = executeCall(trakt.sync().collectionMovies(null))
        assertSyncMovies(movies, "collection")
    }

    @Test
    @Throws(IOException::class)
    fun test_collectionShows() {
        val shows = executeCall(trakt.sync().collectionShows(null))
        assertSyncShows(shows, "collection")
    }

    @Test
    @Throws(IOException::class)
    fun test_addItemsToCollection_movie() {
        val movie = SyncMovie()
        movie.ids = buildMovieIds()
        val items = SyncItems().movies(movie)
        addItemsToCollection(items)
    }

    @Test
    @Throws(IOException::class)
    fun test_addItemsToCollection_show() {
        val show = SyncShow()
        show.ids = buildShowIds()
        val items = SyncItems().shows(show)
        addItemsToCollection(items)
    }

    @Test
    @Throws(IOException::class)
    fun test_addItemsToCollection_season() {
        // season
        val season = SyncSeason()
        season.number = 1

        // show
        val show = SyncShow()
        show.ids = ShowIds(slug = "community")
        show.seasons = ArrayList()
        show.seasons(season)
        val items = SyncItems().shows(show)
        addItemsToCollection(items)
    }

    @Test
    @Throws(IOException::class)
    fun test_addItemsToCollection_episode() {
        // Fri Jul 14 2017 02:40:00 UTC
        val collectedAt = OffsetDateTime.ofInstant(Instant.ofEpochMilli(1500000000000L), ZoneOffset.UTC)

        // episodes
        val episode1 = SyncEpisode()
        episode1.number = 1
        episode1.collectedAt(collectedAt)
        val episode2 = SyncEpisode()
        episode2.number = 2

        // season
        val season = SyncSeason()
        season.number = TestData.EPISODE_SEASON
        val episodes: MutableList<SyncEpisode> = ArrayList()
        episodes.add(episode1)
        episodes.add(episode2)
        season.episodes = episodes

        // show
        val show = SyncShow()
        show.ids = ShowIds(tvdb = TestData.SHOW_TVDB_ID)
        val seasons: MutableList<SyncSeason> = ArrayList()
        seasons.add(season)
        show.seasons = seasons
        val items = SyncItems().shows(show)
        addItemsToCollection(items)
    }

    @Throws(IOException::class)
    private fun addItemsToCollection(items: SyncItems) {
        val response = executeCall(trakt.sync().addItemsToCollection(items))
        assertSyncResponse(response)
    }

    private fun assertSyncResponse(response: SyncResponse) {
        Assertions.assertThat(response.added!!.movies).isNotNull
        Assertions.assertThat(response.added!!.episodes).isNotNull
        Assertions.assertThat(response.existing!!.movies).isNotNull
        Assertions.assertThat(response.existing!!.episodes).isNotNull
        Assertions.assertThat(response.not_found).isNotNull
        Assertions.assertThat(response.deleted).isNull()
    }

    @Test
    @Throws(IOException::class)
    fun test_deleteItemsFromCollection() {
        val response = executeCall(trakt.sync().deleteItemsFromCollection(buildItemsForDeletion()))
        assertSyncResponseDelete(response)
    }

    private fun assertSyncResponseDelete(response: SyncResponse) {
        Assertions.assertThat(response.deleted!!.movies).isNotNull
        Assertions.assertThat(response.deleted!!.episodes).isNotNull
        Assertions.assertThat(response.existing).isNull()
        Assertions.assertThat(response.not_found).isNotNull
        Assertions.assertThat(response.added).isNull()
    }

    private fun buildItemsForDeletion(): SyncItems {
        // movie
        val movie = SyncMovie()
        movie.ids = buildMovieIds()

        // episode
        val episode2 = SyncEpisode()
        episode2.number = 2
        val season = SyncSeason()
        season.number = TestData.EPISODE_SEASON
        val episodes: MutableList<SyncEpisode> = ArrayList()
        episodes.add(episode2)
        season.episodes = episodes
        val show = SyncShow().id(ShowIds(tvdb = TestData.SHOW_TVDB_ID))
        val seasons: MutableList<SyncSeason> = ArrayList()
        seasons.add(season)
        show.seasons = seasons
        return SyncItems().movies(movie).shows(show)
    }

    @Test
    @Throws(IOException::class)
    fun test_watchedMovies() {
        val watchedMovies = executeCall(trakt.sync().watchedMovies(null))
        assertSyncMovies(watchedMovies, "watched")
    }

    @Test
    @Throws(IOException::class)
    fun test_watchedShows() {
        val watchedShows = executeCall(trakt.sync().watchedShows(null))
        assertSyncShows(watchedShows, "watched")
    }

    @Test
    @Throws(IOException::class)
    fun test_addItemsToWatchedHistory() {
        // movie
        val movie = SyncMovie()
        movie.watched_at = OffsetDateTime.now().minusHours(1)
        movie.ids = buildMovieIds()

        // episode
        val episode = SyncEpisode()
        episode.number = TestData.EPISODE_NUMBER
        episode.watched_at = OffsetDateTime.now().minusHours(1)
        val episode2 = SyncEpisode()
        episode2.number = 2
        episode2.watched_at = OffsetDateTime.now().minusMinutes(30)
        // season
        val season = SyncSeason()
        season.number = TestData.EPISODE_SEASON
        val episodes: MutableList<SyncEpisode> = ArrayList()
        episodes.add(episode)
        episodes.add(episode2)
        season.episodes = episodes
        // show
        val show = SyncShow()
        show.ids = ShowIds(tvdb = TestData.SHOW_TVDB_ID)
        val seasons: MutableList<SyncSeason> = ArrayList()
        seasons.add(season)
        show.seasons = seasons
        val items = SyncItems().movies(movie).shows(show)
        val response = executeCall(trakt.sync().addItemsToWatchedHistory(items))
        Assertions.assertThat(response).isNotNull
        Assertions.assertThat(response.added!!.movies).isNotNull
        Assertions.assertThat(response.added!!.episodes).isNotNull
        Assertions.assertThat(response.existing).isNull()
        Assertions.assertThat(response.deleted).isNull()
        Assertions.assertThat(response.not_found).isNotNull
    }

    @Test
    @Throws(IOException::class)
    fun test_deleteItemsFromWatchedHistory() {
        val items = buildItemsForDeletion()
        val response = executeCall(trakt.sync().deleteItemsFromWatchedHistory(items))
        Assertions.assertThat(response).isNotNull
        Assertions.assertThat(response.deleted!!.movies).isNotNull
        Assertions.assertThat(response.deleted!!.episodes).isNotNull
        Assertions.assertThat(response.added).isNull()
        Assertions.assertThat(response.existing).isNull()
        Assertions.assertThat(response.not_found).isNotNull
    }

    @Test
    @Throws(IOException::class)
    fun test_ratingsMovies() {
        val ratedMovies = executeCall(
            trakt.sync().ratingsMovies(
                RatingsFilter.ALL,
                null
            )
        )
        assertRatedEntities(ratedMovies)
    }

    @Test
    @Throws(IOException::class)
    fun test_ratingsMovies_filtered() {
        val ratedMovies = executeCall(
            trakt.sync().ratingsMovies(
                RatingsFilter.TOTALLYNINJA,
                null
            )
        )
        Assertions.assertThat(ratedMovies).isNotNull
        for (movie in ratedMovies) {
            Assertions.assertThat(movie.rated_at).isNotNull
            Assertions.assertThat(movie.rating).isEqualTo(Rating.TOTALLYNINJA)
        }
    }

    @Test
    @Throws(IOException::class)
    fun test_ratingsShows() {
        val ratedShows = executeCall(
            trakt.sync().ratingsShows(
                RatingsFilter.ALL,
                null
            )
        )
        assertRatedEntities(ratedShows)
    }

    @Test
    @Throws(IOException::class)
    fun test_ratingsSeasons() {
        val ratedSeasons = executeCall(
            trakt.sync().ratingsSeasons(
                RatingsFilter.ALL,
                null
            )
        )
        assertRatedEntities(ratedSeasons)
    }

    @Test
    @Throws(IOException::class)
    fun test_ratingsEpisodes() {
        val ratedEpisodes = executeCall(
            trakt.sync().ratingsEpisodes(
                RatingsFilter.ALL,
                null
            )
        )
        assertRatedEntities(ratedEpisodes)
    }

    @Test
    @Throws(IOException::class)
    fun test_addRatings_movie() {
        val movie = SyncMovie()
            .id(MovieIds(slug = TestData.MOVIE_SLUG))
            .rating(Rating.MEH)
        val items = SyncItems().movies(movie)
        executeCall(trakt.sync().addRatings(items))
    }

    @Test
    @Throws(IOException::class)
    fun test_addRatings_show() {
        val show = SyncShow()
            .id(ShowIds(slug = TestData.SHOW_SLUG))
            .rating(Rating.TERRIBLE)
        val items = SyncItems().shows(show)
        executeCall(trakt.sync().addRatings(items))
    }

    @Test
    @Throws(IOException::class)
    fun test_addRatings_season() {
        val season = SyncSeason()
            .number(TestData.EPISODE_SEASON)
            .rating(Rating.FAIR)
        val show = SyncShow()
            .id(ShowIds(slug = "community"))
            .seasons(season)
        val items = SyncItems().shows(show)
        executeCall(trakt.sync().addRatings(items))
    }

    @Test
    @Throws(IOException::class)
    fun test_addRatings_episode() {
        val episode1 = SyncEpisode()
            .number(1)
            .rating(Rating.TOTALLYNINJA)
        val episode2 = SyncEpisode()
            .number(2)
            .rating(Rating.GREAT)
        val episodes = ArrayList<SyncEpisode>()
        episodes.add(episode1)
        episodes.add(episode2)
        val season = SyncSeason()
            .number(TestData.EPISODE_SEASON)
            .episodes(episodes)
        val show = SyncShow()
            .id(ShowIds(slug = TestData.SHOW_SLUG))
            .seasons(season)
        val items = SyncItems().shows(show)
        executeCall(trakt.sync().addRatings(items))
    }

    @Test
    @Throws(IOException::class)
    fun test_deleteRatings() {
        val items = buildItemsForDeletion()
        val response = executeCall(trakt.sync().deleteRatings(items))
        Assertions.assertThat(response).isNotNull
        Assertions.assertThat(response.deleted!!.movies).isNotNull
        Assertions.assertThat(response.deleted!!.shows).isNotNull
        Assertions.assertThat(response.deleted!!.seasons).isNotNull
        Assertions.assertThat(response.deleted!!.episodes).isNotNull
        Assertions.assertThat(response.added).isNull()
        Assertions.assertThat(response.existing).isNull()
        Assertions.assertThat(response.not_found).isNotNull
    }

    @Test
    @Throws(IOException::class)
    fun test_watchlistMovies() {
        val movies = executeCall(trakt.sync().watchlistMovies(null))
        assertSyncMovies(movies, "watchlist")
    }

    @Test
    @Throws(IOException::class)
    fun test_watchlistShows() {
        val shows = executeCall(trakt.sync().watchlistShows(null))
        Assertions.assertThat(shows).isNotNull
        for (show in shows) {
            Assertions.assertThat(show.show).isNotNull
            Assertions.assertThat(show.listed_at).isNotNull
        }
    }

    @Test
    @Throws(IOException::class)
    fun test_watchlistSeasons() {
        val seasons = executeCall(trakt.sync().watchlistSeasons(null))
        Assertions.assertThat(seasons).isNotNull
        for (season in seasons) {
            Assertions.assertThat(season.season).isNotNull
            Assertions.assertThat(season.show).isNotNull
            Assertions.assertThat(season.listed_at).isNotNull
        }
    }

    @Test
    @Throws(IOException::class)
    fun test_watchlistEpisodes() {
        val episodes = executeCall(trakt.sync().watchlistEpisodes(null))
        Assertions.assertThat(episodes).isNotNull
        for (episode in episodes) {
            Assertions.assertThat(episode.episode).isNotNull
            Assertions.assertThat(episode.show).isNotNull
            Assertions.assertThat(episode.listed_at).isNotNull
        }
    }

    @Test
    @Throws(IOException::class)
    fun test_addItemsToWatchlist_movie() {
        val movie = SyncMovie()
        movie.ids = buildMovieIds()
        val items = SyncItems().movies(movie)
        addItemsToWatchlist(items)
    }

    @Test
    @Throws(IOException::class)
    fun test_addItemsToWatchlist_show() {
        val show = SyncShow()
        show.ids = buildShowIds()
        val items = SyncItems().shows(show)
        addItemsToWatchlist(items)
    }

    @Test
    @Throws(IOException::class)
    fun test_addItemsToWatchlist_season() {
        // season
        val season = SyncSeason()
        season.number = 1

        // show
        val show = SyncShow()
        show.ids = ShowIds(slug = "community")
        val seasons: MutableList<SyncSeason> = ArrayList()
        seasons.add(season)
        show.seasons = seasons
        val items = SyncItems().shows(show)
        addItemsToWatchlist(items)
    }

    @Test
    @Throws(IOException::class)
    fun test_addItemsToWatchlist_episodes() {
        // episode
        val episode1 = SyncEpisode()
        episode1.number = 1
        val episode2 = SyncEpisode()
        episode2.number = 2
        // season
        val season = SyncSeason()
        season.number = TestData.EPISODE_SEASON
        val episodes: MutableList<SyncEpisode> = ArrayList()
        episodes.add(episode1)
        episodes.add(episode2)
        season.episodes = episodes
        // show
        val show = SyncShow()
        show.ids = ShowIds(tvdb = TestData.SHOW_TVDB_ID)
        val seasons: MutableList<SyncSeason> = ArrayList()
        seasons.add(season)
        show.seasons = seasons
        val items = SyncItems().shows(show)
        addItemsToWatchlist(items)
    }

    @Throws(IOException::class)
    private fun addItemsToWatchlist(items: SyncItems) {
        val requestResponse = executeCall(trakt.sync().addItemsToWatchlist(items))
        assertSyncResponse(requestResponse)
    }

    @Test
    @Throws(IOException::class)
    fun test_deleteItemsFromWatchlist() {
        val requestResponse = executeCall(
            trakt.sync().deleteItemsFromWatchlist(
                buildItemsForDeletion()
            )
        )
        assertSyncResponseDelete(requestResponse)
    }

    private fun buildMovieIds(): MovieIds {
        return MovieIds(tmdb = TestData.MOVIE_TMDB_ID)
    }

    private fun buildShowIds(): ShowIds {
        return ShowIds(slug = "the-walking-dead")
    }
}