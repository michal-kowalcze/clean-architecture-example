package eu.kowalcze.michal.arch.clean.example.domain.model

import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import java.time.LocalDate

internal class DayScheduleCreatorTest {
    @Test
    fun `Should create standard set of shifts`() {
        // given
        val creator = DayScheduleCreator()
        val today = LocalDate.now()

        //when
        val daySchedule = creator.create(today)

        // then
        daySchedule.day shouldBe today
        daySchedule.slots shouldHaveSize DayScheduleCreator.SHIFTS_PER_DAY
    }
}