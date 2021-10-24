package eu.kowalcze.michal.arch.clean.example.api

import eu.kowalcze.michal.arch.clean.example.domain.usecase.GetScheduleUseCase
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import java.time.LocalDate
import java.time.LocalTime

@Controller
class GetScheduleEndpoint(private val getScheduleUseCase: GetScheduleUseCase) {

    @GetMapping("/schedules/{localDate}")
    fun getSchedules(@PathVariable localDate: String): DayScheduleDto {
        // convert to domain model
        val scheduleDay = LocalDate.parse(localDate)
        // execute domain action
        val daySchedule = getScheduleUseCase.getSchedule(scheduleDay)
        // convert to API
        return daySchedule.toApi()
    }

}

data class DayScheduleDto(
    val day: LocalDate,
    val slots: List<SlotDto>
)

data class SlotDto(
    val reserved: Boolean,
    val start: LocalTime,
    val end: LocalTime,
)