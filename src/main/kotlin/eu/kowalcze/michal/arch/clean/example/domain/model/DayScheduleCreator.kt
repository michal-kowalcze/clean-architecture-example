package eu.kowalcze.michal.arch.clean.example.domain.model

import java.time.LocalDate
import java.time.LocalTime
import java.util.stream.IntStream
import kotlin.streams.toList


class DayScheduleCreator {
    fun create(scheduleDay: LocalDate): DaySchedule = DaySchedule(scheduleDay, createStandardSlots())

    private fun createStandardSlots(): List<Slot> {
        val firstShiftStart = LocalTime.of(8, 0, 0)
        val shiftDurationMinutes = 120

        return IntStream.range(0, SHIFTS_PER_DAY)
            .mapToObj { firstShiftStart.plusMinutes((it * shiftDurationMinutes).toLong()) }
            .map { Slot(reserved = false, start = it, end = it.plusMinutes(shiftDurationMinutes.toLong())) }
            .toList()
    }

    companion object {
        const val SHIFTS_PER_DAY = 6
    }
}