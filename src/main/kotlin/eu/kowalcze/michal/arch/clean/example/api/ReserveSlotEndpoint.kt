package eu.kowalcze.michal.arch.clean.example.api

import eu.kowalcze.michal.arch.clean.example.domain.model.SlotId
import eu.kowalcze.michal.arch.clean.example.domain.usecase.ReserveSlotUseCase
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PutMapping
import java.time.LocalDate
import javax.servlet.http.HttpServletResponse

@Controller
class ReserveSlotEndpoint(
    private val reserveSlotUseCase: ReserveSlotUseCase,
    private val useCaseExecutor: SpringUseCaseExecutor,
) {

    @PutMapping("/schedules/{localDate}/{index}", produces = ["application/json"], consumes = ["application/json"])
    fun getSchedules(@PathVariable localDate: String, @PathVariable index: Int): ResponseEntity<DayScheduleDto> {
        // convert to domain model
        val slotId = SlotId(LocalDate.parse(localDate), index)
        // execute domain action
        return useCaseExecutor.execute(useCase = reserveSlotUseCase,
            input = slotId,
            toApiConversion = {
                // convert to API
                val dayScheduleDto = it.toApi()
                UseCaseApiResult(HttpServletResponse.SC_ACCEPTED, dayScheduleDto)
            })
    }

}
