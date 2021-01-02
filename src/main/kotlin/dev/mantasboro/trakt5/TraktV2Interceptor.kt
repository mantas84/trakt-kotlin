package dev.mantasboro.trakt5

import okhttp3.Interceptor
import okhttp3.Interceptor.Chain
import okhttp3.Request
import okhttp3.Request.Builder
import okhttp3.Response
import java.io.IOException

/**
 * Adds API key and version headers and if available an authorization header.
 * As it may retry requests (on HTTP 429 responses), ensure this is added as an application interceptor
 * (never a network interceptor), otherwise caching will be broken and requests will fail.
 * See [.handleIntercept].
 */
class TraktV2Interceptor(private val trakt: TraktV2) : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Chain): Response {
        return handleIntercept(chain, trakt.apiKey(), trakt.accessToken())
    }

    /**
     * If the host matches [TraktV2.API_HOST] adds a header for the current [TraktV2.API_VERSION],
     * [ ][TraktV2.HEADER_TRAKT_API_KEY] with the given api key, [TraktV2.HEADER_CONTENT_TYPE] and if not present an
     * Authorization header using the given access token.
     *
     * If a request fails due to HTTP 429 Too Many Requests, will retry the request after the time in seconds given
     * by the Retry-After response header.
     */
    @Throws(IOException::class)
    fun handleIntercept(
        chain: Chain,
        apiKey: String,
        accessToken: String?
    ): Response {
        val request: Request = chain.request()
        if (TraktV2.API_HOST != request.url.host) {
            // do not intercept requests for other hosts
            // this allows the interceptor to be used on a shared okhttp client
            return chain.proceed(request)
        }
        val builder: Builder = request.newBuilder()

        // set required content type, api key and request specific API version
        builder.header(TraktV2.HEADER_CONTENT_TYPE, TraktV2.CONTENT_TYPE_JSON)
        builder.header(TraktV2.HEADER_TRAKT_API_KEY, apiKey)
        builder.header(TraktV2.HEADER_TRAKT_API_VERSION, TraktV2.API_VERSION)

        // add authorization header
        if (hasNoAuthorizationHeader(request) && accessTokenIsNotEmpty(accessToken)) {
            builder.header(TraktV2.HEADER_AUTHORIZATION, "Bearer $accessToken")
        }

        // Try the request.
        val response: Response = chain.proceed(builder.build())

        // Handle Trakt rate limit errors https://trakt.docs.apiary.io/#introduction/rate-limiting
        if (response.code == 429 /* Too Many Requests */) {
            // Re-try if the server indicates we should.
            val retryHeader = response.header("Retry-After")
            if (retryHeader != null) {
                try {
                    val retry = retryHeader.toInt()
                    // Wait a little longer than the server indicates (+ half second).
                    Thread.sleep(((retry + 0.5) * 1000).toLong())

                    // Close body of unsuccessful response.
                    if (response.body != null) {
                        response.body!!.close()
                    }

                    // Try again.
                    // Is fine, because unlike a network interceptor, an application interceptor can re-try requests.
                    return handleIntercept(chain, apiKey, accessToken)
                } catch (ignored: NumberFormatException) {
                    // No valid Retry-After header or timed out, return failed response.
                } catch (ignored: InterruptedException) {
                }
            }
        }
        return response
    }

    private fun hasNoAuthorizationHeader(request: Request): Boolean {
        return request.header(TraktV2.HEADER_AUTHORIZATION) == null
    }

    private fun accessTokenIsNotEmpty(accessToken: String?): Boolean {
        return accessToken != null && accessToken.length != 0
    }
}
