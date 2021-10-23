package eu.kowalcze.michal.arch.clean.example.domain.usecase

import eu.kowalcze.michal.arch.clean.example.domain.schedule.DaySchedule
import eu.kowalcze.michal.arch.clean.example.domain.schedule.DayScheduleCreator
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

private class InMemoryDayScheduleRepository : DayScheduleRepository {
    private val store: MutableMap<LocalDate, DaySchedule> = mutableMapOf()
    override fun get(scheduleDay: LocalDate): DaySchedule? = store[scheduleDay]

    override fun save(daySchedule: DaySchedule): DaySchedule {
        store[daySchedule.day] = daySchedule
        return daySchedule
    }

    fun itemsCount() = store.size
}