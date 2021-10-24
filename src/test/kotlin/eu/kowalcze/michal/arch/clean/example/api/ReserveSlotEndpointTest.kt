package eu.kowalcze.michal.arch.clean.example.api

import eu.kowalcze.michal.arch.clean.example.domain.model.DayScheduleCreator
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import java.time.LocalDate

internal class ReserveSlotEndpointTest: BaseIntegrationTest() {
    @Test
    fun `Should retrieve schedule for today`() {
        // given
        val today = LocalDate.now()
        val todayStr = today.toString()
        val headers = HttpHeaders()
        headers.put("accept", listOf("application/json"))
        headers.put("content-type", listOf("application/json"))

        // when
        val responseEntity = restTemplate.exchange(
            localUrl("/schedules/$todayStr/0"), HttpMethod.PUT,
            HttpEntity(null, headers),
            DayScheduleTestDto::class.java
        )

        // then
        responseEntity.statusCode shouldBe HttpStatus.ACCEPTED
        responseEntity.body!!.slots!![0].reserved shouldBe "true"
        responseEntity.body!!.slots!![1].reserved shouldBe "false"
    }
}