package eu.kowalcze.michal.arch.clean.example.domain.usecase

import eu.kowalcze.michal.arch.clean.example.domain.model.DaySchedule
import eu.kowalcze.michal.arch.clean.example.domain.model.DayScheduleCreator
import eu.kowalcze.michal.arch.clean.example.domain.model.Slot
import eu.kowalcze.michal.arch.clean.example.domain.model.SlotId
import eu.kowalcze.michal.arch.clean.example.infrastructure.InMemoryDayScheduleRepository
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeSameInstanceAs
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.time.LocalDate
import java.time.LocalTime

internal class ReserveSlotUseCaseTest {
    private lateinit var daySchedulerRepository: InMemoryDayScheduleRepository
    private lateinit var dayScheduleCreator: DayScheduleCreator
    private lateinit var getScheduleUseCase: GetScheduleUseCase
    private lateinit var reserveSlotUseCase: ReserveSlotUseCase

    @BeforeEach
    fun setup() {
        dayScheduleCreator = mockk(relaxed = true)
        daySchedulerRepository = InMemoryDayScheduleRepository()
        getScheduleUseCase = GetScheduleUseCase(daySchedulerRepository, dayScheduleCreator)
        reserveSlotUseCase = ReserveSlotUseCase(daySchedulerRepository, getScheduleUseCase)
    }

    @Test
    fun `Should reserve a slot`() {
        // given
        val today = LocalDate.now()
        val givenSchedule =
            DaySchedule(today, listOf(Slot(reserved = false, start = LocalTime.MIN, end = LocalTime.MAX)))
        every { dayScheduleCreator.create(today) }.returns(givenSchedule)

        // when
        val daySchedule = reserveSlotUseCase.reserve(SlotId(today, index = 0))

        // then
        daySchedule.slots[0].reserved shouldBe true
        daySchedulerRepository.itemsCount() shouldBe 1
        daySchedulerRepository.get(today) shouldBeSameInstanceAs daySchedule
    }

}