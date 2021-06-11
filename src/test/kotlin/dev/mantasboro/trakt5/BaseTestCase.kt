package dev.mantasboro.trakt5

import dev.mantasboro.trakt5.entities.AccessToken
import dev.mantasboro.trakt5.entities.BaseMovie
import dev.mantasboro.trakt5.entities.BaseShow
import dev.mantasboro.trakt5.entities.Credits
import dev.mantasboro.trakt5.entities.CrewMember
import dev.mantasboro.trakt5.entities.Ratings
import dev.mantasboro.trakt5.entities.Stats
import dev.mantasboro.trakt5.entities.base.BaseRatedEntity
import dev.mantasboro.trakt5.enums.Type
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.assertj.core.api.Assertions
import org.junit.BeforeClass
import retrofit2.Call
import retrofit2.Response
import java.io.IOException

open class BaseTestCase {

    internal class TestTraktV2 : TraktV2 {

        constructor(apiKey: String) : super(apiKey = apiKey,true) {}
        constructor(apiKey: String, clientSecret: String, redirectUri: String) : super(
            apiKey,
            clientSecret,
            redirectUri,
            true
        ) {
            refreshToken(TEST_REFRESH_TOKEN)
        }

        override fun setOkHttpClientDefaults(builder: OkHttpClient.Builder) {
            super.setOkHttpClientDefaults(builder)
            if (DEBUG) {
                // add logging
                // standard output is easier to read
                val logger = object : HttpLoggingInterceptor.Logger {
                    override fun log(message: String) {
                        println(message)
                    }
                }
                val logging = HttpLoggingInterceptor(logger)
                val isCI = System.getenv("CI") != null
                // Reduce log size on CI server. If there is a response issue, should test on dev machine!
                logging.level = if (isCI) HttpLoggingInterceptor.Level.HEADERS else HttpLoggingInterceptor.Level.BODY
                if (isCI) {
                    logging.redactHeader(HEADER_TRAKT_API_KEY)
                    logging.redactHeader(HEADER_AUTHORIZATION)
                }
                builder.addNetworkInterceptor(logging)
            }
        }
    }

    protected open val trakt: TraktV2
        get() = Companion.trakt

    /**
     * Execute call with non-Void response body.
     */
    @Throws(IOException::class)
    open fun <T> executeCall(call: Call<T>): T {
        val response = call.execute()
        if (!response.isSuccessful) {
            handleFailedResponse(response) // will throw error
        }
        val body = response.body()
        return body ?: throw IllegalStateException("Body should not be null for successful response")
    }

    /**
     * Execute call with Void response body.
     */
    @Throws(IOException::class)
    fun <T> executeVoidCall(call: Call<T>) {
        val response = call.execute()
        if (!response.isSuccessful) {
            handleFailedResponse(response) // will throw error
        }
    }

    fun assertSuccessfulResponse(response: Response<*>) {
        if (!response.isSuccessful) {
            handleFailedResponse(response)
        }
    }

    private fun handleFailedResponse(response: Response<*>) {
        if (response.code() == 401) {
            Assertions.fail<Any>(
                "Authorization required, supply a valid OAuth access token: "
                        + response.code() + " " + response.message()
            )
        } else {
            var message = "Request failed: " + response.code() + " " + response.message()
            val error = trakt.checkForTraktError(response)
            if (error?.message != null) {
                message += " message: " + error.message
            }
            Assertions.fail<Any>(message)
        }
    }

    protected fun <T : BaseRatedEntity?> assertRatedEntities(ratedEntities: List<T>) {
        for (ratedEntity in ratedEntities) {
            Assertions.assertThat(ratedEntity?.rated_at).isNotNull
            Assertions.assertThat(ratedEntity?.rating).isNotNull
        }
    }

    fun assertRatings(ratings: Ratings) {
        // rating can be null, but we use a show where we can be sure it's rated
        Assertions.assertThat(ratings.rating).isGreaterThanOrEqualTo(0.0)
        Assertions.assertThat(ratings.votes).isGreaterThanOrEqualTo(0)
        Assertions.assertThat(ratings.distribution).hasSize(10)
    }

    fun assertStats(stats: Stats) {
        Assertions.assertThat(stats.watchers).isGreaterThanOrEqualTo(0)
        Assertions.assertThat(stats.plays).isGreaterThanOrEqualTo(0)
        Assertions.assertThat(stats.collectors).isGreaterThanOrEqualTo(0)
        Assertions.assertThat(stats.comments).isGreaterThanOrEqualTo(0)
        Assertions.assertThat(stats.lists).isGreaterThanOrEqualTo(0)
        Assertions.assertThat(stats.votes).isGreaterThanOrEqualTo(0)
    }

    fun assertShowStats(stats: Stats) {
        assertStats(stats)
        Assertions.assertThat(stats.collected_episodes).isGreaterThanOrEqualTo(0)
    }

    protected fun assertSyncMovies(movies: List<BaseMovie>, type: String?) {
        for (movie in movies) {
            Assertions.assertThat(movie.movie).isNotNull
            when (type) {
                "collection" -> Assertions.assertThat(movie.collected_at).isNotNull
                "watched" -> {
                    Assertions.assertThat(movie.plays).isPositive
                    Assertions.assertThat(movie.last_watched_at).isNotNull
                    Assertions.assertThat(movie.last_updated_at).isNotNull
                }
                "watchlist" -> Assertions.assertThat(movie.listed_at).isNotNull
            }
        }
    }

    protected fun assertSyncShows(shows: List<BaseShow>, type: String) {
        for (show in shows) {
            Assertions.assertThat(show.show).isNotNull
            if ("collection" == type) {
                Assertions.assertThat(show.last_collected_at).isNotNull
            } else if ("watched" == type) {
                Assertions.assertThat(show.plays).isPositive
                Assertions.assertThat(show.last_watched_at).isNotNull
                Assertions.assertThat(show.last_updated_at).isNotNull
            }
            for ((number, episodes) in show.seasons!!) {
                Assertions.assertThat(number).isGreaterThanOrEqualTo(0)
                for ((number1, collected_at, plays, last_watched_at) in episodes!!) {
                    Assertions.assertThat(number1).isGreaterThanOrEqualTo(0)
                    if ("collection" == type) {
                        Assertions.assertThat(collected_at).isNotNull
                    } else if ("watched" == type) {
                        Assertions.assertThat(plays).isPositive
                        Assertions.assertThat(last_watched_at).isNotNull
                    }
                }
            }
        }
    }

    fun assertCast(credits: Credits, type: Type) {
        for ((character, movie, show, person) in credits.cast!!) {
            Assertions.assertThat(character).isNotNull
            if (type === Type.SHOW) {
                Assertions.assertThat(movie).isNull()
                Assertions.assertThat(show).isNotNull
                Assertions.assertThat(person).isNull()
            } else if (type === Type.MOVIE) {
                Assertions.assertThat(movie).isNotNull
                Assertions.assertThat(show).isNull()
                Assertions.assertThat(person).isNull()
            } else if (type === Type.PERSON) {
                Assertions.assertThat(movie).isNull()
                Assertions.assertThat(show).isNull()
                Assertions.assertThat(person).isNotNull
            }
        }
    }

    fun assertCrew(credits: Credits, type: Type) {
        credits.crew?.run {
            assertCrewMembers(this.production, type)
            assertCrewMembers(this.writing, type)
            assertCrewMembers(this.directing, type)
            assertCrewMembers(this.costumeAndMakeUp, type)
            assertCrewMembers(this.sound, type)
            assertCrewMembers(this.art, type)
            assertCrewMembers(this.camera, type)
        }
    }

    fun assertCrewMembers(crew: List<CrewMember>?, type: Type) {
        if (crew == null) {
            return
        }
        for ((job, movie, show, person) in crew) {
            Assertions.assertThat(job).isNotNull // may be empty, so not checking for now
            if (type === Type.SHOW) {
                Assertions.assertThat(movie).isNull()
                Assertions.assertThat(show).isNotNull
                Assertions.assertThat(person).isNull()
            } else if (type === Type.MOVIE) {
                Assertions.assertThat(movie).isNotNull
                Assertions.assertThat(show).isNull()
                Assertions.assertThat(person).isNull()
            } else if (type === Type.PERSON) {
                Assertions.assertThat(movie).isNull()
                Assertions.assertThat(show).isNull()
                Assertions.assertThat(person).isNotNull
            }
        }
    }

    protected fun assertAccessTokenResponse(response: Response<AccessToken>) {
        assertSuccessfulResponse(response)
        Assertions.assertThat(response.body()!!.access_token).isNotEmpty
        Assertions.assertThat(response.body()!!.refresh_token).isNotEmpty
        Assertions.assertThat(response.body()!!.created_at).isPositive
        Assertions.assertThat(response.body()!!.expires_in).isPositive
        println("Retrieved access token: " + response.body()!!.access_token)
        println("Retrieved refresh token: " + response.body()!!.refresh_token)
        println("Retrieved scope: " + response.body()!!.scope)
        println("Retrieved expires in: " + response.body()!!.expires_in + " seconds")
    }

    @Throws(IOException::class)
    open fun <T> executeCallWithoutReadingBody(call: Call<T>): Response<T> {
        val response = call.execute()
        if (!response.isSuccessful) {
            handleFailedResponse(response) // will throw error
        }
        return response
    }

    companion object {
        const val TEST_CLIENT_ID = "35a671df22d3d98b09aab1c0bc52977e902e696a7704cab94f4d12c2672041e4"
        const val TEST_ACCESS_TOKEN =
            "5c37b7d2c1ae43d3f0436c5d13a828eb81cae236afe24fbd27142f4111669d3f" // "sgtest" on production
        const val TEST_REFRESH_TOKEN =
            "b6319445408c7da1ded77cacc5232632bbcad44769339794bfb8c46ba0b155a8" // "sgtest" on production
        private const val DEBUG = true
        private val trakt: TraktV2 = TestTraktV2(TEST_CLIENT_ID)
        const val DEFAULT_PAGE_SIZE = 10

        const val APP_VERSION = "trakt-java-4"
        const val APP_DATE = "2014-10-15"

        @BeforeClass
        @JvmStatic
        fun setUpOnce() {
            Companion.trakt.accessToken(TEST_ACCESS_TOKEN)
        }
    }
}