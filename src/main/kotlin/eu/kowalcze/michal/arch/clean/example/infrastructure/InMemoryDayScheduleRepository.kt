package eu.kowalcze.michal.arch.clean.example.infrastructure

import eu.kowalcze.michal.arch.clean.example.domain.model.DaySchedule
import eu.kowalcze.michal.arch.clean.example.domain.repository.DayScheduleRepository
import java.time.LocalDate

class InMemoryDayScheduleRepository : DayScheduleRepository {
    private val store: MutableMap<LocalDate, DaySchedule> = mutableMapOf()
    override fun get(scheduleDay: LocalDate): DaySchedule? = store[scheduleDay]

    override fun save(daySchedule: DaySchedule): DaySchedule {
        store[daySchedule.day] = daySchedule
        return daySchedule
    }

    fun itemsCount() = store.size
}