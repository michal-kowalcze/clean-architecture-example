package eu.kowalcze.michal.arch.clean.example.api

import eu.kowalcze.michal.arch.clean.example.domain.model.SlotId
import eu.kowalcze.michal.arch.clean.example.domain.usecase.ReserveSlotUseCase
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PutMapping
import java.time.LocalDate

@Controller
class ReserveSlotEndpoint(private val reserveSlotUseCase: ReserveSlotUseCase) {

    @PutMapping("/schedules/{localDate}/{index}")
    fun getSchedules(@PathVariable localDate: String, @PathVariable index: Int): DayScheduleDto {
        val slotId = SlotId(LocalDate.parse(localDate), index)
        val daySchedule = reserveSlotUseCase.reserve(slotId)
        return daySchedule.toApi()
    }

}
