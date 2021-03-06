package dev.mantasboro.trakt5

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dev.mantasboro.trakt5.entities.AccessToken
import dev.mantasboro.trakt5.entities.AccessTokenRefreshRequest
import dev.mantasboro.trakt5.entities.AccessTokenRequest
import dev.mantasboro.trakt5.entities.CheckinError
import dev.mantasboro.trakt5.entities.ClientId
import dev.mantasboro.trakt5.entities.DeviceCode
import dev.mantasboro.trakt5.entities.DeviceCodeAccessTokenRequest
import dev.mantasboro.trakt5.entities.TraktError
import dev.mantasboro.trakt5.entities.TraktOAuthError
import dev.mantasboro.trakt5.services.Authentication
import dev.mantasboro.trakt5.services.Calendars
import dev.mantasboro.trakt5.services.Checkin
import dev.mantasboro.trakt5.services.Comments
import dev.mantasboro.trakt5.services.Episodes
import dev.mantasboro.trakt5.services.Genres
import dev.mantasboro.trakt5.services.Movies
import dev.mantasboro.trakt5.services.People
import dev.mantasboro.trakt5.services.Recommendations
import dev.mantasboro.trakt5.services.Scrobble
import dev.mantasboro.trakt5.services.Search
import dev.mantasboro.trakt5.services.Seasons
import dev.mantasboro.trakt5.services.Shows
import dev.mantasboro.trakt5.services.Sync
import dev.mantasboro.trakt5.services.Users
import okhttp3.OkHttpClient
import okhttp3.OkHttpClient.Builder
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.io.IOException
import java.io.UnsupportedEncodingException
import java.net.URLEncoder

/**
 * Helper class for easy usage of the trakt v2 API using retrofit.
 */
@Suppress("TooManyFunctions")
open class TraktV2 {
    private var okHttpClient: OkHttpClient? = null
    private var retrofit: Retrofit? = null
    private var apiKey: String
    private var clientSecret: String? = null
    private var redirectUri: String? = null
    private var accessToken: String? = null
    private var refreshToken: String? = null

    /**
     * Get a new API manager instance.
     *
     * @param apiKey The API key obtained from trakt, currently equal to the OAuth client id.
     */
    constructor(apiKey: String) {
        this.apiKey = apiKey
    }

    /**
     * Get a new API manager instance capable of calling OAuth2 protected endpoints.
     *
     * @param apiKey       The API key obtained from trakt, currently equal to the OAuth client id.
     * @param clientSecret The client secret obtained from trakt.
     * @param redirectUri  The redirect URI to use for OAuth2 token requests.
     */
    constructor(apiKey: String, clientSecret: String?, redirectUri: String?) {
        this.apiKey = apiKey
        this.clientSecret = clientSecret
        this.redirectUri = redirectUri
    }

    fun apiKey(): String {
        return apiKey
    }

    fun apiKey(apiKey: String): TraktV2 {
        this.apiKey = apiKey
        return this
    }

    fun accessToken(): String? {
        return accessToken
    }

    /**
     * Sets the OAuth 2.0 access token to be appended to requests.
     *
     *
     *  If set, some methods will return user-specific data.
     *
     * @param accessToken A valid access token, obtained via e.g. [.exchangeCodeForAccessToken].
     */
    fun accessToken(accessToken: String?): TraktV2 {
        this.accessToken = accessToken
        return this
    }

    fun refreshToken(): String? {
        return refreshToken
    }

    /**
     * Sets the OAuth 2.0 refresh token to be used, in case the current access token has expired, to get a new access
     * token.
     */
    fun refreshToken(refreshToken: String?): TraktV2 {
        this.refreshToken = refreshToken
        return this
    }

    /**
     * Creates a [Retrofit.Builder] that sets the base URL, adds a Gson converter and sets [.okHttpClient]
     * as its client.
     *
     * @see .okHttpClient
     */
    protected fun retrofitBuilder(): Retrofit.Builder {
        val moshi = Moshi.Builder()
            .add(TraktV2Adapters())
            .addLast(KotlinJsonAdapterFactory())
            .build()

        return Retrofit.Builder()
            .baseUrl(API_URL)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .client(okHttpClient())
    }

    /**
     * Returns the default OkHttp client instance. It is strongly recommended to override this and use your app
     * instance.
     *
     * @see .setOkHttpClientDefaults
     */
    @Synchronized
    protected fun okHttpClient(): OkHttpClient {
        if (okHttpClient == null) {
            val builder = Builder()
            setOkHttpClientDefaults(builder)
            okHttpClient = builder.build()
        }
        return okHttpClient!!
    }

    /**
     * Adds [dev.mantasboro.trakt5.TraktV2Interceptor] as an application interceptor and
     * [dev.mantasboro.trakt5.TraktV2Authenticator] as an authenticator.
     */
    protected open fun setOkHttpClientDefaults(builder: Builder) {
        builder.addInterceptor(TraktV2Interceptor(this))
        builder.authenticator(TraktV2Authenticator(this))
    }

    /**
     * Return the [Retrofit] instance. If called for the first time builds the instance.
     */
    protected fun retrofit(): Retrofit? {
        if (retrofit == null) {
            retrofit = retrofitBuilder().build()
        }
        return retrofit
    }

    /**
     * Build an OAuth 2.0 authorization URL to obtain an authorization code.
     *
     *
     * Send the user to the URL. Once the user authorized your app, the server will redirect to `redirectUri`
     * with the authorization code and the sent state in the query parameter `code`.
     *
     *
     * Ensure the state matches, then supply the authorization code to [.exchangeCodeForAccessToken] to get an
     * access token.
     *
     * @param state State variable to prevent request forgery attacks.
     */
    fun buildAuthorizationUrl(state: String): String {
        checkNotNull(redirectUri) { "redirectUri not provided" }
        val authUrl = StringBuilder(OAUTH2_AUTHORIZATION_URL)
        authUrl.append("?").append("response_type=code")
        authUrl.append("&").append("redirect_uri=").append(urlEncode(redirectUri!!))
        authUrl.append("&").append("state=").append(urlEncode(state))
        authUrl.append("&").append("client_id=").append(urlEncode(apiKey()))
        return authUrl.toString()
    }

    private fun urlEncode(content: String): String {
        return try {
            // can not use java.nio.charset.StandardCharsets as on Android only available since API 19
            URLEncoder.encode(content, "UTF-8")
        } catch (e: UnsupportedEncodingException) {
            throw UnsupportedOperationException(e)
        }
    }

    /**
     * Request a code to start the device authentication process from trakt.
     *
     *
     * The `device_code` and `interval` will be used later to poll for the `access_token`.
     * The `user_code` and `verification_url` should be presented to the user.
     */
    @Throws(IOException::class)
    fun generateDeviceCode(): Response<DeviceCode> {
        val clientId = ClientId(apiKey)
        return authentication().generateDeviceCode(clientId).execute()
    }

    /**
     * Request an access token from trakt using device authentication.
     *
     *
     * Supply the received access token to [.accessToken] and store the refresh token to later refresh
     * the access token once it has expired.
     *
     *
     * On failure re-authorization of your app is required (see [.generateDeviceCode]).
     *
     * @param deviceCode A valid device code (see [.generateDeviceCode]).
     */
    @Throws(IOException::class)
    fun exchangeDeviceCodeForAccessToken(deviceCode: String?): Response<AccessToken> {
        checkNotNull(clientSecret) { "clientSecret not provided" }
        val request = DeviceCodeAccessTokenRequest(deviceCode, apiKey, clientSecret)
        return authentication().exchangeDeviceCodeForAccessToken(request).execute()
    }

    /**
     * Request an access token from trakt.
     *
     *
     * Supply the received access token to [.accessToken] and store the refresh token to later refresh
     * the access token once it has expired.
     *
     *
     * On failure re-authorization of your app is required (see [.buildAuthorizationUrl]).
     *
     * @param authCode A valid authorization code (see [.buildAuthorizationUrl]).
     */
    @Throws(IOException::class)
    fun exchangeCodeForAccessToken(authCode: String?): Response<AccessToken> {
        checkNotNull(clientSecret) { "clientSecret not provided" }
        checkNotNull(redirectUri) { "redirectUri not provided" }
        return authentication().exchangeCodeForAccessToken(
            AccessTokenRequest(
                authCode!!,
                apiKey(),
                clientSecret!!,
                redirectUri!!
            )
        ).execute()
    }

    /**
     * Request to refresh an expired access token for trakt. If your app is still authorized, returns a response which
     * includes a new access token.
     *
     *
     * Supply the received access token to [.accessToken] and store the refresh token to later refresh
     * the access token once it has expired.
     *
     *
     * On failure re-authorization of your app is required (see [.buildAuthorizationUrl]).
     */
    @Throws(IOException::class)
    fun refreshAccessToken(refreshToken: String?): Response<AccessToken> {
        checkNotNull(clientSecret) { "clientSecret not provided" }
        checkNotNull(redirectUri) { "redirectUri not provided" }
        return authentication().refreshAccessToken(
            AccessTokenRefreshRequest(
                refreshToken!!,
                apiKey(),
                clientSecret!!,
                redirectUri!!
            )
        ).execute()
    }

    /**
     * If the response code is 409 tries to convert the body into a [CheckinError].
     */
    fun checkForCheckinError(response: Response<*>): CheckinError? {
        if (response.code() != 409) {
            return null // only code 409 can be a check-in error
        }
        val errorConverter = retrofit()!!.responseBodyConverter<CheckinError>(
            CheckinError::class.java, arrayOfNulls(0)
        )
        return try {
            errorConverter.convert(response.errorBody())
        } catch (e: IOException) {
            CheckinError() // null values
        }
    }

    /**
     * If the response is not successful, tries to parse the error body into a [TraktError].
     */
    fun checkForTraktError(response: Response<*>): TraktError? {
        if (response.isSuccessful) {
            return null
        }
        val errorConverter = retrofit()!!.responseBodyConverter<TraktError>(
            TraktError::class.java, arrayOfNulls(0)
        )
        return try {
            errorConverter.convert(response.errorBody())
        } catch (ignored: IOException) {
            TraktError() // null values
        }
    }

    /**
     * If the [Authentication] response is not successful,
     * tries to parse the error body into a [TraktOAuthError].
     */
    fun checkForTraktOAuthError(response: Response<*>): TraktOAuthError? {
        if (response.isSuccessful) {
            return null
        }
        val errorConverter =
            retrofit()!!.responseBodyConverter<TraktOAuthError>(TraktOAuthError::class.java, arrayOfNulls(0))
        if (response.errorBody() != null) {
            try {
                return errorConverter.convert(response.errorBody()!!)
            } catch (ignored: IOException) {
            }
        }
        return TraktOAuthError() // null values
    }

    fun authentication(): Authentication {
        return retrofit()!!.create(Authentication::class.java)
    }

    /**
     * By default, the calendar will return all shows or movies for the specified time period. If OAuth is sent, the
     * items returned will be limited to what the user has watched, collected, or added to their watchlist. You'll most
     * likely want to send OAuth to make the calendar more relevant to the user.
     */
    fun calendars(): Calendars {
        return retrofit()!!.create(Calendars::class.java)
    }

    /**
     * Checking in is a manual process used by mobile apps. While not as effortless as scrobbling, checkins help fill in
     * the gaps. You might be watching live tv, at a friend's house, or watching a movie in theaters. You can simply
     * checkin from your phone or tablet in those situations.
     */
    fun checkin(): Checkin {
        return retrofit()!!.create(Checkin::class.java)
    }

    /**
     * Comments are attached to any movie, show, season, episode, or list and can be shorter shouts or more in depth
     * reviews. Each comment can have replies and can be voted up or down. These votes are used to determine popular
     * comments.
     */
    fun comments(): Comments {
        return retrofit()!!.create(Comments::class.java)
    }

    /**
     * One or more genres are attached to all movies and shows. Some API methods allow filtering by genre, so it's good
     * to cache this list in your app.
     */
    fun genres(): Genres {
        return retrofit()!!.create(Genres::class.java)
    }

    fun movies(): Movies {
        return retrofit()!!.create(Movies::class.java)
    }

    fun people(): People {
        return retrofit()!!.create(People::class.java)
    }

    /**
     * Recommendations are based on the watched history for a user and their friends. There are other factors that go
     * into the algorithm as well to further personalize what gets recommended.
     */
    fun recommendations(): Recommendations {
        return retrofit()!!.create(Recommendations::class.java)
    }

    /**
     * Searches can use queries or ID lookups. Queries will search fields like the title and description. ID lookups are
     * helpful if you have an external ID and want to get the trakt ID and info. This method will search for movies,
     * shows, episodes, people, users, and lists.
     */
    fun search(): Search {
        return retrofit()!!.create(Search::class.java)
    }

    fun shows(): Shows {
        return retrofit()!!.create(Shows::class.java)
    }

    fun seasons(): Seasons {
        return retrofit()!!.create(Seasons::class.java)
    }

    fun episodes(): Episodes {
        return retrofit()!!.create(Episodes::class.java)
    }

    fun sync(): Sync {
        return retrofit()!!.create(Sync::class.java)
    }

    fun scrobble(): Scrobble {
        return retrofit()!!.create(Scrobble::class.java)
    }

    fun users(): Users {
        return retrofit()!!.create(Users::class.java)
    }

    companion object {
        /**
         * trakt API v2 URL.
         */
        const val API_HOST = "api.trakt.tv"
        const val API_URL = "https://$API_HOST/"
        const val API_VERSION = "2"
        const val SITE_URL = "https://trakt.tv"
        const val OAUTH2_AUTHORIZATION_URL = "$SITE_URL/oauth/authorize"
        const val HEADER_AUTHORIZATION = "Authorization"
        const val HEADER_CONTENT_TYPE = "Content-Type"
        const val CONTENT_TYPE_JSON = "application/json"
        const val HEADER_TRAKT_API_VERSION = "trakt-api-version"
        const val HEADER_TRAKT_API_KEY = "trakt-api-key"
    }
}
