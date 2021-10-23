package eu.kowalcze.michal.arch.clean.example.domain.repository

import eu.kowalcze.michal.arch.clean.example.domain.model.DaySchedule
import java.time.LocalDate


interface DayScheduleRepository {
    fun get(scheduleDay: LocalDate): DaySchedule?
    fun save(daySchedule: DaySchedule): DaySchedule
}
