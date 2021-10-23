package eu.kowalcze.michal.arch.clean.example.domain.usecase

import eu.kowalcze.michal.arch.clean.example.domain.model.DaySchedule
import eu.kowalcze.michal.arch.clean.example.domain.model.DayScheduleCreator
import eu.kowalcze.michal.arch.clean.example.domain.repository.DayScheduleRepository
import eu.kowalcze.michal.arch.clean.example.infrastructure.InMemoryDayScheduleRepository
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeSameInstanceAs
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.time.LocalDate

internal class GetScheduleUseCaseTest {
    private lateinit var daySchedulerRepository: InMemoryDayScheduleRepository
    private lateinit var dayScheduleCreator: DayScheduleCreator
    private lateinit var getScheduleUseCase: GetScheduleUseCase

    @BeforeEach
    fun setup() {
        dayScheduleCreator = mockk(relaxed = true)
        daySchedulerRepository = InMemoryDayScheduleRepository()
        getScheduleUseCase = GetScheduleUseCase(daySchedulerRepository, dayScheduleCreator)
    }

    @Test
    fun `Should retrieve an existing schedule`() {
        // given
        val today = LocalDate.now()
        val givenSchedule = DaySchedule(today, listOf())
        daySchedulerRepository.save(givenSchedule)

        // when
        val schedule = getScheduleUseCase.getSchedule(today)

        // then
        schedule shouldBeSameInstanceAs givenSchedule
        daySchedulerRepository.itemsCount() shouldBe 1
        daySchedulerRepository.get(today) shouldBeSameInstanceAs givenSchedule
        verify(exactly = 0) { dayScheduleCreator.create(any()) }
    }

    @Test
    fun `Should use created schedule`() {
        // given
        val today = LocalDate.now()
        val givenSchedule = DaySchedule(today, listOf())
        every { dayScheduleCreator.create(today) }.returns(givenSchedule)

        // when
        val schedule = getScheduleUseCase.getSchedule(today)

        // then
        schedule shouldBeSameInstanceAs givenSchedule
        daySchedulerRepository.itemsCount() shouldBe 1
        daySchedulerRepository.get(today) shouldBeSameInstanceAs givenSchedule
        verify { dayScheduleCreator.create(any()) }
    }
}

