package eu.kowalcze.michal.arch.clean.example.domain.model

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import java.time.LocalDate
import java.time.LocalTime

internal class DayScheduleTest {

    @Test
    fun `Should reserve a slot`() {
        // given
        val today = LocalDate.now()
        val givenSchedule =
            DaySchedule(today, listOf(Slot(reserved = false, start = LocalTime.MIN, end = LocalTime.MAX)))


        // when
        val daySchedule = givenSchedule.reserveSlot(0)

        // then
        daySchedule.slots[0].reserved shouldBe true
    }

    @Test
    fun `Should fail when slot is already reserved`() {
        // given
        val today = LocalDate.now()
        val givenSchedule =
            DaySchedule(today, listOf(Slot(reserved = true, start = LocalTime.MIN, end = LocalTime.MAX)))

        // expect
        val exception =
            shouldThrow<SlotAlreadyReservedException> { givenSchedule.reserveSlot(0) }

        exception.message shouldBe "Slot[0] is already reserved"
    }

    @Test
    fun `Should fail when slot index is out of `() {
        // given
        val today = LocalDate.now()
        val givenSchedule =
            DaySchedule(today, listOf(Slot(reserved = true, start = LocalTime.MIN, end = LocalTime.MAX)))

        // expect
        val exception =
            shouldThrow<InvalidSlotIndexException> { givenSchedule.reserveSlot(1) }

        exception.message shouldBe "Slot[1] is not available. Slots count is: 1"
    }
}