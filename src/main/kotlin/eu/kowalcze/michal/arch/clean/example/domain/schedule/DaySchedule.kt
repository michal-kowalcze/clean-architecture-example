package eu.kowalcze.michal.arch.clean.example.domain.schedule

import java.time.LocalDate
import java.time.LocalTime


data class DaySchedule(
    val day: LocalDate,
    val slots: List<Slot>,
)

data class Slot(
    val occupied: Boolean,
    val start: LocalTime,
    val end: LocalTime,
)