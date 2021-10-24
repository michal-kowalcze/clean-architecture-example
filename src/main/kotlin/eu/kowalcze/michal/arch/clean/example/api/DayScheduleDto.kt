package eu.kowalcze.michal.arch.clean.example.api

import eu.kowalcze.michal.arch.clean.example.domain.model.DaySchedule
import eu.kowalcze.michal.arch.clean.example.domain.model.Slot


// DaySchedule is from domain layer, so it does not know about DayScheduleDto - it is necessary to convert object here
fun DaySchedule.toApi(): DayScheduleDto = DayScheduleDto(day, slots.map { it.toApi() })

fun Slot.toApi(): SlotDto = SlotDto(reserved, start, end)