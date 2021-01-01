package dev.mantasboro.trakt5.services

import dev.mantasboro.trakt5.BaseTestCase
import dev.mantasboro.trakt5.enums.Extended
import dev.mantasboro.trakt5.enums.Type
import org.assertj.core.api.Assertions
import org.junit.Test
import java.io.IOException

class PeopleTest : BaseTestCase() {
    @Test
    @Throws(IOException::class)
    fun test_summary() {
        val person = executeCall(trakt.people().summary(TEST_PERSON_SLUG, Extended.FULL))
        Assertions.assertThat(person).isNotNull
        Assertions.assertThat(person.name).isNotEmpty
        Assertions.assertThat(person.ids).isNotNull
        Assertions.assertThat(person.ids!!.trakt).isNotNull
        Assertions.assertThat(person.ids!!.slug).isNotNull
    }

    @Test
    @Throws(IOException::class)
    fun test_movieCredits() {
        val credits = executeCall(trakt.people().movieCredits(TEST_PERSON_SLUG))
        assertCast(credits, Type.MOVIE)
        assertCrew(credits, Type.MOVIE)
    }

    @Test
    @Throws(IOException::class)
    fun test_showCredits() {
        val credits = executeCall(trakt.people().showCredits(TEST_PERSON_SLUG))
        assertCast(credits, Type.SHOW)
        assertCrew(credits, Type.SHOW)
    }

    companion object {
        private const val TEST_PERSON_SLUG = "bryan-cranston"
    }
}