package dev.mantasboro.trakt5

import dev.mantasboro.trakt5.TraktV2
import org.assertj.core.api.Assertions
import org.junit.Test
import java.io.IOException
import java.math.BigInteger
import java.security.SecureRandom

/**
 * This test should NOT be run with the regular test suite. It requires a valid, temporary (!) auth code to be set.
 */
class AuthTest : BaseTestCase() {

    companion object {
        private const val TEST_CLIENT_SECRET = ""
        private const val TEST_AUTH_CODE = ""
        private const val TEST_REFRESH_TOKEN = ""
        private const val TEST_REDIRECT_URI = "http://localhost"
        private val trakt: TraktV2 = TestTraktV2(TEST_CLIENT_ID, TEST_CLIENT_SECRET, TEST_REDIRECT_URI)
    }

    override val trakt: TraktV2 = Companion.trakt

    @Test
    fun test_getAuthorizationRequest() {
        val sampleState = BigInteger(130, SecureRandom()).toString(32)
        val authUrl = trakt.buildAuthorizationUrl(sampleState)
        Assertions.assertThat(authUrl).isNotEmpty
        Assertions.assertThat(authUrl).startsWith(TraktV2.OAUTH2_AUTHORIZATION_URL)
        // trakt does not support scopes, so don't send one (server sets default scope)
        Assertions.assertThat(authUrl).doesNotContain("scope")
        println("Get an auth code at the following URI: $authUrl")
    }

    @Test
    @Throws(IOException::class)
    fun test_getAccessToken() {
        if (TEST_CLIENT_SECRET.isEmpty() || TEST_AUTH_CODE.isEmpty()) {
            print("Skipping test_getAccessTokenRequest test, no valid auth data")
            return
        }
        val response = trakt.exchangeCodeForAccessToken(TEST_AUTH_CODE)
        assertAccessTokenResponse(response)
    }

    @Test
    @Throws(IOException::class)
    fun test_refreshAccessToken() {
        if (TEST_CLIENT_SECRET.isEmpty() || TEST_REFRESH_TOKEN.isEmpty()) {
            print("Skipping test_refreshAccessToken test, no valid auth data")
            return
        }
        val response = trakt.refreshAccessToken(trakt.refreshToken())
        assertAccessTokenResponse(response)
    }
}