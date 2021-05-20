package dev.mantasboro.trakt5

import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import java.io.IOException

/**
 * If required tries to obtain a new access and refresh tokens if a refresh token is available.
 * If so, replaces the existing tokens and updates the authentication header of the request.
 * See [.handleAuthenticate].
 */
class TraktV2Authenticator(val trakt: TraktV2) : Authenticator {
    @Throws(IOException::class)
    override fun authenticate(route: Route?, response: Response): Request? {
        return handleAuthenticate(response, trakt)
    }

    /**
     * If not doing a trakt [TraktV2.API_URL] request tries to refresh the access token with the refresh token.
     *
     * @param response The response passed to [.authenticate].
     * @param trakt The [TraktV2] instance to get the API key from and to set the updated JSON web token on.
     * @return A request with updated authorization header or null if no auth is possible.
     */
    @Throws(IOException::class)
    fun handleAuthenticate(response: Response, trakt: TraktV2): Request? {
        if (TraktV2.isHost(response.request.url.host)) {
            return null // not a trakt API endpoint (possibly trakt OAuth or other API), give up.
        }
        if (responseCount(response) >= 2) {
            return null // failed 2 times, give up.
        }
        val refreshToken = trakt.refreshToken()
        if (refreshToken == null || refreshToken.isEmpty()) {
            return null // have no refresh token, give up.
        }

        // try to refresh the access token with the refresh token
        val refreshResponse = trakt.refreshAccessToken(refreshToken)
        val body = refreshResponse.body()
        if (!refreshResponse.isSuccessful || body == null) {
            return null // failed to retrieve a token, give up.
        }

        // store the new tokens
        val accessToken = body.access_token
        trakt.accessToken(accessToken)
        trakt.refreshToken(body.refresh_token)

        // retry request
        return response.request.newBuilder()
            .header(TraktV2.HEADER_AUTHORIZATION, "Bearer $accessToken")
            .build()
    }

    companion object {

        private fun responseCount(response: Response): Int {
            var response = response
            var result = 1
            while (response.priorResponse.also { response = it!! } != null) {
                result++
            }
            return result
        }
    }
}
