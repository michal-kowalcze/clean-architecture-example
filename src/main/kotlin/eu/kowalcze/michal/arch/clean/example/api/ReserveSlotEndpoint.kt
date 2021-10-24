package eu.kowalcze.michal.arch.clean.example.api

import eu.kowalcze.michal.arch.clean.example.domain.model.SlotId
import eu.kowalcze.michal.arch.clean.example.domain.usecase.ReserveSlotUseCase
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PutMapping
import java.time.LocalDate

@Controller
class ReserveSlotEndpoint(
    private val reserveSlotUseCase: ReserveSlotUseCase,
    private val useCaseExecutor: UseCaseExecutor,
) {

    @PutMapping("/schedules/{localDate}/{index}", produces = ["application/json"], consumes = ["application/json"])
    fun getSchedules(@PathVariable localDate: String, @PathVariable index: Int): ResponseEntity<DayScheduleDto>? {
        // convert to domain model
        val slotId = SlotId(LocalDate.parse(localDate), index)
        // execute domain action
        val daySchedule = useCaseExecutor.execute(reserveSlotUseCase, slotId)
        // convert to API
        val dayScheduleDto = daySchedule.toApi()

        return ResponseEntity.accepted().body(dayScheduleDto)

    }

}
