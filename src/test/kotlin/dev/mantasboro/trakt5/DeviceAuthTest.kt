package dev.mantasboro.trakt5

import org.assertj.core.api.Assertions
import org.junit.Test
import java.io.IOException

/**
 * This test should NOT be run with the regular test suite. It requires a valid, temporary (!) auth code to be set.
 */
class DeviceAuthTest : BaseTestCase() {

    @Test
    @Throws(IOException::class)
    fun test_generateDeviceCode() {
        if (TEST_CLIENT_ID.isEmpty()) {
            print("Skipping test_generateDeviceCode test, no valid client id")
            return
        }
        val codeResponse = trakt.generateDeviceCode()
        assertSuccessfulResponse(codeResponse)
        val deviceCode = codeResponse.body()
        Assertions.assertThat(deviceCode!!.device_code).isNotEmpty
        Assertions.assertThat(deviceCode.user_code).isNotEmpty
        Assertions.assertThat(deviceCode.verification_url).isNotEmpty
        Assertions.assertThat(deviceCode.expires_in).isPositive
        Assertions.assertThat(deviceCode.interval).isPositive
        println("Device Code: " + deviceCode.device_code)
        println("User Code: " + deviceCode.user_code)
        println("Enter the user code at the following URI: " + deviceCode.verification_url)
        println("Set the TEST_DEVICE_CODE variable to run the access token test")
    }

    @Test
    @Throws(IOException::class)
    fun test_getAccessToken() {
        if (TEST_CLIENT_SECRET.isEmpty() || TEST_DEVICE_CODE.isEmpty()) {
            print("Skipping test_getAccessToken test, no valid auth data")
            return
        }
        val response = trakt.exchangeDeviceCodeForAccessToken(TEST_DEVICE_CODE)
        assertAccessTokenResponse(response)
    }

    companion object {
        private const val TEST_CLIENT_SECRET = ""
        private const val TEST_DEVICE_CODE = ""

        // The Redirect URI is not used in OAuth device authentication.
        // Set the default as the out-of-band URI used during standard OAuth.
        private const val TEST_REDIRECT_URI = "urn:ietf:wg:oauth:2.0:oob"
        private val trakt: TraktV2 = TestTraktV2(TEST_CLIENT_ID, TEST_CLIENT_SECRET, TEST_REDIRECT_URI)
    }
}