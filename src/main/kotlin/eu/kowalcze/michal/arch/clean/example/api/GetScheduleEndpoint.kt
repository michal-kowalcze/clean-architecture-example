package eu.kowalcze.michal.arch.clean.example.api

import eu.kowalcze.michal.arch.clean.example.domain.usecase.GetScheduleUseCase
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import java.time.LocalDate
import java.time.LocalTime
import javax.servlet.http.HttpServletResponse

@Controller
class GetScheduleEndpoint(
    private val getScheduleUseCase: GetScheduleUseCase,
    private val useCaseExecutor: SpringUseCaseExecutor,
) {

    @GetMapping("/schedules/{localDate}", produces = ["application/json"])
    fun getSchedules(@PathVariable localDate: String): ResponseEntity<*> {
        // convert to domain model
        val scheduleDay = LocalDate.parse(localDate)

        // execute domain action
        return useCaseExecutor.execute(
            useCase = getScheduleUseCase,
            input = scheduleDay,
            toApiConversion = {
                // convert to API
                val dayScheduleDto = it.toApi()
                UseCaseApiResult(HttpServletResponse.SC_OK, dayScheduleDto)
            },
        )
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