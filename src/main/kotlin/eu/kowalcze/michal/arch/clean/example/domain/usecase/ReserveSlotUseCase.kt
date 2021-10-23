package eu.kowalcze.michal.arch.clean.example.domain.usecase

import eu.kowalcze.michal.arch.clean.example.domain.model.DaySchedule
import eu.kowalcze.michal.arch.clean.example.domain.model.SlotId
import eu.kowalcze.michal.arch.clean.example.domain.repository.DayScheduleRepository

class ReserveSlotUseCase(
    private val dayScheduleRepository: DayScheduleRepository,
    private val getScheduleUseCase: GetScheduleUseCase,
) {

    fun reserve(slotId: SlotId): DaySchedule {
        val daySchedule = getScheduleUseCase.getSchedule(scheduleDay = slotId.day)

        val modifiedSchedule = daySchedule.reserveSlot(slotId.index)

        return dayScheduleRepository.save(modifiedSchedule)
    }
}

