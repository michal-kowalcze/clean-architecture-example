package eu.kowalcze.michal.arch.clean.example.domain.usecase

import eu.kowalcze.michal.arch.clean.example.domain.schedule.DaySchedule
import java.time.LocalDate


interface DayScheduleRepository {
    fun get(scheduleDay: LocalDate): DaySchedule?
    fun save(daySchedule: DaySchedule): DaySchedule
}
