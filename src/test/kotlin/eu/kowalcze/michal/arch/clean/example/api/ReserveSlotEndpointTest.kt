package eu.kowalcze.michal.arch.clean.example.api

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import java.time.LocalDate

internal class ReserveSlotEndpointTest : BaseIntegrationTest() {
    @Test
    fun `Should retrieve schedule for today`() {
        // given
        val today = LocalDate.now()
        val todayStr = today.toString()

        // when
        val responseEntity = reserveSlot(todayStr, 0)

        // then
        responseEntity.statusCode shouldBe HttpStatus.ACCEPTED
        responseEntity.body!!.slots!![0].reserved shouldBe "true"
        responseEntity.body!!.slots!![1].reserved shouldBe "false"
    }

    @Test
    fun `Should fail when index is out of range`() {
        // given
        val today = LocalDate.now()
        val todayStr = today.toString()

        // when
        val responseEntity = reserveSlotAndExpectError(todayStr, 10)

        // then
        responseEntity.statusCode shouldBe HttpStatus.UNPROCESSABLE_ENTITY
        responseEntity.body!! shouldBe "INVALID-SLOT-ID"
    }


    @Test
    fun `Should fail when slot is already occupied`() {
        // given
        val today = LocalDate.now()
        val todayStr = today.toString()
        reserveSlot(todayStr, 1)
        // when
        val responseEntity = reserveSlotAndExpectError(todayStr, 1)

        // then
        responseEntity.statusCode shouldBe HttpStatus.CONFLICT
        responseEntity.body!! shouldBe "SLOT-ALREADY-RESERVED"
    }

    private fun reserveSlot(
        todayStr: String,
        index: Int
    ) = restTemplate.exchange(
        localUrl("/schedules/$todayStr/$index"), HttpMethod.PUT,
        HttpEntity(null, jsonHeaders()),
        DayScheduleTestDto::class.java
    )

    private fun reserveSlotAndExpectError(
        todayStr: String,
        index: Int
    ) = restTemplate.exchange(
        localUrl("/schedules/$todayStr/$index"), HttpMethod.PUT,
        HttpEntity(null, jsonHeaders()),
        String::class.java
    )

    private fun jsonHeaders(): HttpHeaders {
        val headers = HttpHeaders()
        headers.put("accept", listOf("application/json"))
        headers.put("content-type", listOf("application/json"))
        return headers
    }
}