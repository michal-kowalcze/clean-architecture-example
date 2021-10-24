package eu.kowalcze.michal.arch.clean.example.domain.usecase

import eu.kowalcze.michal.arch.clean.example.domain.model.DaySchedule
import eu.kowalcze.michal.arch.clean.example.domain.model.SlotId
import eu.kowalcze.michal.arch.clean.example.domain.repository.DayScheduleRepository

class ReserveSlotUseCase(
    private val dayScheduleRepository: DayScheduleRepository,
    private val getScheduleUseCase: GetScheduleUseCase,
) : UseCase<SlotId, DaySchedule> {

    override fun apply(input: SlotId): DaySchedule {
        val daySchedule = getScheduleUseCase.apply(input = input.day)

        val modifiedSchedule = daySchedule.reserveSlot(input.index)

        return dayScheduleRepository.save(modifiedSchedule)
    }
}

