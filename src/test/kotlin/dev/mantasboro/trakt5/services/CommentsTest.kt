package dev.mantasboro.trakt5.services

import dev.mantasboro.trakt5.BaseTestCase
import dev.mantasboro.trakt5.TestData
import dev.mantasboro.trakt5.entities.Comment
import dev.mantasboro.trakt5.entities.Episode
import dev.mantasboro.trakt5.entities.EpisodeIds
import org.assertj.core.api.Assertions
import org.junit.Test
import retrofit2.Response
import java.io.IOException
import java.net.HttpURLConnection

class CommentsTest : BaseTestCase() {
    @Test
    @Throws(InterruptedException::class, IOException::class)
    fun test_postAndUpdate() {
        // first post a new comment
        val comment = Comment(buildTestEpisode(), "This is a toasty comment!", true, false)
        var commentResponse = executeCall(trakt.comments().post(comment))

        // give the server some time to handle the data
        Thread.sleep(3000)

        // update the new comment
        val updatedComment = Comment("This is toasty! I was just updated.", false, false)
        commentResponse = executeCall(trakt.comments().update(commentResponse.id!!, updatedComment))
        assertCommentResponse(updatedComment, commentResponse)
        // give the server some time to handle the data
        Thread.sleep(3000)

        // delete the comment again
        val response: Response<*> = trakt.comments().delete(commentResponse.id!!).execute()
        assertSuccessfulResponse(response)
        Assertions.assertThat(response.code()).isEqualTo(HttpURLConnection.HTTP_NO_CONTENT)
    }

    @Test
    @Throws(InterruptedException::class, IOException::class)
    fun test_delete() {
        // first post a new comment
        val comment = Comment(buildTestEpisode(), "This is toasty! I should be deleted soon.", true, false)
        val commentResponse = executeCall(trakt.comments().post(comment))

        // give the server some time to handle the data
        Thread.sleep(3000)

        // delete the comment again
        val response: Response<*> = trakt.comments().delete(commentResponse.id!!).execute()
        assertSuccessfulResponse(response)
        Assertions.assertThat(response.code()).isEqualTo(HttpURLConnection.HTTP_NO_CONTENT)
    }

    @Test
    @Throws(InterruptedException::class, IOException::class)
    fun test_replies() {
        // first post a new comment
        val comment = Comment(buildTestEpisode(), "This is a toasty comment!", true, false)
        val response = executeCall(trakt.comments().post(comment))

        // give the server some time to handle the data
        Thread.sleep(3000)

        // post a reply to the new comment
        val expectedReply = Comment("This is a reply to the toasty comment!", false, false)
        val actualReply = executeCall(
            trakt.comments().postReply(
                response.id!!, expectedReply
            )
        )
        assertCommentResponse(expectedReply, actualReply)

        // give the server some time to handle the data
        Thread.sleep(3000)

        // look if the comment replies include our new reply
        val replies = executeCall(
            trakt.comments().replies(
                response.id!!
            )
        )
        for (reply in replies) {
            if (reply.id == actualReply.id) {
                assertCommentResponse(actualReply, reply)
            }
        }

        // delete the comment and replies (does this work?)
        val deleteResponse: Response<*> = trakt.comments().delete(response.id!!).execute()
        assertSuccessfulResponse(deleteResponse)
        Assertions.assertThat(deleteResponse.code()).isEqualTo(HttpURLConnection.HTTP_NO_CONTENT)
    }

    private fun assertCommentResponse(expected: Comment, actual: Comment) {
        Assertions.assertThat(actual.comment).isEqualTo(expected.comment)
        Assertions.assertThat(actual.spoiler).isEqualTo(expected.spoiler)
        Assertions.assertThat(actual.review).isEqualTo(expected.review)
    }

    private fun buildTestEpisode(): Episode {
        return Episode(ids = EpisodeIds(tvdb = TestData.EPISODE_TVDB_ID))
    }
}