package eu.kowalcze.michal.arch.clean.example.api

import eu.kowalcze.michal.arch.clean.example.domain.model.InvalidSlotIndexException
import eu.kowalcze.michal.arch.clean.example.domain.model.SlotAlreadyReservedException
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
    fun getSchedules(@PathVariable localDate: String, @PathVariable index: Int): ResponseEntity<*> =
        useCaseExecutor.execute(
            useCase = reserveSlotUseCase,
            inputProvider = { SlotId(LocalDate.parse(localDate), index) },
            toApiConversion = {
                val dayScheduleDto = it.toApi()
                UseCaseApiResult(HttpServletResponse.SC_ACCEPTED, dayScheduleDto)
            },
            handledExceptions = {
                exception(InvalidSlotIndexException::class, 422, "INVALID-SLOT-ID")
                exception(
                    SlotAlreadyReservedException::class,
                    HttpServletResponse.SC_CONFLICT,
                    "SLOT-ALREADY-RESERVED"
                )
            },
        )

}
