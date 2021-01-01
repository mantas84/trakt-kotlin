package dev.mantasboro.trakt5.services

import dev.mantasboro.trakt5.BaseTestCase
import dev.mantasboro.trakt5.TestData
import dev.mantasboro.trakt5.entities.HistoryEntry
import dev.mantasboro.trakt5.entities.ListItemRank
import dev.mantasboro.trakt5.entities.MovieIds
import dev.mantasboro.trakt5.entities.PersonIds
import dev.mantasboro.trakt5.entities.ShowIds
import dev.mantasboro.trakt5.entities.SyncItems
import dev.mantasboro.trakt5.entities.SyncMovie
import dev.mantasboro.trakt5.entities.SyncPerson
import dev.mantasboro.trakt5.entities.SyncShow
import dev.mantasboro.trakt5.entities.TraktList
import dev.mantasboro.trakt5.entities.UserSlug
import dev.mantasboro.trakt5.entities.base.BaseIdsData
import dev.mantasboro.trakt5.enums.Extended
import dev.mantasboro.trakt5.enums.HistoryType
import dev.mantasboro.trakt5.enums.ListPrivacy
import dev.mantasboro.trakt5.enums.Rating
import dev.mantasboro.trakt5.enums.RatingsFilter
import dev.mantasboro.trakt5.enums.SortBy
import dev.mantasboro.trakt5.enums.SortHow
import org.assertj.core.api.Assertions
import org.junit.Test
import org.threeten.bp.LocalTime
import org.threeten.bp.OffsetDateTime
import org.threeten.bp.ZoneOffset
import retrofit2.Response
import java.io.IOException
import java.net.HttpURLConnection
import java.util.ArrayList

class UsersTest : BaseTestCase() {
    @Test
    @Throws(IOException::class)
    fun test_getSettings() {
        val settings = executeCall(trakt.users().settings())
        Assertions.assertThat(settings.user).isNotNull
        Assertions.assertThat(settings.account).isNotNull
        Assertions.assertThat(settings.connections).isNotNull
        Assertions.assertThat(settings.sharing_text).isNotNull
    }

    @Test
    @Throws(IOException::class)
    fun test_profile() {
        val user = executeCall(trakt.users().profile(TestData.USER_SLUG, Extended.FULL))
        Assertions.assertThat(user.username).isEqualTo(TestData.USERNAME_STRING)
        Assertions.assertThat(user.isPrivate).isEqualTo(false)
        Assertions.assertThat(user.name).isEqualTo(TestData.USER_REAL_NAME)
        Assertions.assertThat(user.vip).isEqualTo(true)
        Assertions.assertThat(user.vip_ep).isEqualTo(true)
        Assertions.assertThat(user.ids!!.slug).isEqualTo(TestData.USERNAME_STRING)
        Assertions.assertThat(user.images!!.avatar!!.full).isNotEmpty
    }

    @Test
    @Throws(IOException::class)
    fun test_collectionMovies() {
        val movies = executeCall(
            trakt.users().collectionMovies(TestData.USER_SLUG, null)
        )
        assertSyncMovies(movies, "collection")
    }

    @Test
    @Throws(IOException::class)
    fun test_collectionShows() {
        val shows = executeCall(trakt.users().collectionShows(TestData.USER_SLUG, null))
        assertSyncShows(shows, "collection")
    }

    @Test
    @Throws(IOException::class)
    fun test_lists() {
        val lists = executeCall(trakt.users().lists(UserSlug.ME))
        for (list in lists) {
            // ensure id and a title
            Assertions.assertThat(list.ids).isNotNull
            Assertions.assertThat(list.ids!!.trakt).isNotNull
            Assertions.assertThat(list.name).isNotEmpty
            Assertions.assertThat(list.description).isNotEmpty
            Assertions.assertThat(list.privacy).isNotNull
            Assertions.assertThat(list.display_numbers).isNotNull
            Assertions.assertThat(list.allow_comments).isNotNull
            Assertions.assertThat(list.sort_by).isNotNull
            Assertions.assertThat(list.sort_how).isNotNull
            Assertions.assertThat(list.created_at).isNotNull
            Assertions.assertThat(list.updated_at).isNotNull
            Assertions.assertThat(list.item_count).isGreaterThanOrEqualTo(0)
            Assertions.assertThat(list.comment_count).isGreaterThanOrEqualTo(0)
            Assertions.assertThat(list.likes).isGreaterThanOrEqualTo(0)
            Assertions.assertThat(list.user).isNotNull
        }
    }

    @Test
    @Throws(IOException::class)
    fun test_createList() {
        val list = TraktList()
        list.name("trakt-java")
        list.description("trakt-java test list")
        list.privacy(ListPrivacy.PUBLIC)
        list.allowComments(false)
        list.displayNumbers(false)
        list.sortBy(SortBy.ADDED)
        list.sortHow(SortHow.ASC)

        // create list...
        val createdList = executeCall(trakt.users().createList(UserSlug.ME, list))
        Assertions.assertThat(createdList.ids!!.trakt).isNotNull
        Assertions.assertThat(createdList.name).isEqualTo(list.name)
        Assertions.assertThat(createdList.description).isEqualTo(list.description)
        Assertions.assertThat(createdList.privacy).isEqualTo(ListPrivacy.PUBLIC)
        Assertions.assertThat(createdList.allow_comments).isEqualTo(false)
        Assertions.assertThat(createdList.display_numbers).isEqualTo(false)
        Assertions.assertThat(createdList.sort_by).isEqualTo(SortBy.ADDED)
        Assertions.assertThat(createdList.sort_how)
            .isEqualTo(SortHow.DESC) // Note: created list is always desc, even on web.

        // ...and delete it again
        val deleteResponse: Response<*> =
            trakt.users().deleteList(UserSlug.ME, createdList.ids!!.trakt.toString()).execute()
        assertSuccessfulResponse(deleteResponse)
        Assertions.assertThat(deleteResponse.code()).isEqualTo(204)
    }

    @Test
    @Throws(IOException::class)
    fun test_updateList() {
        // change name (append a new suffix that changes frequently)
        val secondOfDay = LocalTime.now().toSecondOfDay()
        val list = TraktList()
        list.name("trakt-java $secondOfDay")

        // create list...
        val updatedList = executeCall(
            trakt.users().updateList(
                UserSlug.ME,
                TEST_LIST_WITH_ITEMS_TRAKT_ID.toString(), list
            )
        )
        Assertions.assertThat(updatedList.ids!!.trakt).isEqualTo(TEST_LIST_WITH_ITEMS_TRAKT_ID)
        Assertions.assertThat(updatedList.name).isEqualTo(list.name)
    }

    @Test
    @Throws(IOException::class)
    fun test_listItems() {
        val entries = executeCall(
            trakt.users().listItems(
                UserSlug.ME, TEST_LIST_WITH_ITEMS_TRAKT_ID.toString(),
                null
            )
        )
        for ((id, rank, listed_at, type) in entries) {
            Assertions.assertThat(listed_at).isNotNull
            Assertions.assertThat(id).isNotNull
            Assertions.assertThat(rank).isNotNull
            Assertions.assertThat(type).isNotNull
        }
    }

    @Test
    @Throws(IOException::class)
    fun test_addListItems() {
        val show = SyncShow().id(ShowIds(tvdb = 256227))
        val movie = SyncMovie().id(MovieIds(BaseIdsData( tmdb = TestData.MOVIE_TMDB_ID)))
        val person: SyncPerson = SyncPerson(ids = PersonIds(data = BaseIdsData(tmdb = TestData.PERSON_TMDB_ID)))
        val items = SyncItems()
        items.shows(show)
        items.movies(movie)
        items.people(person)

        // add items...
        var response = executeCall(
            trakt.users().addListItems(
                UserSlug.ME, TEST_LIST_WITH_ITEMS_TRAKT_ID.toString(),
                items
            )
        )
        Assertions.assertThat(response.added!!.shows).isEqualTo(1)
        Assertions.assertThat(response.added!!.movies).isEqualTo(1)
        Assertions.assertThat(response.added!!.people).isEqualTo(1)

        // ...and remove them again
        response = executeCall(
            trakt.users().deleteListItems(
                UserSlug.ME, TEST_LIST_WITH_ITEMS_TRAKT_ID.toString(),
                items
            )
        )
        Assertions.assertThat(response.deleted!!.shows).isEqualTo(1)
        Assertions.assertThat(response.deleted!!.movies).isEqualTo(1)
        Assertions.assertThat(response.deleted!!.people).isEqualTo(1)
    }

    @Test
    @Throws(IOException::class)
    fun test_reorderListItems() {
        val entries = executeCall(
            trakt.users().listItems(
                UserSlug.ME, TEST_LIST_WITH_ITEMS_TRAKT_ID.toString(),
                null
            )
        )

        // reverse order
        val newRank: MutableList<Long?> = ArrayList()
        for (i in entries.indices.reversed()) {
            newRank.add(entries[i].id)
        }
        val (updated) = executeCall(
            trakt.users().reorderListItems(
                UserSlug.ME, TEST_LIST_WITH_ITEMS_TRAKT_ID.toString(),
                ListItemRank(newRank.filterNotNull())
            )
        )
        Assertions.assertThat(updated).isEqualTo(entries.size)
    }

    @Test
    @Throws(InterruptedException::class, IOException::class)
    fun test_unfollowAndFollow() {
        // unfollow first
        val userToFollow = UserSlug(TestData.USER_TO_FOLLOW)
        val response: Response<*> = trakt.users().unfollow(userToFollow).execute()
        assertSuccessfulResponse(response)
        Assertions.assertThat(response.code()).isEqualTo(HttpURLConnection.HTTP_NO_CONTENT)

        // give the server some time to handle the data
        Thread.sleep(1000)

        // follow again
        val (_, user) = executeCall(trakt.users().follow(userToFollow))
        Assertions.assertThat(user!!.username).isEqualTo(TestData.USER_TO_FOLLOW)
    }

    @Test
    @Throws(IOException::class)
    fun test_followers() {
        val followers = executeCall(trakt.users().followers(TestData.USER_SLUG, null))
        for ((followed_at, user) in followers) {
            Assertions.assertThat(followed_at).isNotNull
            Assertions.assertThat(user).isNotNull
        }
    }

    @Test
    @Throws(IOException::class)
    fun test_following() {
        val following = executeCall(trakt.users().following(TestData.USER_SLUG, null))
        for ((followed_at, user) in following) {
            Assertions.assertThat(followed_at).isNotNull
            Assertions.assertThat(user).isNotNull
        }
    }

    @Test
    @Throws(IOException::class)
    fun test_friends() {
        val friends = executeCall(trakt.users().friends(TestData.USER_SLUG, null))
        for ((friends_at, user) in friends) {
            Assertions.assertThat(friends_at).isNotNull
            Assertions.assertThat(user).isNotNull
        }
    }

    @Test
    @Throws(IOException::class)
    fun test_historyEpisodesAndMovies() {
        val history = executeCall(
            trakt.users().history(
                TestData.USER_SLUG, 1,
                DEFAULT_PAGE_SIZE, null,
                null, null
            )
        )
        for ((id, watched_at, action, type, episode, show, movie) in history) {
            Assertions.assertThat(id).isGreaterThan(0)
            Assertions.assertThat(watched_at).isNotNull
            Assertions.assertThat(action).isNotEmpty
            Assertions.assertThat(type).isNotEmpty
            if ("episode" == type) {
                Assertions.assertThat(episode).isNotNull
                Assertions.assertThat(show).isNotNull
            } else if ("movie" == type) {
                Assertions.assertThat(movie).isNotNull
            }
        }
    }

    @Test
    @Throws(IOException::class)
    fun test_historyEpisodes() {
        val history = executeCall(
            trakt.users().history(
                TestData.USER_SLUG, HistoryType.EPISODES, 1,
                DEFAULT_PAGE_SIZE, null,
                null, null
            )
        )
        for ((id, watched_at, action, type, episode, show) in history) {
            Assertions.assertThat(id).isGreaterThan(0)
            Assertions.assertThat(watched_at).isNotNull
            Assertions.assertThat(action).isNotEmpty
            Assertions.assertThat(type).isEqualTo("episode")
            Assertions.assertThat(episode).isNotNull
            Assertions.assertThat(show).isNotNull
            println(
                "Episode watched at date: " + watched_at + watched_at!!.toInstant().toEpochMilli()
            )
        }
    }

    @Test
    @Throws(IOException::class)
    fun test_historyMovies() {
        val history = executeCall(
            trakt.users().history(
                UserSlug.ME, HistoryType.MOVIES, 1,
                DEFAULT_PAGE_SIZE, null,
                null, null
            )
        )
        assertMovieHistory(history)
    }

    @Test
    @Throws(IOException::class)
    fun test_historyItem() {
        val history = executeCall(
            trakt.users().history(
                UserSlug.ME, HistoryType.MOVIES,
                TestData.MOVIE_WATCHED_TRAKT_ID, 1,
                DEFAULT_PAGE_SIZE, null,
                OffsetDateTime.of(2016, 8, 3, 9, 0, 0, 0, ZoneOffset.UTC),
                OffsetDateTime.of(2016, 8, 3, 10, 0, 0, 0, ZoneOffset.UTC)
            )
        )
        Assertions.assertThat(history.size).isGreaterThanOrEqualTo(0)
        assertMovieHistory(history)
    }

    private fun assertMovieHistory(history: List<HistoryEntry>) {
        for ((_, watched_at, action, type, _, _, movie) in history) {
            Assertions.assertThat(watched_at).isNotNull
            Assertions.assertThat(action).isNotEmpty
            Assertions.assertThat(type).isEqualTo("movie")
            Assertions.assertThat(movie).isNotNull
        }
    }

    @Test
    @Throws(IOException::class)
    fun test_ratingsMovies() {
        val ratedMovies = executeCall(
            trakt.users().ratingsMovies(
                TestData.USER_SLUG, RatingsFilter.ALL,
                null
            )
        )
        assertRatedEntities(ratedMovies)
    }

    @Test
    @Throws(IOException::class)
    fun test_ratingsMovies_filtered() {
        val ratedMovies = executeCall(
            trakt.users().ratingsMovies(
                TestData.USER_SLUG,
                RatingsFilter.TOTALLYNINJA,
                null
            )
        )
        for (movie in ratedMovies) {
            Assertions.assertThat(movie.rated_at).isNotNull
            Assertions.assertThat(movie.rating).isEqualTo(Rating.TOTALLYNINJA)
        }
    }

    @Test
    @Throws(IOException::class)
    fun test_ratingsShows() {
        val ratedShows = executeCall(
            trakt.users().ratingsShows(
                TestData.USER_SLUG, RatingsFilter.ALL,
                null
            )
        )
        assertRatedEntities(ratedShows)
    }

    @Test
    @Throws(IOException::class)
    fun test_ratingsSeasons() {
        val ratedSeasons = executeCall(
            trakt.users().ratingsSeasons(
                TestData.USER_SLUG, RatingsFilter.ALL,
                null
            )
        )
        assertRatedEntities(ratedSeasons)
    }

    @Test
    @Throws(IOException::class)
    fun test_ratingsEpisodes() {
        val ratedEpisodes = executeCall(
            trakt.users().ratingsEpisodes(
                TestData.USER_SLUG, RatingsFilter.ALL,
                null
            )
        )
        assertRatedEntities(ratedEpisodes)
    }

    @Test
    @Throws(IOException::class)
    fun test_watchlistMovies() {
        val movies = executeCall(
            trakt.users().watchlistMovies(
                UserSlug.ME,
                null
            )
        )
        assertSyncMovies(movies, "watchlist")
    }

    @Test
    @Throws(IOException::class)
    fun test_watchlistShows() {
        val shows = executeCall(
            trakt.users().watchlistShows(
                UserSlug.ME,
                null
            )
        )
        for (show in shows) {
            Assertions.assertThat(show.show).isNotNull
            Assertions.assertThat(show.listed_at).isNotNull
        }
    }

    @Test
    @Throws(IOException::class)
    fun test_watchlistSeasons() {
        val seasons = executeCall(
            trakt.users().watchlistSeasons(
                UserSlug.ME,
                null
            )
        )
        for (season in seasons) {
            Assertions.assertThat(season.season).isNotNull
            Assertions.assertThat(season.show).isNotNull
            Assertions.assertThat(season.listed_at).isNotNull
        }
    }

    @Test
    @Throws(IOException::class)
    fun test_watchlistEpisodes() {
        val episodes = executeCall(
            trakt.users().watchlistEpisodes(
                UserSlug.ME,
                null
            )
        )
        for (episode in episodes) {
            Assertions.assertThat(episode.episode).isNotNull
            Assertions.assertThat(episode.show).isNotNull
            Assertions.assertThat(episode.listed_at).isNotNull
        }
    }

    @Test
    @Throws(IOException::class)
    fun test_watchedMovies() {
        val watchedMovies = executeCall(
            trakt.users().watchedMovies(
                TestData.USER_SLUG,
                null
            )
        )
        assertSyncMovies(watchedMovies, "watched")
    }

    @Test
    @Throws(IOException::class)
    fun test_watchedShows() {
        val watchedShows = executeCall(
            trakt.users().watchedShows(
                TestData.USER_SLUG,
                null
            )
        )
        assertSyncShows(watchedShows, "watched")
    }

    companion object {
        private const val TEST_LIST_WITH_ITEMS_TRAKT_ID = 20416093
    }
}