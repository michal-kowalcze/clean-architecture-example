package eu.kowalcze.michal.arch.clean.example.domain.schedule

import java.time.LocalDate


interface DayScheduleCreator {
    fun create(scheduleDay: LocalDate): DaySchedule
}